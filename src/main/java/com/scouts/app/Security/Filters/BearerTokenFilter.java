package com.scouts.app.Security.Filters;

import java.io.IOException;
import java.net.Authenticator;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scouts.app.Exceptions.InvalidAuthTokenException;
import com.scouts.app.Http.Responses.ErrorResponse;
import com.scouts.app.Models.User;
import com.scouts.app.RepoModels.RepoUser;
import com.scouts.app.Repositories.UserRepository;
import com.scouts.app.Security.Tokens.EmailPasswordAuthenticationToken;
import com.scouts.app.Services.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * BearerTokenFilter
 */
@Component
public class BearerTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null) {
			filterChain.doFilter(request, response);
			return;
		}

		User user;
		try {
			user = this.verify(authHeader);
		} catch (InvalidAuthTokenException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
			ObjectMapper mapper = new ObjectMapper();
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType("application/json");
			response.getWriter().write(mapper.writeValueAsString(errorResponse));
			return;
		}

		String role = "ROLE_" + user.getRole();
		Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

		Authentication authenticationToken = new EmailPasswordAuthenticationToken(user, authorities);
		authenticationToken.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
		filterChain.doFilter(request, response);
		return;
	}

	private User verify(String authHeader) throws InvalidAuthTokenException {
		if (authHeader.length() < 8 || !authHeader.startsWith("Bearer "))
			throw new InvalidAuthTokenException("Invalid Authorization Header");

		String token = authHeader.substring(7);
		Long userID = this.jwtService.verify(token);

		RepoUser repoUser = this.userRepository.findById(userID).orElse(null);
		if (repoUser == null)
			throw new InvalidAuthTokenException("User Not Found");

		User user = new User(repoUser);
		return user;
	}
}
