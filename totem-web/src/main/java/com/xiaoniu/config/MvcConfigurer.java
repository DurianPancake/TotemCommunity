package com.xiaoniu.config;

import com.xiaoniu.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: LLH
 * @Date: 2019/6/12 11:39
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    //开启匹配后缀型配置
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true);
    }

    /**
     * 新增拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/sys/**", "/comment/reply/**");
    }
}
