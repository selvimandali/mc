package com.ccs.creditcardapply.service.impl;

import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import com.ccs.creditcardapply.dto.ApplicationDTO;
import com.ccs.creditcardapply.entity.Address;
import com.ccs.creditcardapply.entity.Application;
import com.ccs.creditcardapply.entity.ContactDetails;
import com.ccs.creditcardapply.entity.Customer;
import com.ccs.creditcardapply.entity.Employee;
import com.ccs.creditcardapply.entity.PersonalData;
import com.ccs.creditcardapply.entity.ProfessionalDetails;
import com.ccs.creditcardapply.exception.CreditCardApplyException;
import com.ccs.creditcardapply.repository.ApplicationRepository;
import com.ccs.creditcardapply.repository.CustomerRepository;
import com.ccs.creditcardapply.repository.EmployeeRepository;
import com.ccs.creditcardapply.service.CreditCardApplyService;
import com.ccs.creditcardapply.service.EmailService;
import lombok.extern.slf4j.Slf4j;

/*
 * CreditCardApplyServiceImpl implements the logic for creating the new employee/customer and 
 * for applying the credit card
 */
@Service
@Slf4j
public class CreditCardApplyServiceImpl implements CreditCardApplyService {
	
	@Value("${customer.url}")
	private String url;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * This method calls the repository method to save a customer into database
	 * @param customer
	 * @return the saved customer entity
	 */
	@Override
	public Customer saveCustomer(Customer customer) {
		String encyptedPassword= encryptData(customer.getPassword());
		customer.setPassword(encyptedPassword);
		Customer savedCustomer = customerRepository.save(customer);
		log.info("saved customer is [{}]",savedCustomer.toString());
		return savedCustomer;
	}
	
	/**
	 * This method calls the repository method to save a employee into database
	 * @param employee
	 * @return the saved employee entity
	 */
	@Override
	public Employee saveEmployee(Employee employee) {
		String encyptedPassword= encryptData(employee.getPassword());
		employee.setPassword(encyptedPassword);
		Employee savedEmployee= employeeRepository.save(employee);
		log.info("saved employee is [{}]",savedEmployee.toString());
		return savedEmployee;
	}
	/**
	 * This method converts the input applicationDTO into application entity apply required validations, 
	 * saves the application and calls the sendAcknowledgementMail from EmailService class for sending the acknowledgment if the data is valid else throws the CreditCardApplyException
	 * @param applicationDTO
	 * @return the saved application entity
	 * @exception CreditCardApplyException
	 */
	@Override
	public Application saveApplication(ApplicationDTO applicationDTO) throws CreditCardApplyException{
		if(!ObjectUtils.isEmpty(applicationDTO)) {
			log.info("fetcing the customer with Id [{}]", applicationDTO.getCustomerId());
			Customer customer = getCustomer(applicationDTO.getCustomerId());
			log.info("fetching the employee with Id [{}]", applicationDTO.getEmployeeId());
			Employee employee = getEmployee(applicationDTO.getEmployeeId()); 
			Address address = Address.builder()
					.officeAddress(applicationDTO.getOfficeAddress())
					.residanceAddress(applicationDTO.getResidanceAddress())
					.build();
			log.info("validating emailId");
			String emailId = validateEmailId(applicationDTO.getEmailId());
			log.info("validating mobileNumber");
			String mobileNumber = validateMobileNumber(applicationDTO.getMobileNumber());
			log.info("validating fullName");
			String fullName = validateFullName(applicationDTO.getFullName());
			ContactDetails contactDetails = ContactDetails.builder()
					.emailId(emailId)
					.mobileNumber(mobileNumber)
					.fullName(fullName)
					.build();
			String panCardNumber = validatePanCardNumber(applicationDTO.getPanCard());
			PersonalData personalData = PersonalData.builder()
					.fatherName(applicationDTO.getFatherName())
					.dateOfBirth(applicationDTO.getDateOfBirth())
					.maritalStatus(applicationDTO.getMaritalStatus())
					.citizenShip(applicationDTO.getCitizenShip())
					.residentialStatus(applicationDTO.getResidentialStatus())
					.panCardNumber(panCardNumber)
					.build();
			ProfessionalDetails professionalDetails= ProfessionalDetails.builder()
					.profession(applicationDTO.getProfession())
					.companyName(applicationDTO.getCompanyName())
					.designation(applicationDTO.getDesignation())
					.grossAnualIncome(applicationDTO.getGrossAnualIncome())
					.build();
			Application application= Application.builder()
					.customer(customer)
					.employee(employee)
					.personalData(personalData)
					.professionalDetails(professionalDetails)
					.contactDetails(contactDetails)
					.address(address)
					.build();
			Application savedApplication = applicationRepository.save(application);
			log.info("saved application is [{}]",savedApplication.toString());
			String returnMessage=emailService.sendAcknowledgementMail(applicationDTO);
			if(StringUtils.isEmpty(returnMessage)) {
				log.info(returnMessage);
			}
			return savedApplication;
		}
		throw new CreditCardApplyException("the input JSON object is empty");
	}
	
