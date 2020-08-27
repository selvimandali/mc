package com.ccs.creditcardapply.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ccs.creditcardapply.dto.ApplicationDTO;
import com.ccs.creditcardapply.dto.CustomerDTO;
import com.ccs.creditcardapply.dto.EmployeeDTO;
import com.ccs.creditcardapply.entity.Application;
import com.ccs.creditcardapply.entity.Customer;
import com.ccs.creditcardapply.entity.Employee;
import com.ccs.creditcardapply.service.CreditCardApplyService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/*
 * CreditCardApplyController provides the rest API for credit-card-apply service
 */
@Api
@RestController
@Slf4j
public class CreditCardApplyController {
	
	@Autowired
	private CreditCardApplyService creditCardApplyService;
	
	@Value("${customer.url}")
	private String url;
	
	/**
	 * This end point is called to save a new employee into database
	 * @param employee
	 * @return the saved employee entity wrapped in ResponseEntity
	 */
	@PostMapping("/saveEmployee")
	public ResponseEntity<Object> saveEmployee(@RequestBody @Validated EmployeeDTO employee){
		log.info("saving the employee into the database [{}]", employee.toString());
		Employee inputEmployee = Employee.builder()
				.userId(employee.getUserId())
				.password(employee.getPassword())
				.build();
		Employee savedEmployee = creditCardApplyService.saveEmployee(inputEmployee);
		if(savedEmployee!=null) {
			EmployeeDTO employeeDTO= EmployeeDTO.builder()
					.employeeId(savedEmployee.getEmployeeId())
					.userId(savedEmployee.getUserId())
					.build();
			log.info("save employeeDTO is [{}]",employeeDTO.toString() );
			return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	/**
	 * This end point is called to save a new employee into database
	 * @param customer
	 * @return the saved customer entity wrapped in ResponseEntity
	 */
	@PostMapping("/saveCustomer")
	public ResponseEntity<Object> saveCustomer(@RequestBody @Validated CustomerDTO customer){
		Customer inputCustomer = Customer.builder()
				.userId(customer.getUserId())
				.password(customer.getPassword())
				.build();
		Customer savedCustomer = creditCardApplyService.saveCustomer(inputCustomer);
		if(savedCustomer!=null) {
			CustomerDTO customerDTO = CustomerDTO.builder()
					.customerId(savedCustomer.getCustomerId())
					.userId(savedCustomer.getUserId())
					.build();
			return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
		}else
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	/**
	 * This end point is used to submit the credit card application
	 * @param  applicationDTO
	 * @return the saved application entity wrapped in ResponseEntity
	 */
	@PostMapping("/apply")
	public ResponseEntity<Object> saveApplication(@RequestBody ApplicationDTO applicationDTO){
		Application application = creditCardApplyService.saveApplication(applicationDTO);
		if(application!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(application);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("some thing went wrong");
		}
	}
	
}
