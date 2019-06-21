package com.xiaoniu.interceptor;

import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.pojo.User;
import com.xiaoniu.service.RedisService;
import com.xiaoniu.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: LLH
 * @Date: 2019/6/21 9:59
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;
    /*
      在Spring4版本中要求必须重写3个方法，不管是否需要
      在Spring5版本中在接口中添加default属性，则省略不写
     */
    /**
     * 业务逻辑：
     *  1.获取Cookie数据
     *  2.从cookie中获取token(TICKET)
     *  3.判断Redis缓存服务器中是否有数据
     * @param request
     * @param response
     * @param handler
     * @return true: 拦截放行 false: 请求拦截，重定向到登录页面
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User login = null;
        if ((login = redisService.getLoginUser(request)) != null) {
            // 使用Session会话时要即时关闭
            UserThreadLocal.set(login);
            return true;
        }
        response.sendRedirect(BasicConst.LOGIN_URL);
        // 表示拦截
        return false;
    }

    /**
     * 删除会话数据
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 防止内存泄漏
        UserThreadLocal.remove();
    }
}