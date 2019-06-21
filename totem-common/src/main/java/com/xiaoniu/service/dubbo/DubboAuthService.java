package com.xiaoniu.service.dubbo;

import com.xiaoniu.pojo.UserAuth;

import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/21 14:56
 */
public interface DubboAuthService {

    /**
     * 查询角色权限列表
     * @author LLH
     * @return
     */
    List<UserAuth> findAuthPage();

    /**
     * 插入新角色
     * @author LLH
     * @param auth
     * @param ids
     */
    void insertNewRoleByIds(UserAuth auth, Long[] ids);

    /**
     * 删除非基础角色
     * @author LLH
     * @param id
     */
    void deleteAuthById(Long id);
}
