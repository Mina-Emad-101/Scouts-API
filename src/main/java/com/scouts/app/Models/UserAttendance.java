package com.scouts.app.Models;

import java.util.Date;

import com.scouts.app.RepoModels.RepoUserAttendance;

import lombok.Data;

/**
 * UserAttendance
 */
@Data
public class UserAttendance {

	private Long id;
	private User user;
	private Attendance attendance;
	private Date createdAt;

	public UserAttendance() {}

	public UserAttendance(RepoUserAttendance repoUserAttendance) {
		this.id = repoUserAttendance.getId();
		this.user = new User(repoUserAttendance.getUser());
		this.attendance = new Attendance(repoUserAttendance.getAttendance());
		this.createdAt = repoUserAttendance.getCreatedAt();
	}
}
