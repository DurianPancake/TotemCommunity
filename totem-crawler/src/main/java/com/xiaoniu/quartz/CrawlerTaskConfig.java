package com.xiaoniu.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: LLH
 * @Date: 2019/6/20 18:22
 */
@Configuration
public class CrawlerTaskConfig {

    private final static String IDENTITY = "crawlerTiming";

    @Bean
    public JobDetail crawlerTimingTaskDetail() {
        //指定job的名称和持久化保存任务
        return JobBuilder
                .newJob(CrawlerTask.class)
                .withIdentity(IDENTITY)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger crawlerBeginTrigger() {
        // 使用Cron表达式定义时间，每三十秒执行
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(crawlerTimingTaskDetail())
                            .withIdentity(IDENTITY)
                            .withSchedule(scheduleBuilder).build();
    }
}
