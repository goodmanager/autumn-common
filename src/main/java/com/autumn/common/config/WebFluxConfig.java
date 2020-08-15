package com.autumn.common.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("i18n/messages", "error/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		return messageSource;
	}

	@Bean
	@LoadBalanced
	public WebClient createWebClient() {
		return WebClient.builder().build();
	}

}
