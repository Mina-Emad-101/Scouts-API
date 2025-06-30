package com.scouts.app.Http.Responses;

import com.scouts.app.Models.User;

import lombok.Getter;
import lombok.Setter;

/**
 * GetUserResponse
 */
public class GetUserResponse extends Response {

	@Getter
	@Setter
	private User user;

	public GetUserResponse() {}

	public GetUserResponse(User user) {
		super(true, "User Retrieved Successfully");
		this.user = user;
	}
}
