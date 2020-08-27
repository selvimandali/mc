package com.ccs.creditcardapply.service;

import com.ccs.creditcardapply.dto.ApplicationDTO;
/*
 * EmailService interface maintains all the methods used for implementing  Notification triggering
 */
public interface EmailService {
	
	public String sendAcknowledgementMail(ApplicationDTO applicationDTO);

}
