package com.scouts.app.Controllers;

import java.time.Instant;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scouts.app.Exceptions.DuplicateUserAttendanceException;
import com.scouts.app.Http.Responses.ErrorResponse;
import com.scouts.app.Http.Responses.Response;
import com.scouts.app.Http.Responses.SuccessResponse;
import com.scouts.app.Models.Attendance;
import com.scouts.app.Models.User;
import com.scouts.app.Models.UserAttendance;
import com.scouts.app.Services.AttendanceService;
import com.scouts.app.Services.UserAttendanceService;
import com.scouts.app.Services.UsersService;

/**
 * AttendanceController
 */
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

	private AttendanceService attendanceService;
	private UsersService usersService;
	private UserAttendanceService userAttendanceService;

	public AttendanceController(AttendanceService attendanceService, UsersService usersService,
			UserAttendanceService userAttendanceService) {
		this.attendanceService = attendanceService;
		this.usersService = usersService;
		this.userAttendanceService = userAttendanceService;
	}

	@PostMapping("/{id}")
	public ResponseEntity<Response> attend(@PathVariable Long id) {
		User user = this.usersService.findById(id);
		if (user == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User Not Found"));

		Date today = Date.from(Instant.now());

		Attendance attendance = this.attendanceService.findByCreatedAt(today);
		if (attendance == null)
			attendance = this.attendanceService.create(user.getSector());

		UserAttendance userAttendance = new UserAttendance();
		userAttendance.setUser(user);
		userAttendance.setAttendance(attendance);
		userAttendance.setCreatedAt(today);

		try {
			this.userAttendanceService.save(userAttendance);
		} catch (DuplicateUserAttendanceException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
		}

		return ResponseEntity.ok(new SuccessResponse(String.format("%s Added Successfully", user.getName())));
	}
}
