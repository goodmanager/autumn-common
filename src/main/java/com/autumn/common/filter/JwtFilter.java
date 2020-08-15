package com.autumn.common.filter;

import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.autumn.common.config.SecurityConfig;
import com.autumn.common.constant.ErrorCodeException;
import com.autumn.common.constant.JwtClaimsKey;
import com.autumn.common.exception.ApplicationException;
import com.autumn.common.util.DateUtil;
import com.autumn.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

public class JwtFilter implements WebFilter {

	@Autowired
	private SecurityConfig securityConfig;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		if (securityConfig.getExcludedSignAndTokenUrl().contains(request.getPath().value())) {
			return chain.filter(exchange);
		} else {
			return checkAuth(exchange, chain);
		}
	}

	private Mono<Void> checkAuth(ServerWebExchange exchange, WebFilterChain chain) {
		HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
		String accessToken = httpHeaders.getFirst(JwtClaimsKey.X_ACCESSTOKEN.getKey());
		Map<String, String> jwtConfig = securityConfig.getJwt();
		DecodedJWT claimMap = JwtUtil.verifyJwtToken(jwtConfig.get("secretKey"), accessToken);
		if (claimMap == null) {
			throw new ApplicationException(ErrorCodeException.ERROR_TOKEN.getErrorCode(), 400,
				ErrorCodeException.ERROR_TOKEN.getMessage());
		} else {
			String uid = claimMap.getClaim(JwtClaimsKey.X_Uid.getKey()).asString();
			String appId = claimMap.getClaim(JwtClaimsKey.X_AppId.getKey()).asString();
			if (uid.equalsIgnoreCase(httpHeaders.getFirst(JwtClaimsKey.X_Uid.getKey()))
				&& appId.equalsIgnoreCase(httpHeaders.getFirst(JwtClaimsKey.X_AppId.getKey()))) {
				return chain.filter(exchange);
			} else if (LocalDateTime.now().isAfter(DateUtil.toLocalDateTime(claimMap.getClaim(PublicClaims.EXPIRES_AT).asDate()))) {
				throw new ApplicationException(
					ErrorCodeException.EXPIRED_TOKEN.getErrorCode(), HttpStatus.UNAUTHORIZED.value(),
					ErrorCodeException.EXPIRED_TOKEN.getMessage());
			} else {
				throw new ApplicationException(ErrorCodeException.ERROR_TOKEN.getErrorCode(), 400,
					ErrorCodeException.ERROR_TOKEN.getMessage());
			}
		}
	}
}
