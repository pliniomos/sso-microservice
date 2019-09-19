package com.pitang.desafio.mv.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pitang.desafio.mv.dto.UserAuthDTO;
import com.pitang.desafio.mv.model.service.ResourceAccountService;
import com.pitang.desafio.mv.token.TokenService;
import com.pitang.desafio.mv.util.ExceptionMessages;

@RestController
public class AuthController implements AuthApi {

	@Autowired
	private ResourceAccountService authService;

	public ResponseEntity<UserAuthDTO> signIn(UserAuthDTO body) {

		authService.checkAvailability(body);

		HttpHeaders responseHeaders = TokenService.setBearerAuthorization(new HttpHeaders(), body.getLogin());

		return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<UserAuthDTO> validateAuthorization(HttpServletRequest request) {

		String loginByToken = TokenService.getJwtsSubjectByToken(request.getHeader("Authorization"));

		if(StringUtils.isEmpty(loginByToken)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ExceptionMessages.EXCEPTION_MSG_UNAUTHORIZED);
		}

		UserAuthDTO userAuthDTO = new UserAuthDTO();

		userAuthDTO.setLogin(loginByToken);

		return new ResponseEntity<>(userAuthDTO, HttpStatus.OK);
	}

}
