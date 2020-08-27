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

import com.ccs.creditcardapply.entity.Address;
import com.ccs.creditcardapply.entity.Application;
import com.ccs.creditcardapply.entity.ContactDetails;
import com.ccs.creditcardapply.entity.Customer;
import com.ccs.creditcardapply.entity.Employee;
import com.ccs.creditcardapply.entity.PersonalData;
import com.ccs.creditcardapply.entity.ProfessionalDetails;

/*
 * Test class for ApplicationRepository
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ApplicationRepositoryTest {
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	private Application savedApplication;
	
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
		Employee employee = Employee.builder()
				.userId("employeeTest")
				.password("password")
				.build();
		Address address = Address.builder()
				.officeAddress("testOfficeAddress")
				.residanceAddress("testResidanceAddress")
				.build();
		ContactDetails contactDetails = ContactDetails.builder()
				.emailId("emailId")
				.mobileNumber("mobileNumber")
				.fullName("fullName")
				.build();
		PersonalData personalData = PersonalData.builder()
				.fatherName("fatherName")
				.dateOfBirth("01-01-01")
				.maritalStatus("single")
				.citizenShip("India")
				.residentialStatus("permenant")
				.panCardNumber("ABCB1234E")
				.build();
		ProfessionalDetails professionalDetails = ProfessionalDetails.builder()
				.profession("profession")
				.companyName("companyName")
				.designation("designation")
				.grossAnualIncome(5.5f)
				.build();
		Application application = Application.builder()
				.address(address)
				.contactDetails(contactDetails)
				.customer(customer)
				.employee(employee)
				.personalData(personalData)
				.professionalDetails(professionalDetails)
				.build();
		this.savedApplication = applicationRepository.save(application);
		assertNotNull(savedApplication);
	}

	/**
	 * This method is a test case for ApplicationRepository
	 */
	@Test
	void testFindByApplicationId() {
		Optional<Application> fetchedApplicationOptional = applicationRepository.findByApplicationId(savedApplication.getApplicationId());
		Application fetchedApplication = fetchedApplicationOptional.get();
		assertEquals(fetchedApplication, savedApplication);
	}

}
