package com.xiaoniu.service;

import com.xiaoniu.constant.enums.CheckType;
import com.xiaoniu.pojo.User;

public interface UserService {

    /**
     * true表示用户已经存在， false表示用户可以使用
     * 1. param     用户参数
     * 2. type      1 username 2 phone 3 email
     * @param param
     * @param type
     * @return
     */
    boolean checkUser(String param, CheckType type);

    /**
     * 注册用户保存数据库
     * @param user
     */
    void saveUser(User user);
}
