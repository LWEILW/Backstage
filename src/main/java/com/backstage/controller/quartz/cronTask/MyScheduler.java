package com.backstage.controller.quartz.cronTask;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.TimeZone;

public class MyScheduler {

    public static void main(String[] args) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(Task1.class).build();

        // 简单任务
        Trigger t1 = TriggerBuilder.newTrigger().withIdentity("mySimpleTrigger").startNow().build();

        // 每天12:47分执行
        Trigger t2 = TriggerBuilder.newTrigger().withIdentity("myCronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();

        // 每隔5秒执行
        Trigger t3 = TriggerBuilder.newTrigger().withIdentity("myCronTrigger").
                withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();

        // 每天12:47(GMT+8)执行, 注意使用了inTimeZone。如果schedule是根据客户时区安排的，就非常有用，顺带解决了DSTSaving的问题
        Trigger t4 = TriggerBuilder.newTrigger().withIdentity("myCronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 47 12 1/1 * ? *").inTimeZone(TimeZone.getTimeZone("GMT+8"))).build();

        // 通过schedulerFactory获取一个调度器
        Scheduler sc = StdSchedulerFactory.getDefaultScheduler();

        // 把作业和触发器注册到任务调度中
        sc.scheduleJob(job, t2);
        sc.start();
    }
}
