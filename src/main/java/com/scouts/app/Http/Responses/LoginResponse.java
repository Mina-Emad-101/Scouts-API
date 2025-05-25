package com.scouts.app.Http.Responses;

import lombok.Getter;

/**
 * LoginRequest
 */
public class LoginResponse extends Response {

	@Getter
	private String token;

	public LoginResponse(String token) {
		super(true, "Logged in successfully");
		this.token = token;
	}
}
