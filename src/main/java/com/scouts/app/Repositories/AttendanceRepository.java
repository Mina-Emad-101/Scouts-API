package com.scouts.app.Repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scouts.app.RepoModels.RepoAttendance;

/**
 * AttendanceRepository
 */
@Repository
public interface AttendanceRepository extends CrudRepository<RepoAttendance, Long> {
	public Optional<RepoAttendance> findByCreatedAt(Date date);
}
