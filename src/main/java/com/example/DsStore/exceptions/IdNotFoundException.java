package com.example.DsStore.exceptions;

public class IdNotFoundException extends RuntimeException {
	String resourceName;
	String fieldName;
	long fieldValue;
	public IdNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
