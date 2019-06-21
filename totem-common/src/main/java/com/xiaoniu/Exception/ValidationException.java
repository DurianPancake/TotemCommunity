package com.xiaoniu.Exception;

/**
 * 参数校验异常
 */
public class ValidationException extends ServiceException {
	
	private static final long serialVersionUID = 1704277789150986670L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}
}
