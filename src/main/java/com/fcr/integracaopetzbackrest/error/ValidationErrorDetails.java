package com.fcr.integracaopetzbackrest.error;

public class ValidationErrorDetails extends ErrorDetails {

	private String field;
	private String fieldMessage;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldMessage() {
		return fieldMessage;
	}

	public void setFieldMessage(String fieldMessage) {
		this.fieldMessage = fieldMessage;
	}
}
