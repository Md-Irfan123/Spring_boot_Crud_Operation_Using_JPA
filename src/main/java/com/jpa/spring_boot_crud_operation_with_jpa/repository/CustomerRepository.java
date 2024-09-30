package com.jpa.spring_boot_crud_operation_with_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.spring_boot_crud_operation_with_jpa.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	/*
	 * 
	 * it will generate custom query to fetch customer by email
	 * @param email
	 * @return
	 */
	public Customer findByEmail(String email);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
	
	
	/*
	 * 
	 * This is a custom query written by a programmer
	 * @param address
	 * @return
	 */
	
	@Query(value = "select c from Customer c where c.address=?1")
	public Customer getCustomerByAddress(String address);
	
	@Transactional
	@Modifying
	@Query(value="Delete from Customer c Where c.id=?1")
	public void deleteCustomerById(int customerId);
	
	
//	@Transactional
//	@Modifying
//	@Query(value="Delete from Customer  Where id=?1, nativeQuery=true")
//	public void deleteCustomerByIdRepository(int customerId);
	
	

}
