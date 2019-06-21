package com.xiaoniu.handler;

import com.xiaoniu.Exception.ServiceException;
import com.xiaoniu.Exception.ValidationException;
import com.xiaoniu.util.StringUtil;
import com.xiaoniu.vo.SysResult;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @Author: LLH
 * @Date: 2019/6/18 20:31
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    public SysResult doHandlerValidationException(
            ValidationException e) {
        logger.debug(" ============== "+ e.getMessage() +" ==============");
        return handleException(e, "参数校验", e.getMessage(), false);
    }

    /**
     * 处理定时中断异常
     */
    @ExceptionHandler(InterruptedException.class)
    public SysResult doHandlerInterruptedException(InterruptedException e) {
        return handleException(e, "定时中断", null, false);
    }

    /**
     * 处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public SysResult doHandlerServiceException(
            ServiceException e) {
        return handleException(e, "业务", null, false);
    }

    /**
     * 处理爬虫定时任务异常
     * @param e
     * @return
     */
    @ExceptionHandler(JobExecutionException.class)
    public SysResult doHandlerJobExecutionException(JobExecutionException e) {
        logger.debug(" ============== "+ e.getClass().getSimpleName() +" ==============");
        return handleException(e, "爬虫定时任务", e.getMessage(), true);
    }

    /**
     * 处理IO及Json转换等流异常
     * @param e
     * @return
     */
    @ExceptionHandler(IOException.class)
    public SysResult doIOException(IOException e) {
        logger.debug(" ============== "+ e.getClass().getSimpleName() +" ==============");
        return handleException(e, "IO", "IO转换异常", true);
    }

    /**
     * 捕获所有运行时期异常
     * @ExceptionHandler 注解修饰的方法为异常
     * @param e 异常
     * @return
     */
    /*处理该注解值类型的异常及其子类的异常 */
    @ExceptionHandler(RuntimeException.class)
    public SysResult doHandleRuntimeException(
            RuntimeException e) {
        return handleException(e, "其他运行", e.getClass().getSimpleName(), true);
    }

    /**
     * 处理基本异常
     */
    @ExceptionHandler(Exception.class)
    public SysResult doHandlerException(Exception e) {
        return handleException(e, "未知", "系统未知异常", true);
    }


    /**
     * 处理异常的通用方法
     * @param e：异常本身
     * @param eName： 异常类型名称
     * @param message： 回显信息
     * @param needLog： 是否需要在控制台完整输出
     * @return
     */
    private SysResult handleException(Throwable e, String eName, String message, boolean needLog) {
        logger.error(("======== 捕获"+ eName +"异常 ========="));
        if(needLog) {
            // 输出全部栈跟踪
            e.printStackTrace();
        } else {
            // 输出简单类名
            Class<?> c = e.getClass();
            logger.error(("异常类型："+ c.getSimpleName()));
        }
        logger.error(" ========================== ");
        if(StringUtil.isEmpty(message)) {
            return SysResult.fail(e);
        }
        return SysResult.fail(message, e);
    }
}
