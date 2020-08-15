package com.autumn.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.autumn.common.constant.JwtClaimsKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	private JwtUtil() {
	}

	/**
	 * 创建jwt token
	 *
	 * @param secretKey
	 * @param payloadClaims
	 * @return
	 */
	public static String createJwtToken(String secretKey, Map<String, String> payloadClaims) {
		Date iatDate = new Date();
		// expire time
		LocalDateTime localTime = DateUtil.toLocalDateTime(iatDate);
		localTime.plusSeconds(Long.valueOf(payloadClaims.get("expired")));
		Date expiresDate = DateUtil.toDate(localTime);

		// header Map
		Map<String, Object> map = new HashMap<>();
		map.put("alg", "HS512");
		map.put("typ", "JWT");

		// build token
		return JWT.create().withHeader(map) // header
			.withClaim(PublicClaims.ISSUER, payloadClaims.get(JwtClaimsKey.X_AppId.getKey()))
			.withClaim(PublicClaims.AUDIENCE, payloadClaims.get(JwtClaimsKey.X_Uid.getKey()))
			.withIssuedAt(iatDate) // sign time
			.withExpiresAt(expiresDate) // expire time
			.sign(Algorithm.HMAC256(secretKey)); // signature

	}

	/**
	 * 验证jwt token
	 *
	 * @param secretKey
	 * @param jwtToken
	 * @return
	 */
	public static DecodedJWT verifyJwtToken(String secretKey, String jwtToken) {
		DecodedJWT jwt = null;
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey)).build();
			jwt = verifier.verify(jwtToken);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return jwt;
	}
}
