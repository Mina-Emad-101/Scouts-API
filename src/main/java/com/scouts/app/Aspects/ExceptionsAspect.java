package com.scouts.app.Aspects;

import java.net.http.HttpResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.scouts.app.Http.Responses.ErrorResponse;

/**
 * ExceptionsAspect
 */
@Aspect
public class ExceptionsAspect {

	@Around("within(com.scouts.app.Controllers..*)")
	public Object exceptionsAspect(ProceedingJoinPoint joinPoint) {
		try {
			return joinPoint.proceed();
		} catch (Throwable e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
		}
	}
}
