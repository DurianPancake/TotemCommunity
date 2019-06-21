package com.xiaoniu.Exception;

/**
 * 权限异常
 */
public class AuthorityException extends ServiceException{
	
	private static final long serialVersionUID = -254436518490082067L;

	public AuthorityException() {
		super("该用户没有足够的权限");
	}

	public AuthorityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthorityException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorityException(String message) {
		super(message);
	}

	public AuthorityException(Throwable cause) {
		super(cause);
	}
}
