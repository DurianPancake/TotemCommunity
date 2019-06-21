package com.xiaoniu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.mapper.UserMapper;
import com.xiaoniu.pojo.User;
import com.xiaoniu.service.RedisService;
import com.xiaoniu.service.dubbo.DubboUserService;
import com.xiaoniu.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.IOException;

/**
 * 该类是Dubbo接口的实现类
 * @Author: LLH
 * @Date: 2019/6/17 11:32
 */
@Service(timeout = 3000)
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    /**
     * 注册用户
     * @param user
     */
    @Override
    @Transactional
    public void saveUser(User user) {
        // 1.将密码加密
        String md5Pass = DigestUtils.md5DigestAsHex((user.getPassword()+user.getAccount()).getBytes());
        // 2.补齐入库数据
        //userMapper.insert(user);
    }

    /**
     * 查询token
     * 1.校验用户名或密码是否正确
     * 2.如果数据不正确，则返回null
     * 3.如果数据正确
     *      1. 生成加密秘钥：MD5（JT_TICKET_+username
     *      2. 将userDB数据转化为userJSON
     *      3.将数据保存到Redis中，7天超时
     * 4.返回token
     * @param user
     * @return
     */
    @Override
    public String findUserByUP(User user) throws IOException {
        String token = null;
        // 1.将密码进行加密
        String md5Pwd = DigestUtils.md5DigestAsHex((user.getPassword()+user.getAccount()).getBytes());
        user.setPassword(md5Pwd);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(user);
        User userDB = userMapper.selectOne(queryWrapper);
        // 2.判断数据正确则执行以下代码
        if (userDB != null) {
            token = BasicConst.TICKET_COOKIE_NAME + userDB.getAccount()+System.currentTimeMillis();
            token = DigestUtils.md5DigestAsHex(token.getBytes());
            System.out.println(token);
            // 脱敏处理
            userDB.setPassword(null);
            String userJSON = ObjectMapperUtil.toJson(userDB);
            redisService.setex(token, BasicConst.TOKEN_EXPIRE_TIME, userJSON);
        }
        return token;
    }
}
