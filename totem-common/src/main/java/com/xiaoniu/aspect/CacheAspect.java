package com.xiaoniu.aspect;

import com.xiaoniu.annotation.Cacheable;
import com.xiaoniu.constant.enums.KeyType;
import com.xiaoniu.service.RedisService;
import com.xiaoniu.util.ObjectMapperUtil;
import com.xiaoniu.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/11 16:02
 */
@Service
@Aspect
@EnableAspectJAutoProxy
public class CacheAspect {

    @Autowired
    private RedisService jedis;

    private Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    /**
     * 在切面中控制方法从缓存或数据库中获
     * 取数据
     * @param joinPoint 切入点
     * @param cache 缓存标记
     * @return 原方法的返回值
     */
    @Around("@annotation(cache)")
    public Object getRedisCache(ProceedingJoinPoint joinPoint, Cacheable cache) throws Throwable {
        // 从注解中获取键
        String key = getKeyFromAnnotation(joinPoint, cache);
        // 从缓存中获取值
        String result = jedis.get(key);
        // 根据结果控制分支
        if (StringUtil.isEmpty(result)) {
            /* 从数据库中取值 并将结果存在缓存中 */
            Object target = joinPoint.proceed();
            String json = ObjectMapperUtil.toJson(target);
            if (cache.seconds() != 0) {
                jedis.setex(key, cache.seconds(), json);
            } else {
                jedis.set(key, json);
            }
            logger.debug(" ====================== redis set =========================");
            logger.debug("redis has cached data with key: "+ key + ", and value :"
                    + json.substring(0, Math.min(json.length(), 20)) + "...");
            return target;
        }
        /* 将Json串转化成对象返回 */
        // 获取返回值类型
        Class<?> returnType = getReturnType(joinPoint);
        logger.debug(" ====================== redis get =========================");
        logger.debug("redis get cached data with key: "+ key);
        return ObjectMapperUtil.toObject(result, returnType);
    }


    /** 从注解中获取键 */
    private String getKeyFromAnnotation(ProceedingJoinPoint joinPoint, Cacheable cache) {
        StringBuilder sb = new StringBuilder(cache.key());
        KeyType keyType = cache.keyType();
        switch (keyType) {
            case EMPTY:
                break;
            case AUTO:
                sb.append("_"+joinPoint.getArgs()[0]);
                break;
            case FULL_ARGS:
                getEntireKey(sb, joinPoint.getArgs());
        }
        return sb.toString();
    }

    /** 拼接完整不为空的参数作为键 */
    private void getEntireKey(StringBuilder sb, Object[] args) {
        List<Object> effectiveArgs = new ArrayList<>();
        for (Object arg : args) {
            if (arg != null) effectiveArgs.add(arg);
        }
        for (Object arg : effectiveArgs) {
            sb.append("_" + arg);
        }
    }

    /** 获取返回值类型 */
    private Class<?> getReturnType(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getReturnType();
    }
}
