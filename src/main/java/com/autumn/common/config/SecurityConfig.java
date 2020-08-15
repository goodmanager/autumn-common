package com.autumn.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 所有请求都需要签名,但可以不需要access token
 */
@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityConfig {

	// 白名单,不需要签名和access token
	private List<String> excludedSignAndTokenUrl;

	// 不需要 access token
	private List<String> excludedTokenUrl;

	// 需要特殊处理的url
	private List<String> excludeExtraUrl;

	// aes key
	private String aesKey;

	// sign key
	private String appId;
	private String appSecret;

	// 请求过期时间
	private int requestExpired;

	// jwt配置
	private Map<String, String> jwt;
}
