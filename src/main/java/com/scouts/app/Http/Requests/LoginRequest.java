package com.scouts.app.Http.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * LoginRequest
 */
@Data
@AllArgsConstructor
public class LoginRequest {

	private String email;
	private String password;
}
