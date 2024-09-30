package com.jpa.spring_boot_crud_operation_with_jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jpa.spring_boot_crud_operation_with_jpa.dto.Customer;
import com.jpa.spring_boot_crud_operation_with_jpa.exception.CustomerNotFoundException;
import com.jpa.spring_boot_crud_operation_with_jpa.repository.CustomerRepository;



@Service
public class CustomerService {
	
	 @Autowired
	    private CustomerRepository repo; // Repository for interacting with the database
	    


	    
	    public Customer createCustomerData(Customer customer) {
	    	return repo.save(customer);
	    }

	    public List<Customer> findAllCustomerData() {
	        return repo.findAll();
	    }

	    public Customer  getById(Integer id) throws CustomerNotFoundException {
	        return repo.findById(id)
	                .orElseThrow(() -> new CustomerNotFoundException("Entity not found with id " + id));
	    }

	    public void deleteById(Integer id) throws CustomerNotFoundException {
	    	Customer customer = repo.findById(id)
	                .orElseThrow(() -> new CustomerNotFoundException("Entity not found with id " + id));
	        repo.delete(customer); // Delete the user
	    }

	    public Customer updateCustomerData(Integer id, Customer customer) throws CustomerNotFoundException {
	    	Customer  userToUpdate = repo.findById(id)
	                .orElseThrow(() -> new CustomerNotFoundException("Entity not found with id " + id));

	        // Update fields of userToUpdate with new data from the user parameter
	        userToUpdate.setUsername(customer.getUsername());
	        userToUpdate.setEmail(customer.getEmail());
	        userToUpdate.setAge(customer.getAge());
	       userToUpdate.setAddress(customer.getAddress());
	      
	        

	        return repo.save(userToUpdate); // Save the updated user
	    }

	    // For Sorting 
	    public List<Customer> findCustomersWithSorting(String field) {
	        return repo.findAll(Sort.by(Sort.Direction.ASC, field));
	    }

	    // For Pagination
	    public Page<Customer> findCustomersWithPagination(int offset, int pageSize) {
	        return repo.findAll(PageRequest.of(offset, pageSize));
	    }
	}
	
	


