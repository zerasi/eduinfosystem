package com.web.sys.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail myyQuartz(){
        return JobBuilder.newJob(MyQuartz.class).withIdentity("myQuartz").storeDurably().build();
    }

    @Bean
    public Trigger myQuartz(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
               // .withIntervalInSeconds(60*60*24)  //设置时间周期单位秒
                .withIntervalInHours(24)
                .repeatForever();

        return TriggerBuilder.newTrigger().forJob(myyQuartz())
                .withIdentity("myQuartz")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
