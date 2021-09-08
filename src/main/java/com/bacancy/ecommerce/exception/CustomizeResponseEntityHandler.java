package com.bacancy.ecommerce.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizeResponseEntityHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

		ExceptionResponse er= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(er,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Object> handleUserNotExceptions(Exception ex, WebRequest request) {

		ExceptionResponse er= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	protected ResponseEntity<Object> handleProductNotExceptions(Exception ex, WebRequest request) {

		ExceptionResponse er= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	protected ResponseEntity<Object> handleCategoryNotExceptions(Exception ex, WebRequest request) {

		ExceptionResponse er= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	protected ResponseEntity<Object> handleOrderNotExceptions(Exception ex, WebRequest request) {

		ExceptionResponse er= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(er,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NotAccessAbleException.class)
	protected ResponseEntity<Object> handleNotAccessAbleExceptions(Exception ex, WebRequest request) {

		ExceptionResponse er= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(er,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(DuplicateException.class)
	protected ResponseEntity<Object> handleDuplicateExceptions(Exception ex, WebRequest request) {

		ExceptionResponse er= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(er,HttpStatus.ALREADY_REPORTED);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse er= new ExceptionResponse(new Date(), ex.getMessage(), ex.getBindingResult().toString());
		return new ResponseEntity(er,HttpStatus.BAD_REQUEST);	
	}
	
}
