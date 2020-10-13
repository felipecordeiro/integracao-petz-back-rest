package com.fcr.integracaopetzbackrest.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fcr.integracaopetzbackrest.error.ResourceNotFoundDetails;
import com.fcr.integracaopetzbackrest.error.ResourceNotFoundException;
import com.fcr.integracaopetzbackrest.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException) {
		ResourceNotFoundDetails rfnDetails = new ResourceNotFoundDetails();
		rfnDetails.setTimestamp(new Date().getTime());
		rfnDetails.setStatus(HttpStatus.NOT_FOUND.value());
		rfnDetails.setTitle("Resource not found");
		rfnDetails.setDetail(rfnException.getMessage());
		rfnDetails.setDeveloperMessage(rfnException.getClass().getName());
		return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException manvException) {

		List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		ValidationErrorDetails manvDetails = new ValidationErrorDetails();
		manvDetails.setTimestamp(new Date().getTime());
		manvDetails.setStatus(HttpStatus.BAD_REQUEST.value());
		manvDetails.setTitle("Field Validation Error");
		manvDetails.setDetail("Field Validation Error");
		manvDetails.setDeveloperMessage(manvException.getClass().getName());
		manvDetails.setField(fields);
		manvDetails.setFieldMessage(fieldMessages);
		return new ResponseEntity<>(manvDetails, HttpStatus.BAD_REQUEST);
	}
}
