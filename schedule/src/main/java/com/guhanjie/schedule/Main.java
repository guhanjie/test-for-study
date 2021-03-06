/** 
 * Project Name:		schedule 
 * Package Name:	com.guhanjie.schedule 
 * File Name:			Main.java 
 * Create Date:		2016年5月6日 下午11:14:43 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.schedule;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Class Name:		Main<br/>
 * Description:		[description]
 * @time				2016年5月6日 下午11:14:43
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class Main {
    /**
     * Method Name:	main<br/>
     * Description:			[description]
     * @author				guhanjie
     * @time					2016年5月6日 下午11:14:44
     * @param args 
     * @throws SchedulerException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        // Grab the Scheduler instance from the Factory
        StdSchedulerFactory sf = new StdSchedulerFactory();
        sf.initialize("quartz.properties");
        Scheduler scheduler = sf.getScheduler();
        JobDataMap jdm = new JobDataMap();
        jdm.put("user", new User("guhanjie", 27));
        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(SimpleJob.class)
                                    .withIdentity("job1", "group1")
                                    .usingJobData("count", 0)
                                    .usingJobData(jdm)
                                    .storeDurably()
                                    .build();

        // Trigger the job to run now, and then repeat every 1 seconds
        Trigger trigger = newTrigger()
                                    .withIdentity("trigger1", "group1")
                                    .startNow()
                                          .withSchedule(simpleSchedule()
                                            .withIntervalInSeconds(5)
                                            .withRepeatCount(100)
//                                            .withMisfireHandlingInstructionFireNow()
//                                            .withMisfireHandlingInstructionIgnoreMisfires()
//                                            .withMisfireHandlingInstructionNextWithExistingCount()
                                            .withMisfireHandlingInstructionNextWithRemainingCount()
//                                            .withMisfireHandlingInstructionNowWithExistingCount()
//                                            .withMisfireHandlingInstructionNowWithRemainingCount()
                                            )
//                                            .repeatForever())            
                                    .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);

        System.out.println("starting to schedule...");
        scheduler.start();

        Thread.sleep(3000);
        System.out.println("================Pausing===============");
        scheduler.pauseAll();
        Thread.sleep(13000);
        System.out.println("================Resuming===============");
        scheduler.resumeAll();
    }
}
