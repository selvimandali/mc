package com.ccs.creditcardapprove.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccs.creditcardapprove.entity.ProfessionalDetails;

/*
 * ProfessionalDetailsRepository is an interface extending JpaRepository interface for maintaining user defined method for 
 * database manipulations. 
 */
@Repository
public interface ProfessionalDetailsRepository extends JpaRepository<ProfessionalDetails, Long> {

}
