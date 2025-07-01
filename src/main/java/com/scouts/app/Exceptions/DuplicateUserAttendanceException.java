package com.scouts.app.Exceptions;

/**
 * DuplicateUserAttendanceException
 */
public class DuplicateUserAttendanceException extends RuntimeException {

	public DuplicateUserAttendanceException() {
		super("User Attendance already exists");
	}
}
