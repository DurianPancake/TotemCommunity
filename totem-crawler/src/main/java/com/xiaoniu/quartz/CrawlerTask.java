package com.xiaoniu.quartz;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoniu.service.dubbo.DubboCrawlerService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Author: LLH
 * @Date: 2019/6/20 18:21
 */
public class CrawlerTask extends QuartzJobBean {

    @Reference
    private DubboCrawlerService crawlerService;

    /**
     * 执行定时任务
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            crawlerService.run();
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
