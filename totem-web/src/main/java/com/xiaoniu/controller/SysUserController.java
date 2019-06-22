package com.xiaoniu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoniu.pojo.User;
import com.xiaoniu.service.RedisService;
import com.xiaoniu.service.dubbo.DubboUserService;
import com.xiaoniu.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LLH
 * @Date: 2019/6/22 9:59
 */
@RestController
@RequestMapping("sys/user")
public class SysUserController {

    @Reference(timeout = 3000, check = false)
    private DubboUserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * 查询后台的管理页面
     * @param pageIndex 页码
     * @param user 查询条件：包括昵称的模糊查询，角色类型，冻结状态
     * @return
     * @author LLH
     */
    @RequestMapping("page/{pageIndex}")
    public SysResult userManagePage(@PathVariable Integer pageIndex, User user) {
        return SysResult.success(userService.findUserListByLimit(user, pageIndex));
    }

    /**
     * 禁用启用用户
     * @author LLH
     */
    @RequestMapping("ban/{userId}")
    public SysResult banUserById(@PathVariable Long userId) {
        return SysResult.success(userService.banUserById(userId));
    }

    /**
     * 指定用户新的角色
     * @return
     * @author LLH
     */
    @RequestMapping("appoint/{userId}/{authId}")
    public SysResult appointUserNewRole(@PathVariable Long userId, @PathVariable Long authId) {
        return SysResult.success(userService.appointUserNewRole(userId, authId));
    }

    /**
     * 查询下拉列表和选定新角色时使用的角色列表
     * @return
     * @author LLH
     */
    @RequestMapping("authList")
    public SysResult getAuthList() {
        return SysResult.success(userService.getAuthList());
    }
}
