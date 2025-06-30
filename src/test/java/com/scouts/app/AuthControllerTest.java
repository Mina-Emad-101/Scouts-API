package com.scouts.app;

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
import com.scouts.app.Http.Responses.LoginResponse;
import com.scouts.app.Models.User;

import jakarta.transaction.Transactional;

/**
 * AuthControllerTest
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/test-data.sql")
@Transactional
@Rollback
public class AuthControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void loginAdminCredentials_ReturnsToken() throws Exception {
		String json = """
				{
					"email": "admin@gmail.com",
					"password": "password"
				}
				""";

		this.mockMvc.perform(
				post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").isString())
				.andExpect(jsonPath("$.token").isString());
	}

	@Test
	public void loginLeaderCredentials_ReturnsToken() throws Exception {
		String json = """
				{
					"email": "leader@gmail.com",
					"password": "password"
				}
				""";

		this.mockMvc.perform(
				post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").isString())
				.andExpect(jsonPath("$.token").isString());
	}

	@Test
	public void loginUserCredentials_ReturnsToken() throws Exception {
		String json = """
				{
					"email": "user@gmail.com",
					"password": "password"
				}
				""";

		this.mockMvc.perform(
				post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").isString())
				.andExpect(jsonPath("$.token").isString());
	}

	@Test
	public void loginInvalidCredentials_ReturnsErrorResponse() throws Exception {
		String json = """
				{
					"email": "invalid@gmail.com",
					"password": "invalid_password"
				}
				""";

		this.mockMvc.perform(
				post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.message").isString());
	}

	@Test
	public void authUserByToken_ReturnsCorrectUser() throws Exception {
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
				get("/api/auth/user")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.user.email").value("user@gmail.com"))
				.andExpect(jsonPath("$.user.role").value(User.UserRole.USER.toString()));
	}

	@Test
	public void authAdminByToken_ReturnsCorrectUser() throws Exception {
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

		this.mockMvc.perform(
				get("/api/auth/user")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.user.email").value("admin@gmail.com"))
				.andExpect(jsonPath("$.user.role").value(User.UserRole.ADMIN.toString()));
	}

	@Test
	public void authLeaderByToken_ReturnsCorrectUser() throws Exception {
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

		this.mockMvc.perform(
				get("/api/auth/user")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.user.email").value("leader@gmail.com"))
				.andExpect(jsonPath("$.user.role").value(User.UserRole.LEADER.toString()));
	}
}
