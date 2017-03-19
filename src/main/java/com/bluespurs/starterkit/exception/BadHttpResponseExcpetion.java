package com.bluespurs.starterkit.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception represents "HTTP response code is not OK" situation
 * and wraps HTTP status object
 */
public class BadHttpResponseExcpetion extends RuntimeException {
	private static final long serialVersionUID = 4897363457598136682L;
	HttpStatus httpStatus;
	String message;

	public BadHttpResponseExcpetion(HttpStatus httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = httpStatus.getReasonPhrase();
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
