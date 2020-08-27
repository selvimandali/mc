package com.ccs.creditcardapprove.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ccs.creditcardapprove.entity.Application;
import com.ccs.creditcardapprove.exception.CreditCardApproveException;
import com.ccs.creditcardapprove.service.EmailService;

import lombok.extern.slf4j.Slf4j;
/*
 * EmailServiceImpl class implements the logic for triggering the email notification
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	private static final String BR = "	<br>\r\n";

	/**
	 * This method fetches the emailId from applicationDTO and send the acknowledgment mail 
	 * @param applicationDTO
	 * @return success message
	 * @throws CreditCardApproveException
	 */
	@Override
	public String sendActionTakenMail(Application application) throws CreditCardApproveException{
		try {
			MimeMessagePreparator preparator = mimeMessage -> {
				MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, true);
				msg.setFrom("selvimandali2096@gmail.com");
				if(!StringUtils.isEmpty(application.getContactDetails().getEmailId()))
				{
					msg.setTo(application.getContactDetails().getEmailId());
				}
				msg.setSubject("Action Taken on Credit card Application");
				msg.setText(getEmailBody(application), true);
			};
			log.info("Sending Notifiction Mail");
			emailSender.send(preparator);
			return "successfully sent email";
		} catch (Exception e) {
			throw new CreditCardApproveException("application is submitted but exception in sending email",e);
		}
	}
	
	/**
	 * This method prepares the mail body for the acknowledgment mail
	 * @param applicationDTO
	 * @return emailBody
	 */
	private String getEmailBody(Application application) {
		log.info("fetching the email body");
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"ISO-8859-1\">\r\n" + 
				"<title>Insert title here</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<p>Hi "+application.getContactDetails().getFullName()+",</p>\r\n" + 
				"<p>\r\n" + 
				"	Your Application for Credit Card is "+application.getStatus()+".\r\n" + 
				BR + 
				BR + 
				"	Thanks for using Credit Card Services.\r\n" + 
				BR + 
				BR + 
				"	Regard,\r\n" + 
				BR + 
				"	Credit Card Services\r\n" + 
				"</p>\r\n" + 
				"</body>\r\n" + 
				"</html>");
		return emailBody.toString();
	}


}
