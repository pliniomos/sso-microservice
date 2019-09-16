package com.pitang.desafio.mv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.pitang.desafio.mv.util.ExceptionMessages;

public class ExpiredTokenException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2061487316326613260L;

	public ExpiredTokenException() {
		super(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_EXPIRED_TOKEN);
	}

}
