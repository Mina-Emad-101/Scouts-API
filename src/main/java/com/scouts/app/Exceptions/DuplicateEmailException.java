package com.scouts.app.Exceptions;

/**
 * DuplicateEmailException
 */
public class DuplicateEmailException extends RuntimeException {

	public DuplicateEmailException(String email) {
		super(String.format("Email (%s) already exists", email));
	}
}
