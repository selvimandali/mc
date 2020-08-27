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
 * This class is an entity class for ProfessionalDetails table
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProfessionalDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long professionalDetailsId;
	
	private String profession;
	
	private String companyName;
	
	private String designation;
	
	private Float grossAnualIncome;
	
}
