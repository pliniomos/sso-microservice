package com.pitang.desafio.mv.token;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import com.pitang.desafio.mv.exception.ExpiredTokenException;
import com.pitang.desafio.mv.exception.InvalidTokenException;
import com.pitang.desafio.mv.util.Constants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenService {

	private TokenService() {
	}

	public static HttpHeaders setBearerAuthorization(HttpHeaders httpHeaders, String username) {

		Date expirationDate = new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME);

		String tokenJWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, Constants.TOKEN_SECRET_KEY)
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
					.setSigningKey(Constants.TOKEN_SECRET_KEY)
					.parseClaimsJws(authorization.replace(Constants.TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException();
		} catch (JwtException e) {
			throw new InvalidTokenException();
		}

	}

}