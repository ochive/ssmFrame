package com.hu.ssmFrame.core.exception;

/**
 * 自定义异常类
 * @author mudking
 *
 */
public class CustomException extends RuntimeException{
	private static final long serialVersionUID = -5200863757480983422L;

	public CustomException() {
		super();
	}

	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}
	
}
