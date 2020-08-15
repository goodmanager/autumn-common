package com.autumn.common.response;

import com.autumn.common.constant.ErrorCodeException;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseResult implements Serializable {

	private static final long serialVersionUID = -4296742914196870305L;

	private int errorCode;

	private String message;

	private Pager pager;

	private Object records;

	private int httpStatus;

	public static Mono<ResponseResult> success(Mono<Pager> pager, Flux<?> list) {
		ResponseResult result = new ResponseResult();
		result.setHttpStatus(200);
		result.setMessage(ErrorCodeException.SUCCESS.getMessage());
		result.setErrorCode(ErrorCodeException.SUCCESS.getErrorCode());
		pager.subscribe((obj) -> {
			result.setPager(obj);
		});
		list.subscribe(
			new BaseSubscriber<Object>() {
				List<Object> objects = new ArrayList<>();

				@Override
				public void hookOnNext(Object ob) {
					objects.add(ob);
				}

				@Override
				public void hookOnComplete() {
					result.setRecords(objects);
					dispose();
				}
			});
		return Mono.just(result);
	}

	public static Mono<ResponseResult> success(Mono<Object> object) {
		ResponseResult result = new ResponseResult();
		result.setMessage(ErrorCodeException.SUCCESS.getMessage());
		result.setHttpStatus(200);
		result.setErrorCode(ErrorCodeException.SUCCESS.getErrorCode());
		return Mono.create(sink -> {
			object.subscribe((obj) -> {
				result.setRecords(obj);
				sink.success(result);
			});
		});
	}

	public static Mono<ResponseResult> success(Object object) {
		ResponseResult result = new ResponseResult();
		return Mono.create(
			sink -> {
				result.setRecords(object);
				result.setMessage(ErrorCodeException.SUCCESS.getMessage());
				result.setHttpStatus(200);
				result.setErrorCode(ErrorCodeException.SUCCESS.getErrorCode());
				sink.success(result);
			});
	}

	public static Mono<ResponseResult> error(int errorCode, String message) {
		ResponseResult result = new ResponseResult();
		return Mono.create(
			sink -> {
				result.setErrorCode(errorCode);
				result.setMessage(message);
				result.setHttpStatus(500);
				sink.success(result);
			});
	}

	public static Mono<ResponseResult> error(int errorCode, int httpStatus, String message) {
		ResponseResult result = new ResponseResult();
		return Mono.create(
			sink -> {
				result.setErrorCode(errorCode);
				result.setMessage(message);
				result.setHttpStatus(httpStatus);
				sink.success(result);
			});
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public Object getRecords() {
		return records;
	}

	public void setRecords(Object records) {
		this.records = records;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
}
