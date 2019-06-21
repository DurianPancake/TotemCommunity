package com.xiaoniu.constant.enums;

/**
 * @Author: LLH
 * @Date: 2019/6/13 16:35
 */
public enum CheckType {

    USERNMAE(1, "username"),
    PHONE(2, "phone"),
    EMAIL(3, "email");

    private Integer type;
    private String name;

    CheckType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
