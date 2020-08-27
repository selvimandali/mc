package com.ccs.creditcardapprove.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ccs.creditcardapprove.entity.Customer;
import com.ccs.creditcardapprove.repository.CustomerRepository;

/*
 * Test class for CustomerRepositoryTest
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository customerRepository;

	private Customer savedCustomer;
	
	/**
	 * This method is called before each and every test case executes, here we are pre-loading the data into database 
	 * executing the test cases
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Customer customer = Customer.builder()
				.userId("customerTest")
				.password("password")
				.build();
		this.savedCustomer = customerRepository.save(customer);
		assertNotNull(savedCustomer);
	}

	/**
	 * This method is a test case for CustomerRepository
	 */
	@Test
	void testFindByCustomerId() {
		Optional<Customer> fetchedCustomerOptional = customerRepository.findByCustomerId(savedCustomer.getCustomerId());
		Customer fetchedCustomer = fetchedCustomerOptional.get();
		assertEquals(fetchedCustomer, savedCustomer);
	}

}
