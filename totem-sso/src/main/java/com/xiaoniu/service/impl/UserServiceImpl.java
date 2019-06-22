package com.xiaoniu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.xiaoniu.constant.enums.CheckType;
import com.xiaoniu.mapper.UserMapper;
import com.xiaoniu.pojo.User;
import com.xiaoniu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * 注册时检查
	 * true表示用户已经存在， false表示用户可以使用
	 * 1. param     用户参数
	 * 2. type      1 username 2 phone 3 email
	 * 将type转化为对应的column
	 * @param param: 需要检验的参数（名称）
	 * @param type: 校验类型枚举
	 * @return
	 */
	@Override
	public boolean checkUser(String param, CheckType type) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(type.getName(), param);
		Integer count = userMapper.selectCount(queryWrapper);
		return count == 0 ? false : true;
	}

	/**
	 * 注册用户保存数据库
	 * @param user
	 */
	@Override
	public void
	saveUser(User user) {
		// TODO:待补充默认数据
		userMapper.insert(user);
	}
}
