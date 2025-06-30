package com.scouts.app.Http.Responses;

import com.scouts.app.Models.User;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthUserResponse
 */
public class AuthUserResponse extends Response {

	@Getter
	@Setter
	private User user;

	public AuthUserResponse() {}

	public AuthUserResponse(User user) {
		super(true, "Authenticated User Retrieved Successfully");
		this.user = user;
	}
}
