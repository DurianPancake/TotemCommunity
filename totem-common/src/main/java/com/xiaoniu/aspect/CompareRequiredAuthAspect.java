package com.xiaoniu.aspect;

import com.xiaoniu.Exception.AuthorityException;
import com.xiaoniu.Exception.NoSuchLoginUserException;
import com.xiaoniu.annotation.RequiresAuth;
import com.xiaoniu.pojo.User;
import com.xiaoniu.service.RedisService;
import com.xiaoniu.util.ObjectMapperUtil;
import com.xiaoniu.util.StringUtil;
import com.xiaoniu.util.UserThreadLocal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * @Author: LLH
 * @Date: 2019/6/21 8:55
 */
@Aspect
@Service
@EnableAspectJAutoProxy
public class CompareRequiredAuthAspect {

    @Autowired
    private RedisService jedis;

    private Logger logger = LoggerFactory.getLogger(CompareRequiredAuthAspect.class);

    /**
     * 比较用户的权限
     *    优先级： 先取权限枚举，再取注解中的字符串值
     * @param joinPoint 切入点
     * @param requiredAuth 需求权限标记
     * @return 原方法的返回值
     */
    @Before("@annotation(requiredAuth)")
    public void checkAuthBeforeMethod(JoinPoint joinPoint, RequiresAuth requiredAuth) throws Throwable {

        // 获取登录用户
        User user = UserThreadLocal.get();
        if (user == null) {
            throw new NoSuchLoginUserException();
        }
        /*
         * 从注解中获取键
         * 如果枚举中为空，则尝试获取注解的key值
         */
        String key = requiredAuth.value().key();
        if (StringUtil.isEmpty(key)) {
            key = requiredAuth.key();
        }
        // 检查键值是否有效
        if (StringUtil.isEmpty(key)) {
            Method targerMethod = getTargerMethod(joinPoint);
            logger.warn(targerMethod.getName()+"可能没有被正确的赋予权限需求，请检查！");
            return;
        }
        // 转化为int权限值
        Integer requiredAuthValue = Integer.valueOf(jedis.get(key));
        // 比较权限值
        if (requiredAuthValue != (requiredAuthValue & user.getRoleAuth())) {
            // 不满足权限时报错
            throw new AuthorityException();
        }
    }

    /**
     * 获取目标方法
     */
    private Method getTargerMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = targetClass
                .getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        return method;
    }
}
