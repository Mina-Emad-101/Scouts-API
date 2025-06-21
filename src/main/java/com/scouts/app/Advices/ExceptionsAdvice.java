package com.scouts.app.Advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.scouts.app.Http.Responses.ErrorResponse;

@RestControllerAdvice
public class ExceptionsAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUncaughtExceptions(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse("An unexpected error occurred: " + e.getMessage()));
	}
}
