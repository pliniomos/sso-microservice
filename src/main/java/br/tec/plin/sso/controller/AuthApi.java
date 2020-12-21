package br.tec.plin.sso.controller;

import javax.servlet.http.HttpServletRequest;

import br.tec.plin.sso.dto.UserAuthDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
