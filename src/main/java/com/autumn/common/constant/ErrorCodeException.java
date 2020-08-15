package com.autumn.common.constant;

public enum ErrorCodeException {

	SUCCESS(0, "请求成功"),
	FAIL_REQUEST_PARAM(100100, "请求参数错误"),
	ERROR_TOKEN(100101, "错误的Access Token"),
	FAIL_HTTP_METHOD(100102, "不支持的请求类型"),
	EXPIRED_REQUEST(100103, "过期的请求"),
	FAIL_SIGN_CHECK(100104, "签名验证失败"),
	EXPIRED_TOKEN(100104, "过期的Access Token"),
	FAILED(999999, "请求失败");

	private int errorCode;
	private String message;

	public int getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	ErrorCodeException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
}
