package com.xiaoniu.util;


import com.xiaoniu.Exception.NoSuchLoginUserException;
import com.xiaoniu.Exception.ValidationException;
import com.xiaoniu.pojo.User;

/**
 * 校验参数的工具类
 */
public abstract class ValidUtil {

	private static String idMsg = "id参数不合法：";

	/** ID校验 */
	public static void validateId(Long id) {
		if(id == null || id < 1) {
			throw new ValidationException(idMsg);
		}
	}

	/** ID校验 */
	public static void validateId(Integer id) {
		validatePosInteger(id, idMsg +id);
	}

	/** 索引页码校验 */
	public static void validatePageIndex(Integer pageIndex) {
		validatePosInteger(pageIndex, "索引页码不合法:"+pageIndex);
	}

	/** 正整数范围校验 */
	private static void validatePosInteger(Integer num, String msg) {
		if(num == null || num < 1) {
			throw new ValidationException(msg);
		}
	}
	
	/** 判断对象是否为空 */
	public static boolean isNotEmpty(Object obj) {
		if(obj != null) {
			return true;
		}
		return false;
	}
	
	/** 对象的空校验，不为空时报错 */
	public static void requireNull(Object obj) {
		if(obj != null) {
			throw new ValidationException("参数不能有值");
		}
	}
	
	/** 对象的非空校验 */
	public static void requireNotNull(Object obj) {
		if(obj == null) {
			throw new ValidationException("参数不得为空");
		}
	}
	
	/** 对象的非空校验 */
	public static void requireNotNull(Object... objs) {
		for (Object object : objs) {
			if(!isNotEmpty(object)) {
				throw new ValidationException("参数不得为空");
			}
		}
	}
	
	/**
	 * 需要登录用户
	 * @param user
	 */
	public static void requireLoginUser(User user) {
		requireNotNull(new NoSuchLoginUserException(), user);
	}
	
	/**
	 * 自定义异常的非空校验
	 * @param e
	 * @param obj
	 */
	public static void requireNotNull(RuntimeException e, Object obj) {
		if(obj == null) {
			throw e;
		}
	}
	
	/** 字符串的非空校验 
	 * @throws Exception 
	 * */
	public static void requireNotEmpty(
			RuntimeException e, CharSequence... css) {
		if(StringUtil.isAnyEmpty(css)) {
			throw e;
		}
	}
	
	/** 重载的字符串非空校验 */
	public static void requireNotEmpty(CharSequence... css) {
		requireNotEmpty(new ValidationException("参数不得为空"), css);
	}
}
