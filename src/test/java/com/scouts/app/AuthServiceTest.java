package com.scouts.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.scouts.app.Exceptions.InvalidLoginException;
import com.scouts.app.Models.User;
import com.scouts.app.Services.AuthService;

import jakarta.transaction.Transactional;

/**
 * AuthServiceTest
 */
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/test-data.sql")
@Transactional
@Rollback
public class AuthServiceTest {

	@Autowired
	private AuthService authService;

	@Test
	public void loginInvalid_ThrowInvalidLoginException() {
		assertThrows(InvalidLoginException.class, () -> {
			this.authService.login("invalid@gmail.com", "invalidPassword");
		});
		assertThrows(InvalidLoginException.class, () -> {
			this.authService.login("admin@gmail.com", "invalidPassword");
		});
		assertThrows(InvalidLoginException.class, () -> {
			this.authService.login("invalid@gmail.com", "password");
		});
	}

	@Test
	public void loginValid_ReturnNotNullUserWithEmail() {
		User user = this.authService.login("admin@gmail.com", "password");
		assertNotNull(user);
		assertEquals("admin@gmail.com", user.getEmail());
	}

	@Test
	public void loginAdmin_RoleIsAdmin() {
		User user = this.authService.login("admin@gmail.com", "password");
		assertEquals(User.UserRole.ADMIN, user.getRole());
	}

	@Test
	public void loginUser_RoleIsUser() {
		User user = this.authService.login("user@gmail.com", "password");
		assertEquals(User.UserRole.USER, user.getRole());
	}
}
