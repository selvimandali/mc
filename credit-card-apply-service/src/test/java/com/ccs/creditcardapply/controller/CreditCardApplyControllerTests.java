package com.ccs.creditcardapply.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ccs.creditcardapply.dto.ApplicationDTO;
import com.ccs.creditcardapply.entity.Application;
import com.ccs.creditcardapply.entity.Customer;
import com.ccs.creditcardapply.entity.Employee;
import com.ccs.creditcardapply.service.CreditCardApplyService;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Test class for CreditCardApplyController
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CreditCardApplyControllerTests {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private CreditCardApplyController creditCardApplyController;

	@Mock
	private CreditCardApplyService creditCardApplyService;
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(creditCardApplyController).build();
	}
	/**
	 * This method builds and return a employee object
	 * @return employee object
	 */
	private Employee getEmployee() {
		return Employee.builder()
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
				.userId("customer")
				.password("password")
				.build();
	}
	
	/**
	 * This method builds and return a application object
	 * @return application object
	 */
	private Application getApplication() {
		return Application.builder().build();
	}
	
	/**
	 * This method is a positive test case for saveEmployee method in CreditCardApplyController class
	 * @throws Exception
	 */
	@Test
	public void testSaveEmployee() throws Exception {
		when(creditCardApplyService.saveEmployee(getEmployee())).thenReturn(getEmployee());
		this.mockMvc
		.perform(post("/saveEmployee").contentType(MediaType.APPLICATION_JSON)
		           .content("{ \"userId\": \"employee\", \"password\": \"password\" }") 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isOk());
	}
	
	/**
	 * This method is a negative test case for saveEmployee method in CreditCardApplyController class
	 * @throws Exception
	 */
	@Test
	public void testSaveEmployeeInternalServerError() throws Exception {
		this.mockMvc
		.perform(post("/saveEmployee").contentType(MediaType.APPLICATION_JSON)
		           .content("{ \"userId\": \"employee\", \"password\": \"password\" }") 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isInternalServerError());
	}
	
	/**
	 * This method is a positive test case for saveCustomer method in CreditCardApplyController class
	 * @throws Exception
	 */
	@Test
	public void testSaveCustomer() throws Exception {
		when(creditCardApplyService.saveCustomer(getCustomer())).thenReturn(getCustomer());
		this.mockMvc
		.perform(post("/saveCustomer").contentType(MediaType.APPLICATION_JSON)
		           .content("{ \"userId\": \"customer\", \"password\": \"password\" }") 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isOk());
	}
	
	/**
	 * This method is a negative test case for saveCustomer method in CreditCardApplyController class
	 * @throws Exception
	 */
	@Test
	public void testSaveCustomerInternalServerError() throws Exception {
		this.mockMvc
		.perform(post("/saveCustomer").contentType(MediaType.APPLICATION_JSON)
		           .content("{ \"userId\": \"customer\", \"password\": \"password\" }") 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isInternalServerError());
	}
	
	/**
	 * This method is a positive test case for saveApplication method in CreditCardApplyController class
	 * @throws Exception
	 */
	@Test
	public void testSaveApplication() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		when(creditCardApplyService.saveApplication(new ApplicationDTO())).thenReturn(new Application());
		this.mockMvc
		.perform(post("/apply").contentType(MediaType.APPLICATION_JSON)
		           .content(objectMapper.writeValueAsString(getApplication())) 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isOk());
	}
	
	/**
	 * This method is a negative test case for saveApplication method in CreditCardApplyController class
	 * @throws Exception
	 */
	@Test
	public void testSaveApplicationInternalServerError() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		this.mockMvc
		.perform(post("/apply").contentType(MediaType.APPLICATION_JSON)
		           .content(objectMapper.writeValueAsString(getApplication())) 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isInternalServerError());
	}

}
