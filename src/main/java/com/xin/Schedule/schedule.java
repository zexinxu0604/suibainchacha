package com.xin.Schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class schedule {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        //创建调度器schedule factory
        SchedulerFactory schedulerFactory  = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        //创建Job
        JobDetail jobDetail = JobBuilder.newJob(com.xin.job.getDailyJob.class).withIdentity("job1", "group1").build();

        // 3、构建Trigger实例,每隔1s执行一次
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()//立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 7 * * ?")).build();//一直执行


        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start ! ------------");
        scheduler.start();
    }
}
