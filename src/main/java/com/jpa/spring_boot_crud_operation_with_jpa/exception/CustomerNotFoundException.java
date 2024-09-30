package com.jpa.spring_boot_crud_operation_with_jpa.exception;

public class CustomerNotFoundException extends RuntimeException {
	
	public CustomerNotFoundException(String message) {
		super(message);
		
	}

}
