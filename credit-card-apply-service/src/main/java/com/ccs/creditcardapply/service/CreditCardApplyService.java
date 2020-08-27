package com.ccs.creditcardapply.service;

import com.ccs.creditcardapply.dto.ApplicationDTO;
import com.ccs.creditcardapply.entity.Application;
import com.ccs.creditcardapply.entity.Customer;
import com.ccs.creditcardapply.entity.Employee;
import com.ccs.creditcardapply.exception.CreditCardApplyException;

/*
 * CreditCardApplyService interface maintains all the methods used for implementing business logic 
 */
public interface CreditCardApplyService {
	
	public Customer saveCustomer(Customer customer);
	public Employee saveEmployee(Employee employee);
	public Application saveApplication(ApplicationDTO applicationDto) throws CreditCardApplyException;
	
}
