package com.xiaoniu.annotation;

import com.xiaoniu.constant.enums.AuthEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识方法需求的权限，权限从注解中的枚举值获取键，或是直接获取
 *  然后根据键值从缓存中获取具体的权限值
 *  - 当都有枚举和key()属性都有值时，使用时优先使用枚举中的key
 * @Author: LLH
 * @Date: 2019/6/21 8:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAuth {

    AuthEnum value() default AuthEnum.NULL;

    String key() default "";
}
