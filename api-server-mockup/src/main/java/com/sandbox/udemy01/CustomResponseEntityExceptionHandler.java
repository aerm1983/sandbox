package com.sandbox.udemy01;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		
		ExceptionResponsePojo exceptionResponse = 
				new ExceptionResponsePojo( 
						new Date(), 
						ex.getMessage(), 
						request.getDescription(false) ) ;
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		
		ExceptionResponsePojo exceptionResponse = 
				new ExceptionResponsePojo( 
						new Date(), 
						ex.getMessage(), 
						request.getDescription(false) ) ;
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	
	

}
