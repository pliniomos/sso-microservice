package br.tec.plin.sso.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import br.tec.plin.sso.dto.UserAuthDTO;
import br.tec.plin.sso.service.ResourceAccountService;
import br.tec.plin.sso.util.ExceptionMessages;

@Service
public class ResourceAccountServiceImpl implements ResourceAccountService {

	@Value("${desafio.pitang.mv.resourceAccountUrl}")
	private String resourceAccountUrl;

	@Override
	public void checkAvailability(UserAuthDTO userAuth) {

		RestTemplate template = new RestTemplate();
		HttpEntity<UserAuthDTO> httpEntity = new HttpEntity<>(userAuth);
		try {
			template.postForEntity(this.resourceAccountUrl + "availability", httpEntity, Void.class);
		} catch (HttpClientErrorException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ExceptionMessages.EXCEPTION_MSG_INVALID_LOGIN);
		}
	}

}
