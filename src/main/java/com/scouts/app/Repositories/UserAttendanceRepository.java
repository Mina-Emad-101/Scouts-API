package com.scouts.app.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scouts.app.RepoModels.RepoAttendance;
import com.scouts.app.RepoModels.RepoUser;
import com.scouts.app.RepoModels.RepoUserAttendance;

/**
 * UserAttendanceRepository
 */
@Repository
public interface UserAttendanceRepository extends CrudRepository<RepoUserAttendance, Long> {
	public List<RepoUserAttendance> findByAttendance(RepoAttendance attendance);
	public List<RepoUserAttendance> findByUser(RepoUser user);
	public Optional<RepoUserAttendance> findByUserAndAttendance(RepoUser user, RepoAttendance attendance);
}
