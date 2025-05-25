package com.scouts.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scouts.app.Services.JWTService;

/**
 * JWTServiceTest
 */
@SpringBootTest
public class JWTServiceTest {

	@Autowired
	private JWTService jwtService;

	@Test
	public void signJWT() {
		String token = this.jwtService.sign(1L);
		assertNotNull(token);
	}

	@Test
	public void signJWTThenVerify() {
		String token = this.jwtService.sign(1L);
		Long userID = this.jwtService.verify(token);
		assertEquals(1, userID);
	}
}
