package com.ccs.creditcardapply.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccs.creditcardapply.entity.Employee;

/*
 * EmployeeRepository is an interface extending JpaRepository interface for maintaining user defined method for 
 * database manipulations. 
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	/**
	 * This method fetches and returns data of the employee with input employeeId
	 * @param employeeId
	 * @return Fetched data from the database wrapped in Optional object
	 */
	Optional<Employee> findByEmployeeId(Long employeeId);
	
}
