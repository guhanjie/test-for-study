/** 
 * Project Name:		test 
 * Package Name:	test 
 * File Name:			Main.java 
 * Create Date:		2016年5月20日 下午2:08:58 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package test.spring.lifecycle;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class Name:		Main<br/>
 * Description:		[description]
 * @time				2016年5月20日 下午2:08:58
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class Main {
	
	/**
	 * Method Name:	main<br/>
	 * Description:			[description]
	 * @author				guhanjie
	 * @time					2016年5月20日 下午2:08:59
	 * @param args 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
		Thread.sleep(5000);
		ac.close();
	}
}
