package com.ccs.creditcardapprove.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ccs.creditcardapprove.dto.ActionDTO;
import com.ccs.creditcardapprove.dto.CustomerDTO;
import com.ccs.creditcardapprove.dto.EmployeeDTO;
import com.ccs.creditcardapprove.entity.Application;
import com.ccs.creditcardapprove.entity.Customer;
import com.ccs.creditcardapprove.entity.Employee;
import com.ccs.creditcardapprove.exception.CreditCardApproveException;
import com.ccs.creditcardapprove.repository.ApplicationRepository;
import com.ccs.creditcardapprove.repository.CustomerRepository;
import com.ccs.creditcardapprove.repository.EmployeeRepository;
import com.ccs.creditcardapprove.service.CreditCardApproveService;
import com.ccs.creditcardapprove.service.EmailService;

import lombok.extern.slf4j.Slf4j;

/*
 * CreditCardApproveServiceImpl implements the logic for approving/rejecting the application and to see the list of 
 * applications submitted by customer/assigned to a employee
 */
@Service
@Slf4j
public class CreditCardApproveServiceImpl implements CreditCardApproveService{
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * This method updates the status and triggers an action taken mail ,if the relevant data is not present in database
	 * CreditCardApproveException is thrown
	 * @param actionDTO
	 * @return application
	 * @throws CreditCardApproveException
	 */
	@Override
	public Application updateApplicationStatus(ActionDTO actionDTO) throws CreditCardApproveException{
		if(!ObjectUtils.isEmpty(actionDTO)) {
			log.info("fetching the application data with id [{}]", actionDTO.getApplicationId());
			Application application = getApplication(actionDTO.getApplicationId());
			application.setStatus(actionDTO.getStatus());
			application.setRejectReason(actionDTO.getRejectReason());
			log.info("updating the status of the application in db");
			Application savedApplication = applicationRepository.save(application);
			log.info("fetching the customer email and decrypting it");
			if(!ObjectUtils.isEmpty(savedApplication.getContactDetails())&&
					StringUtils.isNotEmpty(savedApplication.getContactDetails().getEmailId())) {
				String decryptedEmail = decryptData(savedApplication.getContactDetails().getEmailId());
				savedApplication.getContactDetails().setEmailId(decryptedEmail);
			}
			else {
				throw new CreditCardApproveException("the  contact details are not  present");
			}
			log.info("sending the notification email");
			emailService.sendActionTakenMail(savedApplication);
			return savedApplication;
		}
		throw new CreditCardApproveException("the input JSON is null");
	}
	
	/**
	 * This method fetches the application data for the input application id ,it throws CreditCardApproveException 
	 * if there is no data
	 * @param applicationId
	 * @return application
	 * @throws CreditCardApproveException
	 */
	@Override
	public Application getApplication(Long applicationId) {
		if(applicationId!=null)
		{
			log.info("fetching the application data from the database");
			Optional<Application> optional =  applicationRepository.findByApplicationId(applicationId);
			if(optional.isPresent()) {
				return optional.get();
			}
			throw new CreditCardApproveException("no application with the given applicationId");
		}
		throw new CreditCardApproveException("application Id shouldn't be null");
	}

	/**
	 * This method fetches the list of applications submitted by a customer if relevant data is present in data base else
	 * CreditCardApproveException is thrown
	 * @param customerId
	 * @return customerDTO
	 * @throws CreditCardApproveException
	 */
	@Override
	public CustomerDTO getApplicationsSubmittedByCustomer(Long customerId) throws CreditCardApproveException {
		if(customerId!=null)
		{
			log.info("fetching the list of applications submitted by a customer with Id [{}]",customerId);
			Optional<Customer> optional = customerRepository.findByCustomerId(customerId);
			if(optional.isPresent()) {
				Customer customer = optional.get();
				log.info("building the customerDTO");
				List<Application> applications = new ArrayList<Application>();
				customer.getApplications().forEach(application->{
					Application appli = Application.builder()
							.applicationId(application.getApplicationId())
							.status(application.getStatus())
							.rejectReason(application.getRejectReason())
							.address(application.getAddress())
							.contactDetails(application.getContactDetails())
							.personalData(application.getPersonalData())
							.professionalDetails(application.getProfessionalDetails())
							.build();
					applications.add(appli);
				});
				return CustomerDTO.builder()
						.customerId(customer.getCustomerId())
						.applications(applications)
						.build();
			}
			throw new CreditCardApproveException("no customer with the given customerId");
		}
		throw new CreditCardApproveException("customer Id shouldn't be null");
	}
	
	/**
	 * This method fetches the list of applications assigned to a employee if relevant data is present in data base else
	 * CreditCardApproveException is thrown
	 * @param employeeId
	 * @return employeeDTO
	 */
	@Override
	public EmployeeDTO getApplicationsAssigendToEmployee(Long employeeId) throws CreditCardApproveException {
		if(employeeId!=null)
		{
			log.info("fetching the list of applications assigned to a employee with Id [{}]",employeeId);
			Optional<Employee> optional = employeeRepository.findByEmployeeId(employeeId);
			if(optional.isPresent()) {
				Employee employee = optional.get();
				List<Application> applications = new ArrayList<Application>();
				log.info("building the employeeDTO");
				employee.getApplications().forEach(application->{
					Application appli = Application.builder()
							.applicationId(application.getApplicationId())
							.status(application.getStatus())
							.rejectReason(application.getRejectReason())
							.address(application.getAddress())
							.contactDetails(application.getContactDetails())
							.personalData(application.getPersonalData())
							.professionalDetails(application.getProfessionalDetails())
							.build();
					applications.add(appli);
				});
				return EmployeeDTO.builder()
						.employeeId(employee.getEmployeeId())
						.applications(applications)
						.build();
			}
			throw new CreditCardApproveException("no employee with the given employeeId");
		}
		throw new CreditCardApproveException("employee Id shouldn't be null");
	}
	
	/**
	 * This method decrypts the input data
	 * @param data
	 * @return decrypted data
	 */
	private String decryptData(String data) {
		log.info("decrypting the given data");
		byte[] decodedBytes = Base64.getDecoder().decode(data);
		return new String(decodedBytes);
	}
}
