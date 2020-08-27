package com.ccs.creditcardapply.service.impl;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ccs.creditcardapply.dto.ApplicationDTO;
import com.ccs.creditcardapply.entity.Application;
import com.ccs.creditcardapply.entity.Customer;
import com.ccs.creditcardapply.entity.Employee;
import com.ccs.creditcardapply.exception.CreditCardApplyException;
import com.ccs.creditcardapply.repository.ApplicationRepository;
import com.ccs.creditcardapply.repository.CustomerRepository;
import com.ccs.creditcardapply.repository.EmployeeRepository;
import com.ccs.creditcardapply.service.EmailService;

/*
 * Test class for CreditCardApplyServiceImpl
 */
@ExtendWith(MockitoExtension.class)
public class CreditCardApplyServiceImplTests {

	@InjectMocks
	private CreditCardApplyServiceImpl creditCardApplyServiceImpl;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@Mock
	private ApplicationRepository applicationRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * This method builds and return a employee object
	 * @return employee object
	 */
	private Employee getEmployee() {
		return Employee.builder()
				.employeeId(new Long(1))
				.userId("employee")
				.password("password")
				.build();
	}
	
	/**
	 * This method builds and return a customer object
	 * @return customer object
	 */
	private Customer getCustomer() {
		return Customer.builder()
				.customerId(new Long(1))
				.userId("customer")
				.password("password")
				.build();
	}
	
	/**
	 * This method builds and return a application object
	 * @return application object
	 */
	private Application getApplication() {
		return Application.builder()
				.applicationId(new Long(1))
				.build();
		
	}
	
	/**
	 * This method tests saveCustomer in CreditCardApplyServiceImpl
	 */
	@Test
	public void testSaveCustomer() {
		when(customerRepository.save(any())).thenReturn(getCustomer());
		Customer customer = creditCardApplyServiceImpl.saveCustomer(getInputCustomer());
		assertNotNull(customer);
	}

	/**
	 * This method builds and return a customer object
	 * @return customer object
	 */
	private Customer getInputCustomer() {
		return Customer.builder().userId("customer").password("password").build();
	}

	/**
	 * This method tests saveEmployee in CreditCardApplyServiceImpl
	 */
	@Test
	public void testSaveEmployee() {
		when(employeeRepository.save(any())).thenReturn(getEmployee());
		Employee employee = creditCardApplyServiceImpl.saveEmployee(getInputEmployee());
		assertNotNull(employee);
	}
	
	/**
	 * This method builds and return a employee object
	 * @return employee object
	 */
	private Employee getInputEmployee() {
		return Employee.builder().userId("employee").password("password").build();
	}
	
	/**
	 * This method is a positive test case for saveApplication method in CreditCardApplyServiceImpl
	 */
	@Test
	public void testSaveApplication() {
		when(customerRepository.findByCustomerId(new Long(1))).thenReturn(Optional.of(getCustomer()));
		when(employeeRepository.findByEmployeeId(new Long(2))).thenReturn(Optional.of(getEmployee()));
		when(applicationRepository.save(any())).thenReturn(getApplication());
		Application application = creditCardApplyServiceImpl.saveApplication(getApplicationDTO());
		assertNotNull(application);
	}
	
	/**
	 * This method is a negative test case for saveApplication method in CreditCardApplyServiceImpl
	 */
	@Test
	public void testSaveApplicationApplicationNull() {
		assertThrows(CreditCardApplyException.class,() -> {creditCardApplyServiceImpl.saveApplication(null);});
	}
	
	/**
	 * This method builds and returns the ApplicationDTO object 
	 * @return applicationDTO 
	 */
	private ApplicationDTO getApplicationDTO() {
		return ApplicationDTO.builder()
				.customerId(new Long(1))
				.employeeId(new Long(2))
				.emailId("selvimandali2096@gmail.com")
				.mobileNumber("9848152565")
				.fullName("selvi")
				.fatherName("satya")
				.dateOfBirth("11-20-1996")
				.maritalStatus("single")
				.citizenShip("India")
				.residentialStatus("temp")
				.panCard("ABDCE1234F")
				.profession("pa")
				.companyName("cts")
				.designation("pa")
				.grossAnualIncome(new Float(5.5))
				.residanceAddress("add1")
				.officeAddress("add2")
				.build();
	}

}
