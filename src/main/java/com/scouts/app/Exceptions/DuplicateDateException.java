package com.scouts.app.Exceptions;

import java.text.DateFormat;
import java.util.Date;

/**
 * DuplicateDateException
 */
public class DuplicateDateException extends RuntimeException {

	public DuplicateDateException(Date date) {
		super(String.format("Date (%s) already exists", DateFormat.getInstance().format(date)));
	}
}
