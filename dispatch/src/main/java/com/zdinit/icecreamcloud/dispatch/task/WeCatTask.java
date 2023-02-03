package com.zdinit.icecreamcloud.dispatch.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
@Slf4j
public class WeCatTask extends QuartzJobBean {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
       redisTemplate.opsForValue().set("data","TaskTest",3000l);
       log.info(redisTemplate.opsForValue().get("data").toString());
    }
}
