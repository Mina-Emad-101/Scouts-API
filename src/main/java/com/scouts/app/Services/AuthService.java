package com.scouts.app.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scouts.app.Repositories.UserRepository;
import com.scouts.app.RepoModels.RepoUser;

import com.scouts.app.Exceptions.InvalidLoginException;
import com.scouts.app.Models.User;

/**
 * AuthService
 */
@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	public User login(String email, String password) {
		RepoUser repoUser = userRepository.findByEmail(email).orElse(null);

		if (repoUser == null)
			throw new InvalidLoginException("User Not Found");
		if (!repoUser.getPassword().equals(password))
			throw new InvalidLoginException("Invalid Password");

		return User.builder()
				.id(repoUser.getId())
				.name(repoUser.getName())
				.email(repoUser.getEmail())
				.role(repoUser.getRole())
				.build();
	}
}
