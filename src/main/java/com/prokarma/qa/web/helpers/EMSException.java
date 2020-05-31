package com.prokarma.qa.web.helpers;

public class EMSException extends RuntimeException{
	/**
	 * Serializing the class
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String error;
	private transient Object[] params;
	private String message = null;

	/**
	 * <p>
	 * Get errorCode.
	 * <p>
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * <p>
	 * Get error.
	 * <p>
	 */
	public String getError() {
		return error;
	}


	/**
	 * @return
	 */
	public Object[] getParams(){
		return params;
	}

	
	public EMSException(String errorCode, String error) {
		this.errorCode = errorCode;
		this.error = error;
	}
	
	public EMSException(String errorCode, String error, Object... params){
		this.errorCode = errorCode;
		this.error = error;
		this.params = params;
	}


	public EMSException(Throwable cause) {
		super(cause);
	}

	public EMSException(String message,String errorCode, Throwable cause) {
		super(message, cause);
		this.errorCode=errorCode;
		this.error=message;
	}
	public EMSException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public EMSException(String className, String methodName, String message) {
		super(className+" : "+methodName+ " : "  +message);
		this.message = className+" : "+methodName+ " : "  +message;
	}


}
