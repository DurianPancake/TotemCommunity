package com.xiaoniu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoniu.Exception.AuthorityException;
import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.constant.enums.AuthEnum;
import com.xiaoniu.mapper.UserAuthMapper;
import com.xiaoniu.mapper.UserMapper;
import com.xiaoniu.pojo.User;
import com.xiaoniu.pojo.UserAuth;
import com.xiaoniu.service.RedisService;
import com.xiaoniu.service.dubbo.DubboUserService;
import com.xiaoniu.util.ObjectMapperUtil;
import com.xiaoniu.util.StringUtil;
import com.xiaoniu.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.List;

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
    private UserAuthMapper authMapper;
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
        user.setDelFlag(false)
                .setPassword(md5Pass)
                .setRoleAuth(0)
                .setRoleName("用户")
                .setStatus(true);
        userMapper.insert(user);
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

    /**
     * 后台查询用户列表
     * @param user
     * @param pageIndex
     * @return
     * @author LLH
     */
    @Override
    public PageInfo<User> findUserListByLimit(User user, Integer pageIndex) {
        // 参数校验
        ValidUtil.validatePageIndex(pageIndex);
        if (!ValidUtil.isNotEmpty(user)) {
            user = new User();
        }
        // 封装查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(user.getNickname())) {
            queryWrapper.like("nickname", user.getNickname());
        }
        if (StringUtil.isNotEmpty(user.getRoleName())) {
            queryWrapper.eq("role_name", user.getRoleName());
        }
        if (user.getStatus() != null) {
            queryWrapper.eq("status", user.getStatus());
        }
        // 排除项
        queryWrapper.eq("del_flag", false);
        queryWrapper.ne("role_name", AuthEnum.SUPER_MANAGER.key());
        PageHelper.startPage(pageIndex, BasicConst.SYS_PAGE_SIZE);
        List<User> users = userMapper.selectList(queryWrapper);
        // 封装分页
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    /**
     * 禁用/启用用户
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public int banUserById(Long userId) {
        // 校验
        ValidUtil.validateId(userId);
        // 目标用户
        User targerUser = getTargetUser(userId);
        User entity = new User();
        entity.setId(userId);
        entity.setStatus(!targerUser.getStatus());
        return userMapper.updateById(entity);
    }

    /** 查询目标用户 */
    private User getTargetUser(Long userId) {
        User targerUser = userMapper.selectById(userId);
        if (targerUser.getRoleName().equals(AuthEnum.SUPER_MANAGER.key())) {
            throw new AuthorityException();
        }
        return targerUser;
    }

    /**
     * 给用户指定一个新的角色
     * @param userId
     * @param authId
     * @return
     * @author LLH
     */
    @Transactional
    @Override
    public int appointUserNewRole(Long userId, Long authId) {
        // 检验参数
        ValidUtil.validateId(userId);
        ValidUtil.validateId(authId);
        // 校验并入库
        getTargetUser(userId);
        User entity = new User();
        UserAuth auth = authMapper.selectById(authId);
        entity.setId(userId)
                .setRoleName(auth.getAuthName())
                .setRoleAuth(auth.getAuthValue());
        return userMapper.updateById(entity);
    }

    /**
     * 查询角色列表
     * @return
     * @author LLH
     */
    @Override
    public List<UserAuth> getAuthList() {
        QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("role_name", AuthEnum.SUPER_MANAGER.key());
        return authMapper.selectList(queryWrapper);
    }
}
