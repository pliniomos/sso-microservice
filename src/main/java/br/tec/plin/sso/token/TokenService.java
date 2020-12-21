package br.tec.plin.sso.token;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import br.tec.plin.sso.util.Constants;
import br.tec.plin.sso.util.ExceptionMessages;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenService {

	@Value("${token.secret.key}")
	private static String tokenSecretKey;

	private TokenService() {
	}

	public static HttpHeaders setBearerAuthorization(HttpHeaders httpHeaders, String username) {

		Date expirationDate = new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME);

		String tokenJWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, tokenSecretKey)
				.compact();

		httpHeaders.add("Access-Control-Expose-Headers", "Authorization");
		httpHeaders.add("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
		httpHeaders.setBearerAuth(tokenJWT);
		httpHeaders.setExpires(expirationDate.getTime());
		
		return httpHeaders;

	}

	public static String getJwtsSubjectByToken(String authorization) {

		if (StringUtils.isEmpty(authorization) || !StringUtils.startsWithIgnoreCase(authorization, Constants.TOKEN_PREFIX)) {
			return null;
		}

		try {
			return Jwts.parser()
					.setSigningKey(tokenSecretKey)
					.parseClaimsJws(authorization.replace(Constants.TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
		} catch (ExpiredJwtException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionMessages.EXCEPTION_MSG_EXPIRED_TOKEN);
		} catch (JwtException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ExceptionMessages.EXCEPTION_MSG_INVALID_TOKEN);
		}

	}

}