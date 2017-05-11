/**
 * 
 */
package com.testing.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author gtorress
 *
 */
public class HelloJob implements Job{
	public static int cantidadLlamada=0;
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		cantidadLlamada++;
		System.out.println(cantidadLlamada+" Hola "+(new Date().toString()));
		
	}

}
