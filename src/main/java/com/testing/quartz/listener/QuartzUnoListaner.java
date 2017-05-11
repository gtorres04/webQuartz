package com.testing.quartz.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

@WebListener
public class QuartzUnoListaner extends QuartzInitializerListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		ServletContext ctx = sce.getServletContext();
		StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
		try {
			Scheduler scheduler = factory.getScheduler();
			// define the job and tie it to our HelloJob class
			JobDetail job = JobBuilder.newJob(com.testing.quartz.job.HelloJob.class)
					.withIdentity("dummyJobName", "grupo1").build();

			// Trigger the job to run on the next round minute
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
					.build();
			/*
			 * Trigger trigger = TriggerBuilder .newTrigger()
			 * .withIdentity("dummyTriggerName", "group1") .withSchedule(
			 * CronScheduleBuilder.cronSchedule("0/5 * * * * ?")) .build();
			 */
			// schedule it
			Scheduler schedulerAux = new StdSchedulerFactory().getScheduler();
			schedulerAux.start();
			schedulerAux.scheduleJob(job, trigger);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		super.contextDestroyed(sce);
	}
}
