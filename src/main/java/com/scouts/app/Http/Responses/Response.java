package com.scouts.app.Http.Responses;

import lombok.Getter;
import lombok.Setter;

/**
 * Response
 */
public class Response {

	@Getter
	@Setter
	private Boolean success;

	@Getter
	@Setter
	private String message;

	public Response() {}

	public Response(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}
}
