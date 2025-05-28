package com.scouts.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.auth0.jwt.exceptions.JWTVerificationException;
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

	@Test
	public void verifyInvalidJWT() {
		String token = "MYINVALIDTOKEN";
		assertThrows(JWTVerificationException.class, () -> this.jwtService.verify(token));
	}

	@Test
	public void verifyExpiredJWT() {
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTY291dHNBcHAiLCJleHAiOjE3NDgzOTMwNTAsInVzZXJJRCI6NSwiaWF0IjoxNzQ4MzkzMDQxfQ.fcRAGZAMUjuoKiiV6piwEXxWWCx3cTNKzudQloS8NVk";
		assertThrows(JWTVerificationException.class, () -> this.jwtService.verify(token));
	}

}
