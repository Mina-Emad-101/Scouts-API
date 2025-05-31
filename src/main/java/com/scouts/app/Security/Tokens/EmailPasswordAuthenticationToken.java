package com.scouts.app.Security.Tokens;


import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * EmailPasswordAuthenticationToken
 */
public class EmailPasswordAuthenticationToken extends AbstractAuthenticationToken {
	private Object principal;

	public EmailPasswordAuthenticationToken(Object principal) {
		super(null);
		this.principal = principal;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}
}
