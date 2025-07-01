package com.scouts.app.Services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scouts.app.Exceptions.DuplicateDateException;
import com.scouts.app.Models.Attendance;
import com.scouts.app.Models.User.Sector;
import com.scouts.app.RepoModels.RepoAttendance;
import com.scouts.app.Repositories.AttendanceRepository;

/**
 * AttendanceService
 */
@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepository attendanceRepository;

	/**
	 * @param id
	 * @return {@link Attendance} or {@code null}
	 */
	public Attendance findById(Long id) {
		RepoAttendance repoAttendance = attendanceRepository.findById(id).orElse(null);

		if (repoAttendance == null)
			return null;

		return new Attendance(repoAttendance);
	}

	public Attendance findByCreatedAt(Date date) {
		RepoAttendance repoAttendance = attendanceRepository.findByCreatedAt(date).orElse(null);

		if (repoAttendance == null)
			return null;

		return new Attendance(repoAttendance);
	}

	public Attendance save(Attendance attendance) throws DuplicateDateException {
		RepoAttendance duplicateDateattendance = this.attendanceRepository.findByCreatedAt(attendance.getCreatedAt()).orElse(null);
		if (duplicateDateattendance != null)
			throw new DuplicateDateException(attendance.getCreatedAt());

		RepoAttendance repoAttendance = RepoAttendance.builder()
				.sector(attendance.getSector())
				.createdAt(attendance.getCreatedAt())
				.build();

		RepoAttendance savedRepoAttendance = this.attendanceRepository.save(repoAttendance);
		return new Attendance(savedRepoAttendance);
	}

	public List<Attendance> all() {
		List<Attendance> attendances = new ArrayList<>();
		this.attendanceRepository.findAll().forEach((RepoAttendance repoAttendance) -> attendances.add(new Attendance(repoAttendance)));

		return attendances;
	}

	public Attendance create(Sector sector) {
		RepoAttendance repoAttendance = RepoAttendance.builder()
				.sector(sector)
				.createdAt(Date.from(Instant.now()))
				.build();

		RepoAttendance savedRepoAttendance = this.attendanceRepository.save(repoAttendance);
		return new Attendance(savedRepoAttendance);
	}
}
