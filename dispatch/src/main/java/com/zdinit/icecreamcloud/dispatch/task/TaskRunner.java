package com.zdinit.icecreamcloud.dispatch.task;

import com.zdinit.icecreamcloud.dispatch.config.QuartzConfig;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 定时任务触发Runner
 */
@Component
@Order(1)
@Slf4j
public class TaskRunner implements CommandLineRunner {

    @Autowired
    private QuartzConfig quartzConfig;
    /* 任务 */
    private static final String MAIL_TASK = "MailTask";
    private static final String WECAT_TASK = "WeCatTask";
    private static final String SMS_TASK = "SMSTask";

    /* 任务组 */
    private static final String MSG_GROUP = "MsgGroup";

    /* 触发器 */
    private static final String TRIGGER = "MsgGroup";

    @Override
    public void run(String... args) throws Exception {
        this.scheduleJobs();
        log.info(WECAT_TASK+"服务启动:"+ Calendar.getInstance().getTime());
    }

    /**
     * SimpleSchedule  每隔一段时间运行, 好像不能同时用，弃用
     */
//    @Bean
//    public JobDetail jobDetail(){
//        return JobBuilder.newJob(MailTask.class).withIdentity(MAIL_TASK).storeDurably().build();
//    }
//    @Bean
//    public Trigger trigger(){
//        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(10)  //时间
//                .repeatForever();
//        return TriggerBuilder.newTrigger().forJob(jobDetail()).withIdentity(MAIL_TASK).withSchedule(simpleScheduleBuilder).build();
//    }


    /**
     * CronSchedule Cron格式 ，指定时间格式
     表达式意义
     "0 0 12 * *?" 每天中午12点触发
     "0 15 10 ? **" 每天上午10:15触发
     "0 15 10 * *?" 每天上午10:15触发
     "0 15 10 * * ?*" 每天上午10:15触发
     "0 15 10 * * ?2005" 2005年的每天上午10:15触发
     "0 * 14 * *?" 在每天下午2点到下午2:59期间的每1分钟触发
     "0 0/5 14 * *?" 在每天下午2点到下午2:55期间的每5分钟触发
     "0 0/5 14,18 ** ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
     "0 0-5 14 * *?" 在每天下午2点到下午2:05期间的每1分钟触发
     "0 10,44 14 ? 3WED" 每年三月的星期三的下午2:10和2:44触发
     "0 15 10 ? *MON-FRI" 周一至周五的上午10:15触发
     "0 15 10 15 *?" 每月15日上午10:15触发
     "0 15 10 L *?" 每月最后一日的上午10:15触发
     "0 15 10 ? *6L" 每月的最后一个星期五上午10:15触发
     "0 15 10 ? * 6L2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
     "0 15 10 ? *6#3" 每月的第三个星期五上午10:15触发
     */
    private void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = quartzConfig.scheduler();
        JobDetail jobDetail = JobBuilder.newJob(WeCatTask.class).withIdentity(WECAT_TASK,MSG_GROUP).build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("5 * * * * ? *");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER,MSG_GROUP).withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

}
