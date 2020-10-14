package com.fcr.integracaopetzbackrest.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fcr.integracaopetzbackrest.error.ErrorDetails;
import com.fcr.integracaopetzbackrest.error.ResourceNotFoundDetails;
import com.fcr.integracaopetzbackrest.error.ResourceNotFoundException;
import com.fcr.integracaopetzbackrest.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ResourceNotFoundDetails rfnDetails = new ResourceNotFoundDetails();
		rfnDetails.setTimestamp(new Date().getTime());
		rfnDetails.setStatus(HttpStatus.NOT_FOUND.value());
		rfnDetails.setTitle("Resource not found");
		rfnDetails.setDetail(ex.getMessage());
		rfnDetails.setDeveloperMessage(ex.getClass().getName());
		return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		ValidationErrorDetails manvDetails = new ValidationErrorDetails();
		manvDetails.setTimestamp(new Date().getTime());
		manvDetails.setStatus(HttpStatus.BAD_REQUEST.value());
		manvDetails.setTitle("Field Validation Error");
		manvDetails.setDetail("Field Validation Error");
		manvDetails.setDeveloperMessage(ex.getClass().getName());
		manvDetails.setField(fields);
		manvDetails.setFieldMessage(fieldMessages);
		return new ResponseEntity<>(manvDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(new Date().getTime());
		errorDetails.setStatus(status.value());
		errorDetails.setTitle("Internal Exception");
		errorDetails.setDetail(ex.getMessage());
		errorDetails.setDeveloperMessage(ex.getClass().getName());
		return new ResponseEntity<>(errorDetails, headers, status);
	}
}
