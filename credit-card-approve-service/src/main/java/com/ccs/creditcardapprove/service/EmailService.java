package com.ccs.creditcardapprove.service;

import com.ccs.creditcardapprove.entity.Application;
import com.ccs.creditcardapprove.exception.CreditCardApproveException;
/*
 * EmailService interface maintains all the methods used for implementing  Notification triggering
 */

public interface EmailService {
	
	public String sendActionTakenMail(Application application) throws CreditCardApproveException;

}
