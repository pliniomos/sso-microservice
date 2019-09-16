package com.pitang.desafio.mv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidEmailOrPasswordException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8198115228055352384L;

	public InvalidEmailOrPasswordException(String msg) {
		super(HttpStatus.UNAUTHORIZED, msg);
	}

}
