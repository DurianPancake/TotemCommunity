package com.xiaoniu.service.dubbo;

import com.xiaoniu.pojo.User;

import java.io.IOException;

/**
 * @Author: LLH
 * @Date: 2019/6/20 8:51
 */
public interface DubboUserService {

    void saveUser(User user);

    String findUserByUP(User user) throws IOException;
}
