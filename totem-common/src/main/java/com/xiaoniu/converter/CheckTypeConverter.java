package com.xiaoniu.converter;

import com.xiaoniu.constant.enums.CheckType;
import org.springframework.core.convert.converter.Converter;

/**
 * @Author: LLH
 * @Date: 2019/6/13 16:40
 */
public class CheckTypeConverter implements Converter<Integer, CheckType> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public CheckType convert(Integer source) {
        Class<CheckType> targetClass = CheckType.class;
        CheckType[] types = targetClass.getEnumConstants();
        for (CheckType type : types) {
            if (type.getType() == source) {
                return type;
            }
        }
        return null;
    }
}
