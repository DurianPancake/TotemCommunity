package com.xiaoniu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JSON工具类
 * @Author: LLH
 * @Date: 2019/6/5 11:53
 */
public abstract class ObjectMapperUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转化为JSON串
     * @param target
     * @return
     */
    public static String toJson(Object target) throws JsonProcessingException {
        return  MAPPER.writeValueAsString(target);
    }

    /**
     * 将JSON串转化为对象
     * @param targetJson json字符串
     * @param clz 目标类型的
     * @param <T>
     * @return
     */
    public static final  <T> T toObject(String targetJson, Class<T> clz) throws IOException {
        T t = MAPPER.readValue(targetJson, clz);
        return t;
    }
}
