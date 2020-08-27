package com.ccs.creditcardapply.exception;

/*
 * CreditCardApplyException is a customized exception which thrown for any exceptional condition
 */
public class CreditCardApplyException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CreditCardApplyException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}

	public CreditCardApplyException(String errorMessage) {
		super(errorMessage);
	}


}
