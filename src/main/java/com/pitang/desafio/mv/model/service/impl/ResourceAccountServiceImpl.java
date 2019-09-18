package com.pitang.desafio.mv.model.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.pitang.desafio.mv.dto.UserAuthDTO;
import com.pitang.desafio.mv.exception.InvalidEmailOrPasswordException;
import com.pitang.desafio.mv.model.service.ResourceAccountService;
import com.pitang.desafio.mv.util.ExceptionMessages;

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
			throw new InvalidEmailOrPasswordException(ExceptionMessages.EXCEPTION_MSG_INVALID_LOGIN);
		}
	}

}
