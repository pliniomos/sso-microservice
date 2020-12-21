package br.tec.plin.sso.service;

import org.springframework.stereotype.Service;

import br.tec.plin.sso.dto.UserAuthDTO;

@Service
public interface ResourceAccountService {

	void checkAvailability(UserAuthDTO user);

}
