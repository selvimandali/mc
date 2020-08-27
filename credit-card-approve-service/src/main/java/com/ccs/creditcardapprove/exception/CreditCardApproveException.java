package com.ccs.creditcardapprove.exception;
/*
 * CreditCardApproveException is a customized exception which thrown for any exceptional condition
 */
public class CreditCardApproveException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CreditCardApproveException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}

	public CreditCardApproveException(String errorMessage) {
		super(errorMessage);
	}


}
