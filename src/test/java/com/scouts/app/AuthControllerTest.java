package com.scouts.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
