package com.scouts.app.Http.Responses;

import lombok.Getter;
import lombok.Setter;

/**
 * LoginRequest
 */
public class LoginResponse extends Response {

	@Getter
	@Setter
	private String token;

	public LoginResponse() {}

	public LoginResponse(String token) {
		super(true, "Logged in successfully");
		this.token = token;
	}
}
