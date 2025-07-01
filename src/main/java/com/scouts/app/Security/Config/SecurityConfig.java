package com.scouts.app.Security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.scouts.app.Security.Filters.BearerTokenFilter;
import com.scouts.app.Models.User;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private BearerTokenFilter bearerTokenFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint((request, response, authException) -> {
							response.setStatus(HttpStatus.UNAUTHORIZED.value());
							response.setContentType("application/json");
							response.getWriter().write("{\"success\": false,\"message\": \"Unauthorized\"}");
						})
						.accessDeniedHandler((request, response, accessDeniedException) -> {
							response.setStatus(HttpStatus.FORBIDDEN.value());
							response.setContentType("application/json");
							response.getWriter().write("{\"success\": false,\"message\": \"Forbidden\"}");
						}))
				.authorizeHttpRequests(authorize -> {
					// PUBLIC
					authorize.requestMatchers(HttpMethod.GET,
							"/api/users/*").permitAll();

					authorize.requestMatchers(HttpMethod.POST,
							"/api/auth/register",
							"/api/auth/login").permitAll();

					// ADMIN
					authorize.requestMatchers(HttpMethod.POST,
							"/api/users").hasRole(User.UserRole.ADMIN.toString());

					// ADMIN or LEADER
					authorize.requestMatchers(HttpMethod.POST,
							"/api/attendance/*")
							.hasAnyRole(User.UserRole.ADMIN.toString(), User.UserRole.LEADER.toString());

					authorize.anyRequest().authenticated();
				})
				.addFilterAfter(this.bearerTokenFilter, BasicAuthenticationFilter.class)
				.build();
	}
}
