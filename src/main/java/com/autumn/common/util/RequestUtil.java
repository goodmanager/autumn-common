package com.autumn.common.util;

import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;

class RequestUtil {

	private RequestUtil() {
	}

	public static String httpGet(WebClient webClient, String url, Map<String, String> headers,
	                             Map<String, String> query) {
		WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClient.get().uri(url, query);
		if (headers != null) {
			requestHeadersSpec.headers(httpHeaders -> {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpHeaders.add(entry.getKey(), entry.getValue());
				}
			});
		}
		return requestHeadersSpec.retrieve().bodyToMono(String.class).block(Duration.ofSeconds(3));
	}

	public static String httpPostFormBody(WebClient webClient, String url, Map<String, String> headers,
	                                      Map<String, String> body) {
		WebClient.RequestBodySpec requestBodySpec =
			webClient.post().contentType(MediaType.APPLICATION_FORM_URLENCODED);
		if (body != null) {
			MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
			for (Map.Entry<String, String> entry : body.entrySet()) {
				multiValueMap.add(entry.getKey(), entry.getValue());
			}
			requestBodySpec.body(BodyInserters.fromFormData(multiValueMap));
		}
		return sendPostRequestWithHeaders(requestBodySpec, headers);
	}

	public static String httpPostFormParams(WebClient webClient, String url, Map<String, String> headers,
	                                        Map<String, String> params) {
		WebClient.RequestBodySpec requestBodySpec =
			webClient.post().uri(url, params).contentType(MediaType.APPLICATION_FORM_URLENCODED);
		return sendPostRequestWithHeaders(requestBodySpec, headers);
	}

	public static String httpPostJson(WebClient webClient, String url, Map<String, String> headers,
	                                  Map<String, String> body) {
		WebClient.RequestBodySpec requestBodySpec = webClient.post().contentType(MediaType.APPLICATION_JSON);
		if (body != null) {
			MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
			for (Map.Entry<String, String> entry : body.entrySet()) {
				multiValueMap.add(entry.getKey(), entry.getValue());
			}
			requestBodySpec.body(BodyInserters.fromFormData(multiValueMap));
		}
		return sendPostRequestWithHeaders(requestBodySpec, headers);
	}

	private static String sendPostRequestWithHeaders(WebClient.RequestBodySpec requestBodySpec,
	                                                 Map<String, String> headers) {
		if (headers != null) {
			requestBodySpec.headers(httpHeaders -> {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpHeaders.add(entry.getKey(), entry.getValue());
				}
			});
		}
		return requestBodySpec.retrieve().bodyToMono(String.class).block(Duration.ofSeconds(3));
	}
}
