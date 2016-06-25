/** 
 * Project Name:		test 
 * Package Name:	test 
 * File Name:			UserService.java 
 * Create Date:		2016年5月20日 下午2:10:34 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package test.spring.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * Class Name:		UserService<br/>
 * Description:		[description]
 * @time				2016年5月20日 下午2:10:34
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class UserService extends LifeCycleSupport implements SmartLifecycle  {
	
	private String name;

	  public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	public void sayHello() {
		logger.info("Doing service...");
		logger.info("Hello "+name);
	}
	
	@PreDestroy
	public void destroy() {
		logger.info("Destroy method.......");
	}

	@Override
	public boolean isRunning() {
		return isStarted();
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		logger.info("Stopping.......");
		callback.run();
	}

	@Override
	public void doInit() {
		logger.info("Init method.......");
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void doStart() {
		logger.info("User service is starting.......");
	}

	@Override
	public void doStop() {
		logger.info("User service is stopping.......");
	}
	
	public void stop() {
		logger.info("User service is stopping.......");
	}
	
}
