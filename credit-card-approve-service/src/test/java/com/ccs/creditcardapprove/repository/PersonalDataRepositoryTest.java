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

import com.ccs.creditcardapprove.entity.PersonalData;
import com.ccs.creditcardapprove.repository.PersonalDataRepository;

/*
 * Test class for PersonalDataRepository
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class PersonalDataRepositoryTest {
	
	@Autowired
	private PersonalDataRepository personalDataRepository;
	
	private PersonalData savedPersonalData;

	/**
	 * This method is called before each and every test case executes, here we are pre-loading the data into database 
	 * executing the test cases
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PersonalData personalData = PersonalData.builder()
				.fatherName("fatherName")
				.dateOfBirth("01-01-01")
				.maritalStatus("single")
				.citizenShip("India")
				.residentialStatus("permenant")
				.panCardNumber("ABCB1234E")
				.build();
		this.savedPersonalData = personalDataRepository.save(personalData);
		assertNotNull(savedPersonalData);
	}

	/**
	 * This method is a test case for PersonalDataRepository
	 */
	@Test
	void test() {
		Optional<PersonalData> fetchedPersonalDataOptional = personalDataRepository.findById(savedPersonalData.getPersonalDataId());
		PersonalData fetchedPersonalData = fetchedPersonalDataOptional.get();
		assertEquals(fetchedPersonalData, savedPersonalData);
	}

}
