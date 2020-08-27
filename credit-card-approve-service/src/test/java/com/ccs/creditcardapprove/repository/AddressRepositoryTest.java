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

import com.ccs.creditcardapprove.entity.Address;
import com.ccs.creditcardapprove.repository.AddressRepository;

/*
 * test class for AddressRepository
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class AddressRepositoryTest {
	
	@Autowired
	private AddressRepository addressRepository;
	
	private Address savedAddress;

	/**
	 * This method is called before each and every test case executes, here we are pre-loading the data into database 
	 * executing the test cases
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Address address = Address.builder()
				.officeAddress("testOfficeAddress")
				.residanceAddress("testResidanceAddress")
				.build();
		this.savedAddress=addressRepository.save(address);
		assertNotNull(savedAddress);
	}

	/**
	 * This method is a test case for AddressRepository
	 */
	@Test
	void test() {
		Optional<Address> fetchedAddressOptional = addressRepository.findById(savedAddress.getAddressId());
		Address fetchedAddress = fetchedAddressOptional.get();
		assertEquals(fetchedAddress, savedAddress);
	}

}
