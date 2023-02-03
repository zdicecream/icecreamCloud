package com.zdinit.icecreamcloud.dispatch.config;


import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 整合Quartz ，默认多线程执行
 *
 * springboot自带 springTask 也是一种实现方式，但是不能持久化，暂不确定使用哪个
 */
@Configuration
public class QuartzConfig {
    @Autowired
    private ApplicationContext applicationContext;
    @Bean
    @ConditionalOnMissingBean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setOverwriteExistingJobs(true);//覆盖已存在的任务
        schedulerFactoryBean.setStartupDelay(60);//延时60秒启动定时任务，避免系统未完全启动却开始执行定时任务的情况

        /**
         * 解决Quartz中无法注入bean问题，原因 Quartz和spring分开实例化自己的类，需要将Quartz交给spring管理
         */
        SpringBeanJobFactory springBeanJobFactory = new SpringBeanJobFactory();
        springBeanJobFactory.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(springBeanJobFactory);
        return schedulerFactoryBean;
    }

    // 创建schedule
    @Bean(name = "scheduler")
    public Scheduler scheduler() {
        return schedulerFactoryBean(applicationContext).getScheduler();
    }
}
