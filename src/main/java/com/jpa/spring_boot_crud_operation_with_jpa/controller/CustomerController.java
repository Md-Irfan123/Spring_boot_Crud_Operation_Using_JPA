package com.jpa.spring_boot_crud_operation_with_jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.jpa.spring_boot_crud_operation_with_jpa.dto.Customer;

import com.jpa.spring_boot_crud_operation_with_jpa.service.CustomerService;


@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService dao;
	

	@PostMapping(value = "/saveCustomerData")
	public Customer saveCustomerController(@RequestBody Customer customer) {
		return  dao.createCustomerData(customer);
		
		
	}
	
	
	
	@GetMapping(value="/getCustomerById/{customerId}")
	public Customer getCustomerByIdController(@PathVariable("customerId") Integer customerId) {
		return dao.getById(customerId);
		 
		
	}
	
	@GetMapping(value="/getAllCustomer")
	public List<Customer> getAllCustomerController() {
		return dao.findAllCustomerData();
		
	}
	
	@DeleteMapping(value = "/deleteUserById/{id}")
	public void deleteByIdController(@PathVariable("id") Integer id) {
		
		
		 dao.deleteById(id);
	
		
	}
	
	

    @PutMapping("/{id}")
    public Customer updateCustomerDataController(@PathVariable("id") Integer id,  @RequestBody Customer customer) {
       
           return dao.updateCustomerData(id, customer);
       
    }
    
    @GetMapping(value="/{field}")
    public List<Customer> findUserDataWithSortingController(@PathVariable String field){
    	
    	 List<Customer> customer=dao.findCustomersWithSorting(field);
    	 return customer;
    


    	
    	

    	

    	
    	
    }
    
    
    @GetMapping(value="/Pagination/{offset}/{pageSize}")
    public Page<Customer> findProductWithPagination(@PathVariable int offset, @PathVariable int pageSize){
    	
	Page<Customer> allUser=dao.findCustomersWithPagination(offset, pageSize);
    	
	return allUser;

    	
    	
    	
    }
	 
	

}
