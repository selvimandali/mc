package com.ccs.creditcardapply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
 * This application allows user to create a user,employee and apply for credit card
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class CreditCardApplyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardApplyServiceApplication.class, args);
	}

}
