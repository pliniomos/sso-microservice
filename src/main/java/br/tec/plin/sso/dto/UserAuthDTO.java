package br.tec.plin.sso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ti.plinio@gmail.com
 * 
 */

public class UserAuthDTO {

	@JsonProperty("login")
	private @Getter @Setter String login;

	@JsonProperty("password")
	private @Getter @Setter String password;

}
