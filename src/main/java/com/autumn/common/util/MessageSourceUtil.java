package com.autumn.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.autumn.common.config.LocaleResolver;

/**
 * @author felix
 */
@Component
public class MessageSourceUtil {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localeResolver;

	public String getMessage(ServerWebExchange exchange, String key, Object... params) {
		LocaleContext localeContext = localeResolver.resolveLocaleContext(exchange);
		return messageSource.getMessage(key, params, localeContext.getLocale());
	}
}
