/** 
 * Project Name:		test 
 * Package Name:	test 
 * File Name:			AbstractService.java 
 * Create Date:		2016年5月20日 下午3:05:34 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package test.spring.lifecycle;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class Name:		AbstractService<br/>
 * Description:		[description]
 * @time				2016年5月20日 下午3:05:34
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public abstract class AbstractService {
	@Autowired
	public UserService userService;
}
