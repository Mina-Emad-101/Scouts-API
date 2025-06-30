package com.scouts.app.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.scouts.app.Exceptions.InvalidLoginException;
import com.scouts.app.Http.Requests.CreateUserRequest;
import com.scouts.app.Http.Responses.CreateUserResponse;
import com.scouts.app.Http.Responses.GetUserResponse;
import com.scouts.app.Http.Responses.ErrorResponse;
import com.scouts.app.Http.Responses.Response;
import com.scouts.app.Models.User;
import com.scouts.app.Services.UsersService;
import com.scouts.app.Services.JWTService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UsersController
 */
@RestController
@RequestMapping("/api/users")
public class UsersController {

	private UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@PostMapping()
	public ResponseEntity<Response> create(@RequestBody CreateUserRequest request) {
		String password = request.getPassword();

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setRole(request.getRole());
		user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

		User savedUser = this.usersService.save(user);

		CreateUserResponse response = new CreateUserResponse(savedUser);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getUser(@PathVariable Long id) {
		User user = this.usersService.findById(id);
		if (user == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User not found"));

		return ResponseEntity.ok(new GetUserResponse(user));
	}
}
