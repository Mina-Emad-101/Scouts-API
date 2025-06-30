package com.scouts.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.scouts.app.Http.Responses.CreateUserResponse;
import com.scouts.app.Http.Responses.LoginResponse;
import com.scouts.app.Models.User;
import com.scouts.app.Services.UsersService;

import jakarta.transaction.Transactional;

/**
 * UsersControllerTest
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/test-data.sql")
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
@Rollback
public class UsersControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsersService usersService;

	@Test
	public void createUserWhileUnauthenticated_Fail401() throws Exception {
		String json = """
				{
					"name": "New",
					"email": "new@gmail.com",
					"password": "newpassword",
					"role": "ADMIN"
				}
				""";

		this.mockMvc.perform(
				post("/api/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void createUserAsNonAdmin_Fail403() throws Exception {
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

		json = """
				{
					"name": "New",
					"email": "new@gmail.com",
					"password": "newpassword",
					"role": "USER"
				}
				""";

		this.mockMvc.perform(
				post("/api/users")
						.header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isForbidden());
	}

	@Test
	public void createUserAsAdmin_Success() throws Exception {
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

		json = """
				{
					"name": "New",
					"email": "new@gmail.com",
					"password": "newpassword",
					"role": "USER"
				}
				""";

		result = this.mockMvc.perform(
				post("/api/users")
						.header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andReturn()
				.getResponse()
				.getContentAsString();

		CreateUserResponse response = mapper.readValue(result, CreateUserResponse.class);
		Long id = response.getId();

		User user = this.usersService.findById(id);
		assertNotNull(user);
	}

	@Test
	public void getUserById_Success() throws Exception {
		this.mockMvc.perform(
				get("/api/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.user.id").value(1))
				.andReturn()
				.getResponse()
				.getContentAsString();
	}


	@Test
	public void getUserByWrongId_Fail404() throws Exception {
		this.mockMvc.perform(
				get("/api/users/100"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").value(false))
				.andReturn()
				.getResponse()
				.getContentAsString();
	}
}
