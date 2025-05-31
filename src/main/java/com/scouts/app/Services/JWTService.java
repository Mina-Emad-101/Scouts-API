package com.scouts.app.Services;

import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.random.RandomGeneratorFactory;

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
		String paddedSecret = this.padSecretTo64Bits(secret);
		String base64Secret = Base64.getEncoder().encodeToString(paddedSecret.getBytes());

		this.algorithm = Algorithm.HMAC256(base64Secret);

		this.issuer = issuer;

		// 60_000 Milliseconds = 1 Minute
		this.expiryTimeInMillis = 60L * 60_000L;
	}

	private String padSecretTo64Bits(String secret) {
		StringBuilder builder = new StringBuilder(secret);
		for (int i = 0; i < 64 - secret.length(); i++) {
			builder.append(" ");
		}
		return builder.toString();
	}

	public String sign(Long userID) {
		String jwtToken = JWT.create()
				.withIssuer(this.issuer)
				.withClaim("userID", userID)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + this.expiryTimeInMillis))
				.sign(this.algorithm);
		return jwtToken;
	}

	/**
	 * Returns UserID found in token's "userID" claim, 
	 * if claim not found, returns JWTVerificationException
	 */
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
