package com.pitang.desafio.mv.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pitang.desafio.mv.dto.UserAuthDTO;

/**
 * 
 * @author ti.plinio@gmail.com
 * 
 */

public interface AuthApi {

	@CrossOrigin
	@PostMapping(value = "/signin", 
			produces = { "application/json" },
			consumes = { "application/json" })
	ResponseEntity<UserAuthDTO> signIn(@RequestBody UserAuthDTO body);

	@PostMapping(value = "/validate", 
			produces = { "application/json" })
	ResponseEntity<UserAuthDTO> validateAuthorization(HttpServletRequest request);

}