	/**
	 * This method fetches the customer entity corresponding to the input customer id.
	 * If the data is not present in the database it throws CreditCardApplyException
	 * @param customerId
	 * @return customer entity 
	 * @throws CreditCardApplyException
	 */
	private Customer getCustomer(Long customerId) throws CreditCardApplyException{
		if(customerId!=null)
		{
			Optional<Customer> optional =  customerRepository.findByCustomerId(customerId);
			if(optional.isPresent()) {
				return optional.get();
			}
			throw new CreditCardApplyException("no Customer with the given customerId");
		}
		throw new CreditCardApplyException("Customer Id shouldn't be null");
	}
	
	/**
	 * This method fetches the employee entity corresponding to the input employee id.
	 * If the data is not present in the database it throws CreditCardApplyException
	 * @param employeeId
	 * @return employee entity
	 * @throws CreditCardApplyException 
	 */
	private Employee getEmployee(Long employeeId) throws CreditCardApplyException{
		if(employeeId!=null)
		{
			Optional<Employee> optional =  employeeRepository.findByEmployeeId(employeeId);
			if(optional.isPresent()) {
				return optional.get();
			}
			throw new CreditCardApplyException("no Employee with the given employeeId");
		}
		throw new CreditCardApplyException("Employee Id shouldn't be null");
	}
	
	/**
	 * This method validates the input mobile number and returns the encrypted value if the mobile number is valid 
	 * else throws the CreditCardApplyException
	 * @param mobileNumber
	 * @return encrypted mobile number
	 * @throws CreditCardApplyException
	 */
	private String validateMobileNumber(String mobileNumber) throws CreditCardApplyException{
		if(!StringUtils.isEmpty(mobileNumber)) {
			Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
			Matcher m = p.matcher(mobileNumber);
			if(m.find()&& m.group().equals(mobileNumber))
			{
				return encryptData(mobileNumber); 
			}
			throw new CreditCardApplyException("not a valid mobile number");
		}
		throw new CreditCardApplyException("mobile Number shouldn't be null");
	}
	
	
	/**
	 * This method validates the input email and returns the encrypted value if the email is valid 
	 * else throws the CreditCardApplyException
	 * @param email
	 * @return encrypted email Id
	 * @throws CreditCardApplyException
	 */
	private String validateEmailId(String email) throws CreditCardApplyException{
		if(!StringUtils.isEmpty(email)) {
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                    "[a-zA-Z0-9_+&*-]+)*@" + 
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                    "A-Z]{2,7}$"; 
                      
			Pattern pattern = Pattern.compile(emailRegex);
			Matcher m = pattern.matcher(email);
			if(m.matches()) {
				return encryptData(email);
			}
			throw new CreditCardApplyException("not a valid email");
		}
		throw new CreditCardApplyException("email shouldn't be null");
	}
	
	/**
	 * This method validates the input panCardNumber and returns the encrypted value if the panCardNumber is valid 
	 * else throws the CreditCardApplyException
	 * @param panCardNumber
	 * @return encrypted panCardNumber
	 * @throws CreditCardApplyException
	 */
	private String validatePanCardNumber(String panCardNumber) throws CreditCardApplyException{
		if(!StringUtils.isEmpty(panCardNumber)) {
			String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(panCardNumber);
			if(m.matches()) {
				return encryptData(panCardNumber);
			}
			throw new CreditCardApplyException("not a valid pan card number");
		}
		throw new CreditCardApplyException("pan card number shouldn't be null");
		
	}
	
	/**
	 * This method checks whether the input full name is not null and returns it, CreditCardApplyException 
	 * is thrown if the full is null
	 * @param fullName
	 * @return fullName
	 * @throws CreditCardApplyException
	 */
	private String validateFullName(String fullName) throws CreditCardApplyException{
		if(!StringUtils.isEmpty(fullName)) {
			return fullName;
		}
		throw new CreditCardApplyException("full name shouldn't be null");
	}
	
	/**
	 * This method encrypts the input data
	 * @param data
	 * @return encrypted data
	 */
	private String encryptData(String data) {
		return Base64.getEncoder().encodeToString(data.getBytes());
	}
	

}
