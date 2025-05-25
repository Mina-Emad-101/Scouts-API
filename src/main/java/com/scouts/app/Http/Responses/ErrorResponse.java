package com.scouts.app.Http.Responses;

/**
 * ErrorResponse
 */
public class ErrorResponse extends Response {

	public ErrorResponse(String message) {
		super(false, message);
	}
}
