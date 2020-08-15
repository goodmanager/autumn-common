package com.autumn.common.config;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.util.List;
import java.util.Locale;

@Component
public class LocaleResolver implements LocaleContextResolver {

	@Override
	public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
		String language = exchange.getRequest().getHeaders().getFirst("Accept-Language");
		Locale targetLocale = Locale.getDefault();
		List<String> referLang = exchange.getRequest().getQueryParams().get("lang");
		if (referLang != null && !referLang.isEmpty()) {
			String lang = referLang.get(0);
			targetLocale = Locale.forLanguageTag(lang);
		} else {
			targetLocale = Locale.forLanguageTag(language);
		}
		return new SimpleLocaleContext(targetLocale);
	}

	@Override
	public void setLocaleContext(ServerWebExchange exchange, LocaleContext localeContext) {
		throw new UnsupportedOperationException("Not Supported");
	}
}
