package com.scouts.app.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.scouts.app.Exceptions.InvalidLoginException;
import com.scouts.app.Http.Requests.LoginRequest;
import com.scouts.app.Http.Responses.ErrorResponse;
import com.scouts.app.Http.Responses.LoginResponse;
import com.scouts.app.Http.Responses.AuthUserResponse;
import com.scouts.app.Http.Responses.Response;
import com.scouts.app.Models.User;
import com.scouts.app.Services.AuthService;
import com.scouts.app.Services.JWTService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AuthController
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;
	private JWTService jwtService;

	public AuthController(AuthService authService, JWTService jwtService) {
		this.authService = authService;
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginRequest login) {
		String email = login.getEmail();
		String password = login.getPassword();
		User user;
		try {
			user = this.authService.login(email, password);
		} catch (InvalidLoginException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
		}

		String token = this.jwtService.sign(user.getId());

		LoginResponse response = new LoginResponse(token);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/user")
	public ResponseEntity<Object> user(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return ResponseEntity.ok(new AuthUserResponse(user));
	}
}
