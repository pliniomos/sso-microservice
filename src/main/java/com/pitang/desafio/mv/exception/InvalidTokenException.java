package com.pitang.desafio.mv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.pitang.desafio.mv.util.ExceptionMessages;

public class InvalidTokenException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8145239744295697100L;

	public InvalidTokenException() {
		super(HttpStatus.UNAUTHORIZED, ExceptionMessages.EXCEPTION_MSG_INVALID_TOKEN);
	}

}
