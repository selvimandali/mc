package com.ccs.creditcardapprove.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.ccs.creditcardapprove.dto.ActionDTO;
import com.ccs.creditcardapprove.dto.CustomerDTO;
import com.ccs.creditcardapprove.dto.EmployeeDTO;
import com.ccs.creditcardapprove.entity.Application;
import com.ccs.creditcardapprove.service.CreditCardApproveService;


/*
 * Test class for CreditCardApproveController
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CreditCardApproveControllerTest {
	
	private MockMvc mockMvc;

	@InjectMocks
	private CreditCardApproveController creditCardApproveController;
	
	@Mock
	private CreditCardApproveService creditCardApproveService;
	
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(creditCardApproveController).build();
	}
	
	/**
	 * This method builds and return a applicationDTO object
	 * @return employee object
	 */
	private ActionDTO getActionDTO() {
		return ActionDTO.builder()
				.applicationId(new Long(1))
				.status("approved")
				.build();
	}

	/**
	 * This method is a positive test case updateApplicationStatus method in CreditCardApproveController
	 * @throws Exception
	 */
	@Test
	public void testUpdateApplicationStatus() throws Exception {
		when(creditCardApproveService.updateApplicationStatus(getActionDTO())).thenReturn(new Application());
		this.mockMvc.perform(put("/takeAction").contentType(MediaType.APPLICATION_JSON)
		           .content("{ \"applicationId\": 1, \"status\": \"approved\" }") 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isOk());
	}
	
	/**
	 * This method is a negative test case updateApplicationStatus method in CreditCardApproveController
	 * @throws Exception
	 */
	@Test
	public void testUpdateApplicationStatusInternalServerError() throws Exception {
		when(creditCardApproveService.updateApplicationStatus(getActionDTO())).thenReturn(null);
		this.mockMvc.perform(put("/takeAction").contentType(MediaType.APPLICATION_JSON)
		           .content("{ \"applicationId\": 1, \"status\": \"approved\" }") 
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isInternalServerError());
	}
	
	/**
	 * This method is a positive test case getAssignedApplicationsForEmployee method in CreditCardApproveController
	 * @throws Exception
	 */
	@Test
	public void testGetAssignedApplicationsForEmployee() throws Exception {
		when(creditCardApproveService.getApplicationsAssigendToEmployee(new Long(1))).thenReturn(new EmployeeDTO());
		this.mockMvc.perform(get("/employee/getAssignedApplications/1")).andExpect(status().isAccepted());
	}
	
	/**
	 * This method is a negative test case getAssignedApplicationsForEmployee method in CreditCardApproveController
	 * @throws Exception
	 */
	@Test
	public void testGetAssignedApplicationsForEmployeeInternalServerError() throws Exception {
		when(creditCardApproveService.getApplicationsAssigendToEmployee(new Long(1))).thenReturn(null);
		this.mockMvc.perform(get("/employee/getAssignedApplications/1")).andExpect(status().isInternalServerError());
	}

	/**
	 * This method is a positive test case getApplicationsSubmittesByCustomer method in CreditCardApproveController
	 * @throws Exception
	 */
	@Test
	public void testGetApplicationsSubmittedByCustomer() throws Exception {
		when(creditCardApproveService.getApplicationsSubmittedByCustomer(new Long(1))).thenReturn(new CustomerDTO());
		this.mockMvc.perform(get("/customer/getApplications/1")).andExpect(status().isAccepted());
	}
	
	/**
	 * This method is a negative test case getApplicationsSubmittesByCustomer method in CreditCardApproveController
	 * @throws Exception
	 */
	@Test
	public void testGetApplicationsSubmittedByCustomerInternalServerError() throws Exception {
		when(creditCardApproveService.getApplicationsSubmittedByCustomer(new Long(1))).thenReturn(null);
		this.mockMvc.perform(get("/customer/getApplications/1")).andExpect(status().isInternalServerError());
	}
	
	/**
	 * This method is a positive test case getApplication method in CreditCardApproveController
	 * @throws Exception
	 */
	@Test
	public void testGetApplication() throws Exception {
		when(creditCardApproveService.getApplication(new Long(1))).thenReturn(new Application());
		this.mockMvc.perform(get("/getApplication/1")).andExpect(status().isAccepted());
	}
	
	/**
	 * This method is a negative test case getApplication method in CreditCardApproveController
	 * @throws Exception
	 */
	@Test
	public void testGetApplicationInternalServerError() throws Exception {
		when(creditCardApproveService.getApplication(new Long(1))).thenReturn(null);
		this.mockMvc.perform(get("/getApplication/1")).andExpect(status().isInternalServerError());
	}

}
