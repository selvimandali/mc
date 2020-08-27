package com.ccs.creditcardapprove.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccs.creditcardapprove.entity.Application;

/*
 * ApplicationRepository is an interface extending JpaRepository interface for maintaining user defined method for 
 * database manipulations. 
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	/**
	 * This method fetches and returns data of the application with input applicationId
	 * @param applicationId
	 * @return Fetched data from the database wrapped in Optional object
	 */
	Optional<Application> findByApplicationId(Long applicationId);
	
}
