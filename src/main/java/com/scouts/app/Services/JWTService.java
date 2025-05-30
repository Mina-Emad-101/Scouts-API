package com.scouts.app.Services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * JWTService
 */
@Service
public class JWTService {

	private Algorithm algorithm;
	private String issuer;
	private Long expiryTimeInMillis;

	public JWTService(@Value("${jwt.secret}") String secret, @Value("${spring.application.name}") String issuer) {
		this.algorithm = Algorithm.HMAC256(secret);
		this.issuer = issuer;

		// 60_000 Milliseconds = 1 Minute
		this.expiryTimeInMillis = 60L * 60_000L;
	}

	public String sign(Long userID) {
		String jwtToken = JWT.create()
				.withIssuer(this.issuer)
				.withClaim("userID", userID)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + this.expiryTimeInMillis))
				.sign(this.algorithm);
		System.out.println("JWTTOKEN: " + jwtToken);
		return jwtToken;
	}

	public Long verify(String token) throws JWTVerificationException {
		JWTVerifier verifier = JWT.require(this.algorithm)
				.withIssuer(this.issuer)
				.build();
		DecodedJWT decodedJWT = verifier.verify(token);
		Long userID = decodedJWT.getClaim("userID").asLong();
		if (userID == null)
			throw new JWTVerificationException("Empty User ID");

		return userID;
	}
}
