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

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;
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
//@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
public class SimpleJob implements Job {
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    /** 
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
//        try {
//            if(((SimpleTrigger)jec.getTrigger()).getTimesTriggered() % 2 == 0)
//                Thread.sleep(5000);
//        }
//        catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        Date fireTime = jec.getFireTime();
        Date scheduledFireTime = jec.getScheduledFireTime();
//        Date nextFireTime = jec.getNextFireTime();
//        Scheduler scheduler = jec.getScheduler();
//        Job job = jec.getJobInstance();
//        JobDetail jobDetail = jec.getJobDetail();
//        JobDataMap jdp = jec.getMergedJobDataMap();
        SimpleTrigger trigger = (SimpleTrigger)jec.getTrigger();
//        Calendar calendar = jec.getCalendar();
//        Object result = jec.getResult();
//        int count = jdp.getInt("count");
//        jobDetail.getJobDataMap().put("count", count+1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        System.out.println("*** fireTime = "+sdf.format(fireTime));
        System.out.println("*** scheduledFireTime = "+sdf.format(scheduledFireTime));
//        System.out.println("*** nextFireTime = "+sdf.format(nextFireTime));
//        System.out.println("*** JobExecutionContext = "+JSONObject.toJSONString(jec));
//        System.out.println("*** scheduler = "+JSONObject.toJSONString(scheduler));
//        System.out.println("*** job = "+JSONObject.toJSONString(job));
//        System.out.println("*** jobDetail = "+JSONObject.toJSONString(jobDetail));
//        System.out.println("*** MergedJobDataMap = "+JSONObject.toJSONString(jdp));
//        System.out.println("*** trigger = "+JSONObject.toJSONString(trigger, true));
//        System.out.println("*** calendar = "+JSONObject.toJSONString(calendar));
//        System.out.println("*** result = "+JSONObject.toJSONString(result));
        System.out.println("*** RepeatCount = "+trigger.getRepeatCount()+"---- TimesTriggered = "+trigger.getTimesTriggered());
        System.out.println("User:" +user);
        System.out.println("===================================");
    }
}
