package com.xiaoniu.constant.enums;

/**
 * 权限需求枚举，含键名
 *  -   主要用于定义基础权限，复合的衍生权限建议使用{@Code RequiresAuth}中的Key属性
 * @See     com.xiaoniu.annotation.RequiresAuth
 * @Author: LLH
 * @Date: 2019/6/21 8:51
 */
public enum AuthEnum {

    NULL(""),
    NORMAL("普通用户"),
    CRAWLER_MANAGER("爬虫管理"),
    AUTH_MANAGER("权限管理"),
    USER_MANAGER("用户管理"),
    EQDATA_MANAGER("地震数据管理"),
    SUPER_MANAGER("超级管理员");

    private String key;

    AuthEnum(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
