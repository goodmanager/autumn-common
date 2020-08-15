package com.autumn.common.exception;

/**
 * 业务异常
 *
 * @author felix
 */
public class BusinessException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8175638207304045557L;

	private int errorCode;

	private int httpStatus;

	private Object[] objects;

	public BusinessException(int errorCode, Object... objects) {
		this.errorCode = errorCode;
		this.httpStatus = 500;
		this.objects = objects;
	}

	public BusinessException(int errorCode, int httpStatus, Object... objects) {
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
