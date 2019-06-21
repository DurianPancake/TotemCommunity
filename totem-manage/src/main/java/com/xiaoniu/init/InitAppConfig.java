package com.xiaoniu.init;

import com.xiaoniu.mapper.UserAuthMapper;
import com.xiaoniu.pojo.UserAuth;
import com.xiaoniu.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动时加载的Bean，用于初始化角色K-V Map
 * @Author: LLH
 * @Date: 2019/6/21 11:00
 */
@Component
@Order(11)
public class InitAppConfig implements ApplicationRunner {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserAuthMapper userAuthMapper;
    private Logger logger = LoggerFactory.getLogger(InitAppConfig.class);

    /**
     * 后台开启时将角色加载进Redis
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<UserAuth> userAuths = userAuthMapper.selectList(null);
        // 遍历列表并存入Redis
        userAuths.forEach(auth ->
                redisService.set(auth.getAuthName(), String.valueOf(auth.getAuthValue())));
        logger.info("============== 用户角色已加载 ===============");
    }
}
