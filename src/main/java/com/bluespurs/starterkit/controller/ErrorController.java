package com.bluespurs.starterkit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.bluespurs.starterkit.exception.BadHttpResponseExcpetion;
import com.bluespurs.starterkit.exception.ProductNotFoundException;

@ControllerAdvice
public class ErrorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);
	
	public static final String PRODUCT_NOT_FOUND = "Product not found: ";
	public static final String BAD_HTTP_REQUEST = "Bad HTTP request: ";
	public static final String BAD_HTTP_RESPONSE = "Bad HTTP response: ";
	public static final String MISSED_REQUEST_PARAMETER = "Missed request parameter: ";
	public static final String FORBIDDEN = "Forbidden, chek API keys: ";

	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public String productNotFound(HttpServletRequest request, ProductNotFoundException exception) {
		LOGGER.error(PRODUCT_NOT_FOUND + exception.getName());
		return PRODUCT_NOT_FOUND + exception.getName();
	}
	
	@ExceptionHandler(BadHttpResponseExcpetion.class)
	@ResponseBody
	public String badResponseStatus(HttpServletRequest request, HttpServletResponse response, BadHttpResponseExcpetion exception) {
		LOGGER.error(BAD_HTTP_RESPONSE + exception.getHttpStatus());
		response.setStatus(exception.getHttpStatus().value());
		return exception.getHttpStatus().getReasonPhrase();
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String missingRequestParameter(HttpServletRequest request, MissingServletRequestParameterException exception) {
		LOGGER.error(MISSED_REQUEST_PARAMETER + exception.getMessage());
		return exception.getMessage();
	}
	
	
	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseBody
	public String forbidden(HttpServletRequest request, HttpServletResponse response, HttpClientErrorException exception) {
		LOGGER.error(exception.getMessage());
		response.setStatus(exception.getRawStatusCode());
		if (exception.getRawStatusCode() == HttpStatus.BAD_REQUEST.value())
			return BAD_HTTP_REQUEST + exception.getMessage();
		return FORBIDDEN + exception.getMessage();
	}

}
