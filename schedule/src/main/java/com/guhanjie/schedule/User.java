/** 
 * Project Name:		schedule 
 * Package Name:	com.guhanjie.schedule 
 * File Name:			Item.java 
 * Create Date:		2016年5月19日 下午9:43:53 
 * Copyright (c) 2008-2016, guhanjie All Rights Reserved.
 */  
package com.guhanjie.schedule;

/**
 * Class Name:		User<br/>
 * Description:		[description]
 * @time				2016年5月19日 下午9:43:53
 * @author			guhanjie
 * @version			1.0.0 
 * @since 			JDK 1.6 
 */
public class User {
    private String name;
    private int age;
    
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String toString() {
        return name+age;
    }
}
