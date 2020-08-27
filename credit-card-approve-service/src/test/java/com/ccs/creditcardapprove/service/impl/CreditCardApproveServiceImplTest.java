package com.ccs.creditcardapprove.service.impl;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ccs.creditcardapprove.dto.ActionDTO;
import com.ccs.creditcardapprove.dto.CustomerDTO;
import com.ccs.creditcardapprove.dto.EmployeeDTO;
import com.ccs.creditcardapprove.entity.Application;
import com.ccs.creditcardapprove.entity.ContactDetails;
import com.ccs.creditcardapprove.entity.Customer;
import com.ccs.creditcardapprove.entity.Employee;
import com.ccs.creditcardapprove.exception.CreditCardApproveException;
import com.ccs.creditcardapprove.repository.ApplicationRepository;
import com.ccs.creditcardapprove.repository.CustomerRepository;
import com.ccs.creditcardapprove.repository.EmployeeRepository;
import com.ccs.creditcardapprove.service.EmailService;

/*
 * Test class for CreditCardApproveServiceImpl
 */
public class CreditCardApproveServiceImplTest {
	
	@InjectMocks
	private CreditCardApproveServiceImpl creditCardApproveServiceImpl;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private ApplicationRepository applicationRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private EmployeeRepository employeeRepository;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * This method is a positive updateApplicationStatus method in CreditCardApproveServiceImpl
	 */
	@Test
	public void testUpdateApplicationStatus() {
		Application savedApplication = new Application();
		ContactDetails contactDetails = new ContactDetails();
		contactDetails.setEmailId("abc");
		savedApplication.setContactDetails(contactDetails);
		when(applicationRepository.findByApplicationId(new Long(1))).thenReturn(Optional.of(savedApplication));
		when(applicationRepository.save(any())).thenReturn(savedApplication);
		Application application = creditCardApproveServiceImpl.updateApplicationStatus(ActionDTO.builder().applicationId(new Long(1)).status("approved").build());
		assertNotNull(application);
	}
	
	/**
	 * This method is a negative updateApplicationStatus method in CreditCardApproveServiceImpl
	 */
	@Test
	public void testUpdateApplicationStatusCreditCardApproveException() {
		Application savedApplication = new Application();
		when(applicationRepository.findByApplicationId(new Long(1))).thenReturn(Optional.of(savedApplication));
		when(applicationRepository.save(any())).thenReturn(savedApplication);
		assertThrows(CreditCardApproveException.class,() -> {creditCardApproveServiceImpl.updateApplicationStatus(ActionDTO.builder().applicationId(new Long(1)).status("approved").build());});
	}
	
	/**
	 * This method is a negative updateApplicationStatus method in CreditCardApproveServiceImpl
	 */
	@Test
	public void testUpdateApplicationStatusContactDetailsCreditCardApproveException() {
		Application savedApplication = new Application();
		ContactDetails contactDetails = new ContactDetails();
		savedApplication.setContactDetails(contactDetails);
		when(applicationRepository.findByApplicationId(new Long(1))).thenReturn(Optional.of(savedApplication));
		when(applicationRepository.save(any())).thenReturn(savedApplication);
		assertThrows(CreditCardApproveException.class,() -> {creditCardApproveServiceImpl.updateApplicationStatus(ActionDTO.builder().applicationId(new Long(1)).status("approved").build());});
	}
	
	/**
	 * This method is a test case for getApplication method in CreditCardApproveServiceImpl
	 */
	@Test
	public void testGetApplication() {
		when(applicationRepository.findByApplicationId(new Long(1))).thenReturn(Optional.of(new Application()));
		Application application = creditCardApproveServiceImpl.getApplication(new Long(1));
		assertNotNull(application);
	}

	/**
	 * This method is a test case for getApplicationsSubmittedByCustomer method in CreditCardApproveServiceImpl
	 */
	@Test
	public void testGetApplicationsSubmittedByCustomer() {
		Customer customer = new Customer();
		Set<Application> set = new HashSet<Application>();
		set.add(new Application());
		customer.setApplications(set);
		when(customerRepository.findByCustomerId(new Long(1))).thenReturn(Optional.of(customer));
		CustomerDTO customerDTO = creditCardApproveServiceImpl.getApplicationsSubmittedByCustomer(new Long(1));
		assertNotNull(customerDTO);
	}

	/**
	 * This method is a test case for getApplicationsAssigendToEmployee method in CreditCardApproveServiceImpl
	 */
	@Test
	public void testGetApplicationsAssigendToEmployee() {
		Employee employee = new Employee();
		Set<Application> set = new HashSet<Application>();
		set.add(new Application());
		employee.setApplications(set);
		when(employeeRepository.findByEmployeeId(new Long(1))).thenReturn(Optional.of(employee));
		EmployeeDTO employeeDTO = creditCardApproveServiceImpl.getApplicationsAssigendToEmployee(new Long(1));
		assertNotNull(employeeDTO);
	}

}
