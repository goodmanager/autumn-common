package com.autumn.common.exception;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 3183257827849708210L;

	private int errorCode;

	private int httpStatus;

	private Object[] objects;

	public ApplicationException(int errorCode, int httpStatus, Object... objects) {
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.objects = objects;
	}

	public ApplicationException(int errorCode, Object... objects) {
		this.errorCode = errorCode;
		this.httpStatus = 500;
		this.objects = objects;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Object[] getObjects() {
		return objects;
	}

	public void setObjects(Object[] objects) {
		this.objects = objects;
	}
}
