package br.tec.plin.sso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ti.plinio@gmail.com
 * 
 */

public class UserTokenDTO {

	@JsonProperty("token")
	private @Getter @Setter String token;

	@JsonProperty("expires")
	private @Getter @Setter String expires;

}
