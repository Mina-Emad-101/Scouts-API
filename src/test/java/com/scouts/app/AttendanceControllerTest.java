package com.scouts.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scouts.app.Http.Responses.LoginResponse;
import com.scouts.app.Models.UserAttendance;
import com.scouts.app.Services.AttendanceService;
import com.scouts.app.Services.UserAttendanceService;
import com.scouts.app.Services.UsersService;

import jakarta.transaction.Transactional;

/**
 * AttendanceControllerTest
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/test-data.sql")
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
@Rollback
public class AttendanceControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsersService usersService;

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private UserAttendanceService userAttendanceService;

	@Test
	public void attendWhileUnauthenticated_Fail401() throws Exception {
		this.mockMvc.perform(
				post("/api/attendance/3"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void attendAsUser_Fail403() throws Exception {
		String json = """
				{
					"email": "user@gmail.com",
					"password": "password"
				}
				""";

		String result = this.mockMvc.perform(
				post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andReturn()
				.getResponse()
				.getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		LoginResponse loginResponse = mapper.readValue(result, LoginResponse.class);
		String token = loginResponse.getToken();

		this.mockMvc.perform(
				post("/api/attendance/3")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isForbidden());
	}

	@Test
	public void attendAsLeader_Success() throws Exception {
		String json = """
				{
					"email": "leader@gmail.com",
					"password": "password"
				}
				""";

		String result = this.mockMvc.perform(
				post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andReturn()
				.getResponse()
				.getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		LoginResponse loginResponse = mapper.readValue(result, LoginResponse.class);
		String token = loginResponse.getToken();

		result = this.mockMvc.perform(
				post("/api/attendance/3")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andReturn()
				.getResponse()
				.getContentAsString();

		List<UserAttendance> userAttendances = this.userAttendanceService.findByUser(this.usersService.findById(3L));
		assertEquals(1, userAttendances.size());
	}

	@Test
	public void attendAsAdmin_Success() throws Exception {
		String json = """
				{
					"email": "admin@gmail.com",
					"password": "password"
				}
				""";

		String result = this.mockMvc.perform(
				post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andReturn()
				.getResponse()
				.getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		LoginResponse loginResponse = mapper.readValue(result, LoginResponse.class);
		String token = loginResponse.getToken();

		result = this.mockMvc.perform(
				post("/api/attendance/3")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andReturn()
				.getResponse()
				.getContentAsString();

		List<UserAttendance> userAttendances = this.userAttendanceService.findByUser(this.usersService.findById(3L));
		assertEquals(1, userAttendances.size());
	}
}
