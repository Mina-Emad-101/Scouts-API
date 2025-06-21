package com.scouts.app.Security.Tokens;


import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.scouts.app.Models.User;

/**
 * EmailPasswordAuthenticationToken
 */
public class EmailPasswordAuthenticationToken extends AbstractAuthenticationToken {
	private Object principal;

	public EmailPasswordAuthenticationToken(Object principal, Collection<GrantedAuthority> authorities) {
		super(authorities);
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
