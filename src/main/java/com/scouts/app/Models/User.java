package com.scouts.app.Models;

import lombok.Builder;
import lombok.Data;

/**
 * User
 */
@Data
@Builder
public class User {

	public enum UserRole {
		USER,
		ADMIN
	}

	private Long id;
	private String name;
	private String email;
	private UserRole role;
}
