package com.ccs.creditcardapprove.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This class acts as an Data transfer object for taking the action on application
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActionDTO {
	
	private Long applicationId;
	
	private String status;
	
	private String rejectReason;
	
}
