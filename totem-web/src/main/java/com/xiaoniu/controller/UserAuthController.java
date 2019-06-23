package com.xiaoniu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoniu.annotation.RequiresAuth;
import com.xiaoniu.constant.enums.AuthEnum;
import com.xiaoniu.pojo.UserAuth;
import com.xiaoniu.service.dubbo.DubboAuthService;
import com.xiaoniu.vo.SysResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/21 15:00
 */
@RestController
@RequestMapping("auth")
public class UserAuthController {

    @Reference(timeout = 3000,check=false)
    private DubboAuthService authService;

    /**
     * 查询数据页
     * @return
     */
    @RequiresAuth(AuthEnum.AUTH_MANAGER)
    @RequestMapping("page")
    public SysResult findAuthList() {
        List<UserAuth> list = authService.findAuthPage();
        return SysResult.success(list);
    }

    /**
     * 创建新角色
     * @param auth
     * @param ids
     * @return
     */
    @RequiresAuth(AuthEnum.AUTH_MANAGER)
    @RequestMapping("createNewRole")
    public SysResult createNewRole(UserAuth auth, Long... ids) {
        authService.insertNewRoleByIds(auth, ids);
        return SysResult.success();
    }

    /**
     * 删除角色、基础权限不可删除
     */
    @RequiresAuth(AuthEnum.AUTH_MANAGER)
    @RequestMapping("delete/{id}")
    public SysResult deleteById(@PathVariable Long id) {
        authService.deleteAuthById(id);
        return SysResult.success();
    }
}
