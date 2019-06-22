package com.xiaoniu.util;


import org.apache.commons.lang3.StringUtils;

/**
 * @Author: LLH
 * @Date: 2019/6/5 19:51
 */
public class StringUtil extends StringUtils {

    /**
     * 如果字符序列不为空则返回true
     * @param value
     * @return
     */
    public static boolean isNotEmpty(CharSequence value) {
        return !StringUtil.isEmpty(value);
    }


    /**
     * 只要有任意为空，则返回true
     * @param css
     * @return
     */
    public static boolean isAnyEmpty(CharSequence... css) {
        for (CharSequence cs : css) {
            if(isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 只要有任意不为空则返回false，否则返回true
     * @param css
     * @return
     */
    public static boolean isAllEmpty(CharSequence... css) {
        for (CharSequence cs : css) {
            if(!isEmpty(cs)) {
                return false;
            }
        }
        return true;
    }
}
