package com.ccs.creditcardapply.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ccs.creditcardapply.entity.Employee;

/*
 * Test class for EmployeeRepository
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private Employee savedEmployee;
	
	/**
	 * This method is called before each and every test case executes, here we are pre-loading the data into database 
	 * executing the test cases
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Employee employee = Employee.builder()
				.userId("employeeTest")
				.password("password")
				.build();
		 this.savedEmployee = employeeRepository.save(employee);
		assertNotNull(savedEmployee);
	}

	/**
	 * This method is a test case for EmployeeRepository
	 */
	@Test
	void testFindByEmployeeId() {
		Optional<Employee> fetchedEmployeeOptional= employeeRepository.findByEmployeeId(savedEmployee.getEmployeeId());
		Employee fetchedEmployee = fetchedEmployeeOptional.get();
		assertEquals(fetchedEmployee, savedEmployee);
	}

}
