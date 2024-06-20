package com.domobility.wavemarket.apiprocessor.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("message", status.name());

		// Get all errors
		List<String> errors = ex.getBindingResult().getAllErrors().stream().map(this::createErrorMessage)
				.collect(Collectors.toList());

		body.put("errors", errors);
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, headers, status);

	}

	private String createErrorMessage(ObjectError error) {
		StringBuilder builder = new StringBuilder(error.getDefaultMessage());
		if (error instanceof FieldError) {
			FieldError fieldError = (FieldError) error;
			builder.append(" - ");
			builder.append(fieldError.getField());
			builder.append(":");
			builder.append(fieldError.getRejectedValue());
		}
		return builder.toString();
	}

}