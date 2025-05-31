package com.scouts.app.Security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.scouts.app.Security.Filters.BearerTokenFilter;

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
				.authorizeHttpRequests(authorize -> {
					authorize.requestMatchers("/api/auth/register").permitAll();
					authorize.requestMatchers("/api/auth/login").permitAll();
					authorize.anyRequest().authenticated();
				})
				.addFilterAfter(this.bearerTokenFilter, BasicAuthenticationFilter.class)
				.build();
	}
}
