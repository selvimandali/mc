package com.ccs.creditcardapprove.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * This class is an entity class for ContactDetails table 
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long contactDetailsId;
	
	private String emailId;
	
	private String mobileNumber;
	
	private String fullName;
}
