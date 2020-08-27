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

import com.ccs.creditcardapply.entity.ProfessionalDetails;

/*
 * Test class for ProfessionalDetailsRepository
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProfessionalDetailsRepositoryTest {

	@Autowired
	private ProfessionalDetailsRepository professionalDetailsRepository;
	
	private ProfessionalDetails savedprofessionalDetails;
	
	/**
	 * This method is called before each and every test case executes, here we are pre-loading the data into database 
	 * executing the test cases
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		ProfessionalDetails professionalDetails = ProfessionalDetails.builder()
				.profession("profession")
				.companyName("companyName")
				.designation("designation")
				.grossAnualIncome(5.5f)
				.build();
		savedprofessionalDetails = professionalDetailsRepository.save(professionalDetails);
		assertNotNull(savedprofessionalDetails);
		
	}
	
	/**
	 * This method is a test case for EmployeeRepository
	 */
	@Test
	void test() {
		Optional<ProfessionalDetails> fetchedProfessionalDetailsOptional = professionalDetailsRepository.findById(savedprofessionalDetails.getProfessionalDetailsId());
		ProfessionalDetails fetchedProfessionalDetail = fetchedProfessionalDetailsOptional.get();
		assertEquals(fetchedProfessionalDetail, savedprofessionalDetails);
	}

}
