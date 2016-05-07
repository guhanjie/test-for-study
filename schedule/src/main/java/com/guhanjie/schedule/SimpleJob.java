/** 
 * Project Name:		schedule 
 * Package Name:	com.guhanjie.schedule 
 * File Name:			SimpleJob.java 
 * Create Date:		2016年5月7日 上午12:20:33 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Calendar;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import com.alibaba.fastjson.JSONObject;

/**
 * Class Name:		SimpleJob<br/>
 * Description:		[description]
 * @time				2016年5月7日 上午12:20:33
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class SimpleJob implements Job {
    /** 
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        Date fireTime = jec.getFireTime();
        Date scheduledFireTime = jec.getScheduledFireTime();
//        if(fireTime.getTime()/1000 > scheduledFireTime.getTime()/1000) {
//            System.out.println(""+fireTime.getTime() +">"+ scheduledFireTime.getTime());
//            return;
//        }
        long runTime = jec.getJobRunTime();
        Date nextFireTime = jec.getNextFireTime();
        Date preFireTime = jec.getPreviousFireTime();
        int refireCount = jec.getRefireCount();
        Scheduler scheduler = jec.getScheduler();
        Job job = jec.getJobInstance();
        JobDetail jobDetail = jec.getJobDetail();
        JobDataMap jdp = jec.getMergedJobDataMap();
        Trigger trigger = jec.getTrigger();
        Calendar calendar = jec.getCalendar();
        Object result = jec.getResult();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("*** fireTime = "+sdf.format(fireTime));
        System.out.println("*** scheduledFireTime = "+sdf.format(scheduledFireTime));
//        System.out.println("*** runTime = "+sdf.format(new Date(runTime)));
        System.out.println("*** nextFireTime = "+sdf.format(nextFireTime));
//        System.out.println("*** preFireTime = "+sdf.format(preFireTime));
//        System.out.println("*** refireCount = "+refireCount);
//        System.out.println("*** JobExecutionContext = "+JSONObject.toJSONString(jec));
//        System.out.println("*** scheduler = "+JSONObject.toJSONString(scheduler));
//        System.out.println("*** job = "+JSONObject.toJSONString(job));
//        System.out.println("*** jobDetail = "+JSONObject.toJSONString(jobDetail));
//        System.out.println("*** MergedJobDataMap = "+JSONObject.toJSONString(jdp));
        System.out.println("*** trigger = "+JSONObject.toJSONString(trigger));
//        System.out.println("*** calendar = "+JSONObject.toJSONString(calendar));
//        System.out.println("*** result = "+JSONObject.toJSONString(result));
        System.out.println("===================================");
    }
}
