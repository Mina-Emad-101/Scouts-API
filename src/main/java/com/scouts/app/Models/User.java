package com.scouts.app.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
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

	public enum Sector {
		ASHBAL,
		KASHAFA,
		MOTAKADEM,
	}

	private Long id;
	private String name;
	private String email;
	private UserRole role;
	private Sector sector;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	public User() {}

	public User(RepoUser repoUser) {
		this.id = repoUser.getId();
		this.name = repoUser.getName();
		this.email = repoUser.getEmail();
		this.password = repoUser.getPassword();
		this.role = repoUser.getRole();
		this.sector = repoUser.getSector();
	}
}
