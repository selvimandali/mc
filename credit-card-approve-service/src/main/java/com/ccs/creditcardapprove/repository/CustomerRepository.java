package com.ccs.creditcardapprove.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccs.creditcardapprove.entity.Customer;

/*
 * CustomerRepository is an interface extending JpaRepository interface for maintaining user defined method for 
 * database manipulations. 
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	/**
	 * This method fetches and returns data of the customer with input customerId
	 * @param customerId
	 * @return Fetched data from the database wrapped in Optional object
	 */
	Optional<Customer> findByCustomerId(Long customerId);

}
