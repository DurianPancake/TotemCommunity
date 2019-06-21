package com.xiaoniu.util;

/**
 * @Author: LLH
 * @Date: 2019/6/1 10:31
 */
public abstract class PageUtil {

    /**
     * 计算数据偏移量
     * @param page
     * @param pageSize
     * @return
     */
    public static int getPageOffset(Integer page, Integer pageSize) {
        return (page - 1) * pageSize;
    }
}
