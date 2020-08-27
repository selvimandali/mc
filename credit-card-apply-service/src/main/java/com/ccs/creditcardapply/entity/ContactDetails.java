package com.ccs.creditcardapply.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * This class is an entity class for ContactDetails table
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ContactDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long contactDetailsId;
	
	private String emailId;
	
	private String mobileNumber;
	
	private String fullName;
}
