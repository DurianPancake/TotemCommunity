package com.xiaoniu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaoniu.mapper") //为mapper接口创建代理对象
// @DubboComponentScan("com.xiaoniu") dubbo提供的Mapper扫描注解，相互替代
public class SpringBootRun {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun.class, args);
	}

}