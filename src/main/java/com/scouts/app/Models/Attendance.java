package com.scouts.app.Models;

import java.util.Date;

import com.scouts.app.RepoModels.RepoAttendance;

import lombok.Data;

/**
 * Attendance
 */
@Data
public class Attendance {
	private Long id;
	private User.Sector sector;
	private Date createdAt;

	public Attendance() {}

	public Attendance(RepoAttendance repoAttendance) {
		this.id = repoAttendance.getId();
		this.createdAt = repoAttendance.getCreatedAt();
	}
}
