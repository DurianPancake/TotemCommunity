package com.xiaoniu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaoniu.Exception.ServiceException;
import com.xiaoniu.annotation.Cacheable;
import com.xiaoniu.annotation.RequiresAuth;
import com.xiaoniu.constant.BasicConst;
import com.xiaoniu.constant.enums.AuthEnum;
import com.xiaoniu.constant.enums.KeyType;
import com.xiaoniu.mapper.UserAuthMapper;
import com.xiaoniu.pojo.UserAuth;
import com.xiaoniu.service.dubbo.DubboAuthService;
import com.xiaoniu.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/21 15:30
 */
@Service(timeout = 3000)
public class UserAuthServiceImpl implements DubboAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private RedisService redisService;

    /**
     * 查询角色权限列表
     * @return
     * @author LLH
     */
    @Cacheable(key = BasicConst.AUTH_PAGE_KEY, keyType = KeyType.EMPTY)
    @Override
    public List<UserAuth> findAuthPage() {
        List<UserAuth> userAuths = userAuthMapper.selectList(null);
        return userAuths;
    }

    /**
     * 插入新角色
     * @author LLH
     * @param auth
     * @param ids
     */
    @RequiresAuth(AuthEnum.AUTH_MANAGER)
    @Override
    public void insertNewRoleByIds(UserAuth auth, Long[] ids) {
        // 校验参数
        ValidUtil.requireNotNull(auth);
        ValidUtil.requireNotEmpty(auth.getAuthName());
        // 取值
        QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<UserAuth> baseAuths = userAuthMapper.selectList(queryWrapper);
        int newAuthValue = 0;
        for (UserAuth a : baseAuths) {
            newAuthValue |= a.getAuthValue();
        }
        // 设置初始值
        auth.setAuthValue(newAuthValue);
        auth.setBaseFlag(false);
        userAuthMapper.insert(auth);
        // 清除缓存
        redisService.del(BasicConst.AUTH_PAGE_KEY);
    }

    /**
     * 删除非基础角色
     * @param id
     * @author LLH
     */
    @RequiresAuth(AuthEnum.AUTH_MANAGER)
    @Override
    public void deleteAuthById(Long id) {
        //
        ValidUtil.requireNotNull(id);
        //
        UserAuth auth = userAuthMapper.selectById(id);
        if (auth.getBaseFlag()) {
            throw new ServiceException("不可删除基础权限");
        }
        userAuthMapper.deleteById(id);
        // 清除缓存
        redisService.del(BasicConst.AUTH_PAGE_KEY);
    }
}
