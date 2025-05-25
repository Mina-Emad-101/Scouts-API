package com.scouts.app.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scouts.app.RepoModels.RepoUser;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends CrudRepository<RepoUser, Long> {
	public Optional<RepoUser> findByEmail(String email);
}
