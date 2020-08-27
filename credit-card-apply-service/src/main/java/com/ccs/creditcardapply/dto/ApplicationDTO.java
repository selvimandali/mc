package com.ccs.creditcardapply.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This class acts as a data transfer object for applying credit card
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDTO {
	
	private Long customerId;
	
	private Long employeeId;
	
	private String emailId;
	
	private String mobileNumber;
	
	private String fullName;
	
	private String fatherName;
	
	private String dateOfBirth;
	
	private String maritalStatus;
	
	private String citizenShip;
	
	private String residentialStatus;
	
	private String panCard;
	
	private String profession;
	
	private String companyName;
	
	private String designation;
	
	private Float grossAnualIncome;
	
	private String residanceAddress;
	
	private String officeAddress;
}
