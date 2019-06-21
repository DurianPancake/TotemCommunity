package com.xiaoniu.util;


import com.xiaoniu.pojo.User;

/**
 * @Author: LLH
 * @Date: 2019/6/19 10:38
 */
public class UserThreadLocal {

    private static ThreadLocal<User> thread = new ThreadLocal<>();

    /**
     * 新增方法
     * @param user
     */
    public static void set(User user) {
        thread.set(user);
    }

    /**
     * 获取方法
     * @return
     */
    public static User get() {
        return thread.get();
    }

    /**
     * 将threadlocal关闭
     * 使用threadlocal切记内存泄漏
     */
    public static void remove() {
        thread.remove();
    }
}
