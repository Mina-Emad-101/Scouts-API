package com.scouts.app.Http.Responses;

import lombok.Data;
import lombok.Getter;

/**
 * Response
 */
public class Response {

	@Getter
	private Boolean success;

	@Getter
	private String message;

	public Response(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}
}
