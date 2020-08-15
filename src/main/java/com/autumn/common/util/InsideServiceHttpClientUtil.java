package com.autumn.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class InsideServiceHttpClientUtil {

	@Autowired
	private WebClient webClient;

	public Mono<String> httpGet(String url, Map<String, String> headers, Map<String, String> query) {
		return Mono.just(RequestUtil.httpGet(webClient, url, headers, query));
	}

	public Mono<String> httpPostFormBody(String url, Map<String, String> headers, Map<String, String> body) {
		return Mono.just(RequestUtil.httpPostFormBody(webClient, url, headers, body));
	}

	public Mono<String> httpPostFormParams(String url, Map<String, String> headers, Map<String, String> params) {
		return Mono.just(RequestUtil.httpPostFormParams(webClient, url, headers, params));
	}

	public Mono<String> httpPostJson(String url, Map<String, String> headers, Map<String, String> body) {
		return Mono.just(RequestUtil.httpPostJson(webClient, url, headers, body));
	}
}
