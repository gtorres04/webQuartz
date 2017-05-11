package com.testing.quartz.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.testing.quartz.job.HelloJob;

public class QuartzUnoListaner implements ServletContextListener {
	Scheduler scheduler;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Context Destroyed");
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			// define the job and tie it to our HelloJob class
			JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("CronQuartzJob", "Group").build();

			// Trigger the job to run on the next round minute
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
					.build();

			Scheduler schedulerAux = new StdSchedulerFactory().getScheduler();
			schedulerAux.start();
			schedulerAux.scheduleJob(job, trigger);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
