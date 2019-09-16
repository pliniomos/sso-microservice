package com.pitang.desafio.mv.model.service;

import org.springframework.stereotype.Service;

import com.pitang.desafio.mv.dto.UserAuthDTO;

@Service
public interface ResourceAccountService {

	void checkAvailability(UserAuthDTO user);

}
