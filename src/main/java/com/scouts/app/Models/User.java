package com.scouts.app.Models;

import com.scouts.app.RepoModels.RepoUser;

import lombok.Data;

/**
 * User
 */
@Data
public class User {

	public enum UserRole {
		USER,
		LEADER,
		ADMIN,
	}

	private Long id;
	private String name;
	private String email;
	private UserRole role;

	public User(RepoUser repoUser) {
		this.id = repoUser.getId();
		this.name = repoUser.getName();
		this.email = repoUser.getEmail();
		this.role = repoUser.getRole();
	}
}
