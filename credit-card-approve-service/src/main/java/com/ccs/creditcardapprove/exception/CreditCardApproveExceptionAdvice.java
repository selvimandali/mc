package com.ccs.creditcardapprove.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/*
 * CreditCardApproveExceptionAdvice is a convenient base class for @RestControllerAdvice classes that wish to provide 
 * centralized exception handling across all @RequestMapping methods through @ExceptionHandler methods.
 * 
 */
@RestControllerAdvice
@Slf4j
public class CreditCardApproveExceptionAdvice extends ResponseEntityExceptionHandler {
	/**
	 * This method is invoked exception is thrown in the flow of executing an HTTP request, it fetches the error message 
	 * from the exception creates a ResponseEntity Object with the error message returned to the corresponding controller
	 * method, The same is returned by the controller to the client calling the corresponding HHTP request. 
	 * @param e
	 * @param webRequest
	 * @return response entity with error message
	 */
	@ExceptionHandler(CreditCardApproveException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleProfileException(CreditCardApproveException e, NativeWebRequest  webRequest) {
		
		log.error(e.getMessage());
		
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
