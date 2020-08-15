package com.autumn.common.exception;

import com.autumn.common.constant.ErrorCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import com.autumn.common.response.ResponseResult;
import com.autumn.common.util.MessageSourceUtil;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private MessageSourceUtil messageSourceUtils;

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = {BusinessException.class})
	public Mono<ResponseResult> catchBusinessException(ServerWebExchange exchange, BusinessException ex) {
		String message = "";
		if (null == ex.getObjects()) {
			message = messageSourceUtils.getMessage(exchange, String.valueOf(ex.getErrorCode()));
		} else {
			message =
				messageSourceUtils.getMessage(
					exchange, String.valueOf(ex.getErrorCode()), ex.getObjects());
		}
		logger.error(ex.getMessage(), ex);
		return ResponseResult.error(ex.getErrorCode(), message);
	}

	@ExceptionHandler(value = {ApplicationException.class})
	public Mono<ResponseResult> catchApplicationException(ServerWebExchange exchange, ApplicationException ex) {
		String message = "";
		if (null == ex.getObjects()) {
			message = messageSourceUtils.getMessage(exchange, String.valueOf(ex.getErrorCode()));
		} else {
			message =
				messageSourceUtils.getMessage(
					exchange, String.valueOf(ex.getErrorCode()), ex.getObjects());
		}
		logger.error(ex.getMessage(), ex);
		return ResponseResult.error(ex.getErrorCode(), message);
	}

	@ExceptionHandler(Exception.class)
	public Mono<ResponseResult> catchException(ServerWebExchange webExchange, Exception ex) {
		logger.error(ex.getMessage(), ex);
		return ResponseResult.error(ErrorCodeException.FAILED.getErrorCode(), ErrorCodeException.FAILED.getMessage());
	}
}
