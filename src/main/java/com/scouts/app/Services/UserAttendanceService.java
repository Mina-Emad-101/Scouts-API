package com.scouts.app.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scouts.app.Exceptions.DuplicateUserAttendanceException;
import com.scouts.app.Models.Attendance;
import com.scouts.app.Models.User;
import com.scouts.app.Models.UserAttendance;
import com.scouts.app.RepoModels.RepoAttendance;
import com.scouts.app.RepoModels.RepoUser;
import com.scouts.app.RepoModels.RepoUserAttendance;
import com.scouts.app.Repositories.UserAttendanceRepository;

/**
 * UserAttendanceService
 */
@Service
public class UserAttendanceService {

	@Autowired
	private UserAttendanceRepository userAttendanceRepository;

	/**
	 * @return {@link List<UserAttendance>}
	 */
	public List<UserAttendance> findByAttendance(Attendance attendance) {
		RepoAttendance repoAttendance = RepoAttendance.builder()
		.id(attendance.getId())
		.sector(attendance.getSector())
		.createdAt(attendance.getCreatedAt())
		.build();

		List<RepoUserAttendance> repoUserAttendances = userAttendanceRepository.findByAttendance(repoAttendance);

		return repoUserAttendances.stream().map((repoUserAttendance) -> new UserAttendance(repoUserAttendance)).toList();
	}

	/**
	 * @return {@link List<UserAttendance>}
	 */
	public List<UserAttendance> findByUser(User user) {
		RepoUser repoUser = RepoUser.builder()
		.id(user.getId())
		.name(user.getName())
		.email(user.getEmail())
		.password(user.getPassword())
		.build();

		List<RepoUserAttendance> repoUserAttendances = userAttendanceRepository.findByUser(repoUser);

		return repoUserAttendances.stream().map((repoUserAttendance) -> new UserAttendance(repoUserAttendance)).toList();
	}

	/**
	 * @return {@link UserAttendance} or {@code null}
	 */
	public UserAttendance findByUserAndAttendance(User user, Attendance attendance) {
		RepoUser repoUser = RepoUser.builder()
		.id(user.getId())
		.name(user.getName())
		.email(user.getEmail())
		.password(user.getPassword())
		.build();

		RepoAttendance repoAttendance = RepoAttendance.builder()
		.id(attendance.getId())
		.sector(attendance.getSector())
		.createdAt(attendance.getCreatedAt())
		.build();

		RepoUserAttendance repoUserAttendance = userAttendanceRepository.findByUserAndAttendance(repoUser, repoAttendance).orElse(null);

		if (repoUserAttendance == null)
			return null;

		return new UserAttendance(repoUserAttendance);
	}

	public UserAttendance save(UserAttendance userAttendance) throws DuplicateUserAttendanceException {
		UserAttendance duplicateUserAttendance = this.findByUserAndAttendance(userAttendance.getUser(), userAttendance.getAttendance());
		if (duplicateUserAttendance != null)
			throw new DuplicateUserAttendanceException();

		RepoUser repoUser = RepoUser.builder()
		.id(userAttendance.getUser().getId())
		.name(userAttendance.getUser().getName())
		.email(userAttendance.getUser().getEmail())
		.password(userAttendance.getUser().getPassword())
		.build();

		RepoAttendance repoAttendance = RepoAttendance.builder()
		.id(userAttendance.getAttendance().getId())
		.sector(userAttendance.getAttendance().getSector())
		.createdAt(userAttendance.getAttendance().getCreatedAt())
		.build();

		RepoUserAttendance repoUserAttendance = RepoUserAttendance.builder()
				.user(repoUser)
				.attendance(repoAttendance)
				.createdAt(userAttendance.getCreatedAt())
				.build();

		RepoUserAttendance savedRepoUserAttendance = this.userAttendanceRepository.save(repoUserAttendance);
		return new UserAttendance(savedRepoUserAttendance);
	}

	public List<UserAttendance> all() {
		List<UserAttendance> userAttendances = new ArrayList<>();
		this.userAttendanceRepository.findAll().forEach((RepoUserAttendance repoUserAttendance) -> userAttendances.add(new UserAttendance(repoUserAttendance)));

		return userAttendances;
	}
}
