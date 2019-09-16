package com.pitang.desafio.mv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.pitang.desafio.mv.util.ExceptionMessages;

public class UnauthorizedException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6195161648873084145L;

	public UnauthorizedException() {
		super(HttpStatus.UNAUTHORIZED, ExceptionMessages.EXCEPTION_MSG_UNAUTHORIZED);
	}

}
