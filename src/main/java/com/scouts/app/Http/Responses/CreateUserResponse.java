package com.scouts.app.Http.Responses;

import com.scouts.app.Models.User;

import lombok.Getter;
import lombok.Setter;

/**
 * CreateUserResponse
 */
public class CreateUserResponse extends Response {

	@Getter
	@Setter
	private Long id;

	public CreateUserResponse() {}

	public CreateUserResponse(User user) {
		super(true, "User Created Successfully");
		this.id = user.getId();
	}
}
