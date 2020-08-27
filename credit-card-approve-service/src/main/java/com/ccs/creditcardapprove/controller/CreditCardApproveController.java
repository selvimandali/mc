package com.ccs.creditcardapprove.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ccs.creditcardapprove.dto.ActionDTO;
import com.ccs.creditcardapprove.dto.CustomerDTO;
import com.ccs.creditcardapprove.dto.EmployeeDTO;
import com.ccs.creditcardapprove.entity.Application;
import com.ccs.creditcardapprove.service.CreditCardApproveService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
/*
 * CreditCardApproveController provides the rest API for credit-card-approve service
 */

@Api
@RestController
@Slf4j
public class CreditCardApproveController {

	@Autowired
	private CreditCardApproveService creditCardApproveService;
	
	private static final String SOME_THING_WENT_WRONG = "some thing went wrong";
	
	/**
	 * This method is responsible for approving or rejecting a credit card application
	 * @param actionDTO
	 * @return updated application wrapped in ResponseEntity
	 */
	@PutMapping("/takeAction")
	public ResponseEntity<Object> updateApplicationStatus(@RequestBody ActionDTO actionDTO){
		log.info("updating the status of application with applicationId [{}]",actionDTO.getApplicationId());
		Application application = creditCardApproveService.updateApplicationStatus(actionDTO);
		if(application!=null) {
			log.info("the application with applicationId [{}] is successfully {}",application.getApplicationId(),application.getStatus());
			return ResponseEntity.status(HttpStatus.OK).body("action taken successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SOME_THING_WENT_WRONG);
	}
	
	/**
	 * This method fetches the list of applications assigned to a particular employee
	 * @param employeeId
	 * @return employeeDTO wrapped in ResponseEntity
	 */
	@GetMapping("/employee/getAssignedApplications/{employeeId}")
	public ResponseEntity<Object> getAssignedApplicationsForEmployee(@PathVariable Long employeeId){
		log.info("fetching all the applications assigned to a particular employee with id [{}]",employeeId);
		EmployeeDTO employee = creditCardApproveService.getApplicationsAssigendToEmployee(employeeId);
		if(employee!=null) {
			log.info("successfully fetched all applications");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(employee);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SOME_THING_WENT_WRONG);
	}
	
	/**
	 * This method fetches the list of applications submitted by a particular customer
	 * @param customerId
	 * @return custmerDTO wrapped in ResponseEntity
	 */
	@GetMapping("/customer/getApplications/{customerId}")
	public ResponseEntity<Object> getApplicationsSubmittedByCustomer(@PathVariable Long customerId){
		log.info("fetching all the applications submitted by a cutomer with id [{}]",customerId);
		CustomerDTO customer = creditCardApproveService.getApplicationsSubmittedByCustomer(customerId);
		if(customer!=null) {
			log.info("successfully fetched all applications");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SOME_THING_WENT_WRONG);
	}
	
	/**
	 * This method fetches the details of a particular application
	 * @param applicationId
	 * @return application entity wrapped in ResponseEntity
	 */
	@GetMapping("/getApplication/{applicationId}")
	public ResponseEntity<Object> getApplication(@PathVariable Long applicationId){
		Application application = creditCardApproveService.getApplication(applicationId);
		log.info("fetching the data of a applivation with applicationId [{}]",applicationId);
		if(application!=null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(application);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SOME_THING_WENT_WRONG);
	}
}
