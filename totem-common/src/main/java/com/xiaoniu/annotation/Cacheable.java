package com.xiaoniu.annotation;


import com.xiaoniu.constant.enums.KeyType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: LLH
 * @Date: 2019/6/11 15:33
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    // 用户自定义的键名前缀
    String key();
    // 键标注类型
    KeyType keyType() default KeyType.AUTO;
    // 标识缓存存活时间
    int seconds() default 1800;
}
