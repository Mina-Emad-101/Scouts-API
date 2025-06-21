package com.scouts.app.Http.Requests;

import com.scouts.app.Models.User;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CreateUserRequest
 */
@Data
@AllArgsConstructor
public class CreateUserRequest {

	private String name;
	private String email;
	private String password;
	private User.UserRole role;
}
