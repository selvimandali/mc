package com.ccs.creditcardapprove.dto;

import java.util.List;

import com.ccs.creditcardapprove.entity.Application;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This class acts as an Data transfer object for showing the list of applications assigned to a employee
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
	
	private Long employeeId;
	
	private List<Application> applications;
	
}
