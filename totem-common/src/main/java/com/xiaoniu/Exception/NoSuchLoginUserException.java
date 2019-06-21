package com.xiaoniu.Exception;

/**
 * 未登录异常
 */
public class NoSuchLoginUserException extends ValidationException {
	
	private static final long serialVersionUID = 8972652679491418916L;

	public NoSuchLoginUserException() {
		super("未登录异常");
	}

	public NoSuchLoginUserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSuchLoginUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchLoginUserException(String message) {
		super(message);
	}

	public NoSuchLoginUserException(Throwable cause) {
		super(cause);
	}
}
