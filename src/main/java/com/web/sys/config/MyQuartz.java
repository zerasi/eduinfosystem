package com.web.sys.config;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class MyQuartz extends QuartzJobBean {

    private static Logger log = LoggerFactory.getLogger(MyQuartz.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        int count = attachmentService.deleteNotInuse();
//        log.info("success to clear useless file in table attachment.delete "+ count +" record.");
    }
}