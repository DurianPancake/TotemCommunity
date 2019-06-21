package com.xiaoniu.constant.enums;

/**
 * Redis使用Key的注解标识类型
 * @Author: LLH
 * @Date: 2019/6/11 15:35
 */
public enum KeyType {

    EMPTY,  // 仅使用自定义Key
    AUTO,   // 使用自定义Key + 第一个参数 （默认）
    FULL_ARGS;  // 使用自定义Key + 全部参数
}
