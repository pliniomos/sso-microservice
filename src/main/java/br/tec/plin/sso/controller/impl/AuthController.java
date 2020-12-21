package br.tec.plin.sso.controller.impl;

import javax.servlet.http.HttpServletRequest;

import br.tec.plin.sso.controller.AuthApi;
import br.tec.plin.sso.service.ResourceAccountService;
import br.tec.plin.sso.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.tec.plin.sso.dto.UserAuthDTO;
import br.tec.plin.sso.util.ExceptionMessages;

@RestController
public class AuthController implements AuthApi {

	@Autowired
	private ResourceAccountService resourceAccountService;

	public ResponseEntity<UserAuthDTO> signIn(UserAuthDTO body) {

		resourceAccountService.checkAvailability(body);

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
