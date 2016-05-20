/** 
 * Project Name:		test 
 * Package Name:	test 
 * File Name:			ConcreteService.java 
 * Create Date:		2016年5月20日 下午3:06:51 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package test.spring.lifecycle;

import org.springframework.stereotype.Component;

/**
 * Class Name:		ConcreteService<br/>
 * Description:		[description]
 * @time				2016年5月20日 下午3:06:51
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
@Component
public class ConcreteService extends AbstractService{
	public void doService() {
		userService.sayHello();
	}
}
