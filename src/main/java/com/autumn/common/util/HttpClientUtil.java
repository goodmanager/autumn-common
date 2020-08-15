package com.autumn.common.util;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class HttpClientUtil {

	private HttpClientUtil() {
	}

	public static String httpGet(String url, Map<String, String> headers, Map<String, String> query) {
		WebClient webClient = WebClient.builder().build();
		return RequestUtil.httpGet(webClient, url, headers, query);
	}

	public static String httpPostFormBody(String url, Map<String, String> headers, Map<String, String> body) {
		WebClient webClient = WebClient.create(url);
		return RequestUtil.httpPostFormBody(webClient, url, headers, body);
	}

	public static String httpPostFormParams(String url, Map<String, String> headers, Map<String, String> params) {
		WebClient webClient = WebClient.create(url);
		return RequestUtil.httpPostFormParams(webClient, url, headers, params);
	}

	public static String httpPostJson(String url, Map<String, String> headers, Map<String, String> body) {
		WebClient webClient = WebClient.create(url);
		return RequestUtil.httpPostJson(webClient, url, headers, body);
	}
}
