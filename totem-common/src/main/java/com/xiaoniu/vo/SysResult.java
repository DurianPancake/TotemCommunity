package com.xiaoniu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

// 系统返回值对象
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {

	private Integer status;	// 200 表示成功
	private String msg;		// 表示返回的信息
	private Object data;	// 表示封装的数据
	
	public static SysResult success() {
		return new SysResult(200, null, null);
	}
	
	public static SysResult success(Object data) {
		return new SysResult(200, null, data);
	}

	public static SysResult success(String msg, Object data) {
		return new SysResult(200, msg, data);
	}
	
	public static SysResult fail() {
		return new SysResult(201, null, null);
	}
	
	public static SysResult fail(String msg) {
		return new SysResult(201, msg, null);
	}

	public static SysResult fail(Throwable e) {
		return new SysResult(201, e.getMessage(), null);
	}

	public static SysResult fail(String msg, Throwable e) {
		return new SysResult(201, msg, null);
	}
}
