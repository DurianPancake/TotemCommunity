package com.xiaoniu.Exception;

/**
 * @Author: LLH
 * @Date: 2019/6/21 17:14
 */
public class NoSuchRoleException extends AuthorityException {

    public NoSuchRoleException() {
        this("可能您的角色已被删除，请于管理员联系");
    }

    public NoSuchRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchRoleException(String message) {
        super(message);
    }

    public NoSuchRoleException(Throwable cause) {
        super(cause);
    }
}
