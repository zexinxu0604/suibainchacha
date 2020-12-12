package com.xin.Schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class schedule implements ServletContextListener {

    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent){
        //创建调度器schedule factory
        schedulerFactory  = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        //创建Job
        JobDetail jobDetail = JobBuilder.newJob(com.xin.job.getDailyJob.class).withIdentity("job1", "group1").build();

        // 3、构建Trigger实例,每隔1s执行一次
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()//立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule("0 58 17 * * ?")).build();//一直执行


        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        System.out.println("--------scheduler start ! ------------");
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
