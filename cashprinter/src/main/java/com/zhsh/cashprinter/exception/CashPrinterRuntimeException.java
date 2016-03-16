package com.zhsh.cashprinter.exception;

/**
 * 
 */
public class CashPrinterRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CashPrinterRuntimeException(String message) {
		super(message);

	}

	public CashPrinterRuntimeException(String message, Throwable cause) {
		super(message, cause);

	}
}
