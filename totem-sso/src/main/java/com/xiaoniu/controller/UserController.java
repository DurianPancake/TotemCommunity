package com.xiaoniu.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.xiaoniu.Exception.JSONPException;
import com.xiaoniu.constant.enums.CheckType;
import com.xiaoniu.converter.CheckTypeConverter;
import com.xiaoniu.pojo.User;
import com.xiaoniu.service.RedisService;
import com.xiaoniu.service.UserService;
import com.xiaoniu.util.ObjectMapperUtil;
import com.xiaoniu.util.StringUtil;
import com.xiaoniu.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;

	/**
	 * 校验用户是否存在
	 * 	http://sso.jt.com/user/check/{param}/{type}
	 * 	返回值 SysResult，
	 * 	由于跨域请求，所以返回值必须特殊处理callback(json)
	 * @param param 需要校验的参数
	 * @param type	校验的类型
	 * @param callback
	 * @return
	 */
	@RequestMapping("check/{param}/{type}")
	public JSONPObject checkUsername(@PathVariable String param, @PathVariable Integer type, String callback) {

		JSONPObject object = null;
		CheckType checkType = getCheckType(type);
		boolean flag = userService.checkUser(param, checkType);
		try {
			object = new JSONPObject(callback, SysResult.success(flag));
		} catch (Exception e) {
			throw new JSONPException(callback, e);
		}
		return object;
	}

	/** 通过数字获取校验类型的枚举 */
	private CheckType getCheckType(Integer type) {
		CheckTypeConverter converter = new CheckTypeConverter();
		return converter.convert(type);
	}

	/**
	 * 注册用户保存数据库
	 * @param userJSON：前台传参
	 * @return
	 */
	@RequestMapping("/register")
	public SysResult saveUser(String userJSON) throws IOException {
		User user = ObjectMapperUtil.toObject(userJSON, User.class);
		userService.saveUser(user);
		return SysResult.success();
	}

	/**
	 * 获取登录用户并返回
	 * @param ticket
	 * @return
	 */
	@RequestMapping("query/{ticket}")
	public JSONPObject getLoginUser(@PathVariable String ticket, String callback) {
		JSONPObject object = null;
		String json = redisService.get(ticket);
		if (StringUtil.isEmpty(json)) {
			throw new JSONPException(callback, "Redis获取登录用户失败");
		}
		object = new JSONPObject(callback, SysResult.success(json));
		return object;
	}
}
