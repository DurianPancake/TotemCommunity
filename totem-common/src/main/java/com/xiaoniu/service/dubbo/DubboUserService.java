package com.xiaoniu.service.dubbo;

import com.github.pagehelper.PageInfo;
import com.xiaoniu.pojo.User;
import com.xiaoniu.pojo.UserAuth;

import java.io.IOException;
import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/20 8:51
 */
public interface DubboUserService {

    void saveUser(User user);

    String findUserByUP(User user) throws IOException;

    /**
     * 后台查询用户列表
     * @param user
     * @param pageIndex
     * @return
     * @author LLH
     */
    PageInfo<User> findUserListByLimit(User user, Integer pageIndex);

    /**
     * 禁用/启用用户
     * @param userId
     * @return
     * @author LLH
     */
    int banUserById(Long userId);

    /**
     * 给用户指定一个新的角色
     * @param userId
     * @param authId
     * @return
     * @author LLH
     */
    int appointUserNewRole(Long userId, Long authId);

    /**
     * 查询角色列表
     * @return
     * @author LLH
     */
    List<UserAuth> getAuthList();
}
