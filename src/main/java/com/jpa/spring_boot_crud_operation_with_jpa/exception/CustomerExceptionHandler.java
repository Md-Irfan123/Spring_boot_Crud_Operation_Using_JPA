package com.jpa.spring_boot_crud_operation_with_jpa.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;





@RestControllerAdvice
public class CustomerExceptionHandler {
	
	
	@ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(CustomerNotFoundException ex) {
	
	 Map<String, Object> errorResponse =  new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("errorCode", HttpStatus.NOT_FOUND.value());


        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	
	
}
}


