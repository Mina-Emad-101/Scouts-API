package com.scouts.app.RepoModels;

import java.util.List;

import com.scouts.app.Models.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RepoUser
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepoUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private User.UserRole role;

	@Enumerated(EnumType.STRING)
	private User.Sector sector;

	@OneToMany(mappedBy = "user")
	private List<RepoUserAttendance> userAttendances;
}
