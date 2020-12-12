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
        JobDetail getDailyJob = JobBuilder.newJob(com.xin.job.getDailyJob.class).withIdentity("job1", "group1").build();
        JobDetail oneTimeJob = JobBuilder.newJob(com.xin.job.getDailyJob.class).withIdentity("job2", "testjob").build();

        // 3、构建Trigger实例,按照日历执行
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()//立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule("0 10 7 * * ?")).build();//一直执行

        Trigger trigger1 =  TriggerBuilder.newTrigger().withIdentity("trigger2","testTrigger").startNow().build();

        try {
            scheduler.scheduleJob(getDailyJob, trigger);
            scheduler.scheduleJob(oneTimeJob, trigger1);
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
