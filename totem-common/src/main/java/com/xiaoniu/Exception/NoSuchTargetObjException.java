package com.xiaoniu.Exception;

/**
 * 未找到实体异常
 */
public class NoSuchTargetObjException extends ServiceException {

	private static final long serialVersionUID = -6755879223992827865L;

	public NoSuchTargetObjException() {
		super("未找到目标对象，可能已经不存在了");
	}

	public NoSuchTargetObjException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSuchTargetObjException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchTargetObjException(String message) {
		super(message);
	}

	public NoSuchTargetObjException(Throwable cause) {
		super(cause);
	}
	
}
