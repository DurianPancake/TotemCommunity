package com.xiaoniu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.pojo.User;
import com.xiaoniu.service.RedisService;
import com.xiaoniu.service.dubbo.DubboUserService;
import com.xiaoniu.util.StringUtil;
import com.xiaoniu.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LLH
 * @Date: 2019/6/21 18:33
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Reference(timeout = 3000, check = false)
    private DubboUserService userService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("{moduleName}")
    public String index(@PathVariable String moduleName) {
        return moduleName;
    }

    /**
     * 使用dubbo的接口实现注册用户
     * @param user
     * @return
     */
    @RequestMapping("doRegister")
    @ResponseBody
    public SysResult doRegister(User user) {
        userService.saveUser(user);
        return SysResult.success();
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public SysResult login(User user, HttpServletResponse response) throws IOException {

        // 调用sso系统获取秘钥
        String token = userService.findUserByUP(user);
        // 如果数据不为Null时，将数据保存到Cookie中
        // Cookie中的key固定值
        if (!StringUtil.isEmpty(token)) {
            Cookie cookie = new Cookie(BasicConst.TICKET_COOKIE_NAME, token);
            cookie.setMaxAge(BasicConst.TOKEN_EXPIRE_TIME); // 生命周期
            cookie.setDomain(BasicConst.SECOND_DOMAIN_NAME);   // 二级域名
            cookie.setPath("/");
            response.addCookie(cookie);
            return SysResult.success();
        }
        return SysResult.fail();
    }

    /**
     * 注销登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (BasicConst.TICKET_COOKIE_NAME.equals(cookie.getName())) {
                cookie.setMaxAge(0);    // 删除Cookie
                cookie.setPath("/");
                cookie.setDomain(BasicConst.SECOND_DOMAIN_NAME);
                deleteRedisCache(cookie);
                response.addCookie(cookie);
            }
        }
        return "redirect:/";
    }

    /** 提取的删除登录令牌的缓存 */
    private void deleteRedisCache(Cookie cookie) {
        String token = cookie.getValue();
        if (StringUtil.isNotEmpty(token)) {
            redisService.del(token);
        }
    }

}
