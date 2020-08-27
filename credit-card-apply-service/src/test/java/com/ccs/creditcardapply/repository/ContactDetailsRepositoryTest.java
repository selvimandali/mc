package com.ccs.creditcardapply.repository;

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

import com.ccs.creditcardapply.entity.ContactDetails;

/*
 * Test class for ContactDetailsRepository
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ContactDetailsRepositoryTest {

	@Autowired
	private ContactDetailsRepository contactDetailsRepository;
	
	private ContactDetails savedContactDetails;
	
	/**
	 * This method is called before each and every test case executes, here we are pre-loading the data into database 
	 * executing the test cases
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		ContactDetails contactDetails = ContactDetails.builder()
				.emailId("emailId")
				.mobileNumber("mobileNumber")
				.fullName("fullName")
				.build();
		this.savedContactDetails=contactDetailsRepository.save(contactDetails);
		assertNotNull(savedContactDetails);
	}
	
	/**
	 * This method is a test case for contactDetailsRepository
	 */
	@Test
	void test() {
		Optional<ContactDetails> fetchedContactDetailsOptional = contactDetailsRepository.findById(savedContactDetails.getContactDetailsId());
		ContactDetails fetchedContactDetails=fetchedContactDetailsOptional.get();
		assertEquals(fetchedContactDetails, savedContactDetails);
	}

}
