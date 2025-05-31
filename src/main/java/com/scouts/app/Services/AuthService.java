package com.scouts.app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
		if (!BCrypt.checkpw(password, repoUser.getPassword()))
			throw new InvalidLoginException("Invalid Password");

		return new User(repoUser);
	}
}
