package com.ccs.creditcardapprove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
 * This application allows employee to approve or reject the apply submitted by customer and also provides a 
 * functionality to see applications submitted by a customer/applications assigned to a employee 
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CreditCardApproveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditCardApproveServiceApplication.class, args);
	}

}
