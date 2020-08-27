package com.ccs.creditcardapprove.service;

import com.ccs.creditcardapprove.dto.ActionDTO;
import com.ccs.creditcardapprove.dto.CustomerDTO;
import com.ccs.creditcardapprove.dto.EmployeeDTO;
import com.ccs.creditcardapprove.entity.Application;
import com.ccs.creditcardapprove.exception.CreditCardApproveException;
/*
 * CreditCardApproveService interface maintains all the methods used for implementing business logic 
 */
public interface CreditCardApproveService {
	
	public Application updateApplicationStatus(ActionDTO actionDTO) throws CreditCardApproveException;
	
	public CustomerDTO getApplicationsSubmittedByCustomer(Long customerId) throws CreditCardApproveException;
	
	public EmployeeDTO getApplicationsAssigendToEmployee(Long employeeId) throws CreditCardApproveException;
	
	public Application getApplication(Long applicationId) throws CreditCardApproveException;

}
