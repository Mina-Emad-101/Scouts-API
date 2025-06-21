package com.scouts.app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.scouts.app.Repositories.UserRepository;
import com.scouts.app.RepoModels.RepoUser;

import com.scouts.app.Exceptions.InvalidLoginException;
import com.scouts.app.Models.User;

/**
 * UsersService
 */
@Service
public class UsersService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * @param id
	 * @return {@link User} or {@code null}
	 */
	public User findById(Long id) {
		RepoUser repoUser = userRepository.findById(id).orElse(null);

		if (repoUser == null)
			return null;

		return new User(repoUser);
	}

	public User save(User user) {
		RepoUser repoUser = RepoUser.builder()
				.name(user.getName())
				.email(user.getEmail())
				.password(user.getPassword())
				.role(user.getRole())
				.build();

		RepoUser savedRepoUser = this.userRepository.save(repoUser);
		return new User(savedRepoUser);
	}
}
