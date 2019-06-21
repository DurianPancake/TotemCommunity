package com.xiaoniu.service;

import com.xiaoniu.Exception.NoSuchLoginUserException;
import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.pojo.User;
import com.xiaoniu.util.ObjectMapperUtil;
import com.xiaoniu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: LLH
 * @Date: 2019/6/11 16:02
 */
@Service
public class RedisService {

    @Autowired
    private JedisCluster jedis;

    public String get(String key) {
        return jedis.get(key);
    }

    public void set(String key, String value) {
        jedis.set(key, value);
    }

    public void setex(String key, int seconds, String value) {
        jedis.setex(key, seconds, value);
    }

    public void del(String key) {
        jedis.del(key);
    }

    public void del(String... keys) {
        jedis.del(keys);
    }

    /**
     * 从request域中获取登录用户
     * @param request
     * @return
     */
    public User getLoginUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (BasicConst.TICKET_COOKIE_NAME.equals(cookie.getName())) {
                String json = this.get(cookie.getValue());
                if (StringUtil.isNotEmpty(json)) {
                    try {
                        return ObjectMapperUtil.toObject(json, User.class);
                    } catch (IOException e) {
                        throw new NoSuchLoginUserException();
                    }
                }
                // 这个null表示有Cookie但是无法从缓存获取值
                return null;
            }
        }
        // 无对应cookie返回null
        return null;
    }

    /**
     * 判断token是否失效
     * @param request
     * @return
     */
    public boolean isTokenAlive(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (BasicConst.TICKET_COOKIE_NAME.equals(cookie.getName())) {
                String json = this.get(cookie.getValue());
                if (StringUtil.isNotEmpty(json)) {
                    return true;
                }
                // 这个null表示有Cookie但是无法从缓存获取值
                return false;
            }
        }
        // 无对应cookie返回null
        return false;
    }
}
