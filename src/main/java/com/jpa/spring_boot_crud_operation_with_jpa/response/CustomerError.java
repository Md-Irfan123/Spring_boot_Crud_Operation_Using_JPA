package com.jpa.spring_boot_crud_operation_with_jpa.response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component

/*
 * 
 * This is a Generic class to response the PostMan API
 * 
 */
public class CustomerError<T> {
	
	public HttpStatus errorCode;
	public String statusMsg;
	public String description;
	

}
