package com.scouts.app.Http.Responses;

/**
 * SuccessResponse
 */
public class SuccessResponse extends Response {

	public SuccessResponse() {}

	public SuccessResponse(String message) {
		super(true, message);
	}
}
