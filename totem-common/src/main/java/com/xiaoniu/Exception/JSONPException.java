package com.xiaoniu.Exception;

import lombok.Data;

/**
 * JSONP 异常
 * @Author: LLH
 * @Date: 2019/6/21 17:22
 */
@Data
public class JSONPException extends ServiceException {

    private String callback;

    public JSONPException(String callback) {
        this.callback = callback;
    }

    public JSONPException(String callback, String message) {
        super(message);
        this.callback = callback;
    }

    public JSONPException(String callback, Throwable cause) {
        super(cause.getClass().getSimpleName(), cause);
        this.callback = callback;
    }
}
