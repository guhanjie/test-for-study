package com.guhanjie.jdk.dynamicproxy;

import java.lang.reflect.Proxy;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForumServiceTest {

	static ForumService target;
	static ForumService proxy;
	
	@BeforeClass
	public static void init() {
		target = new ForumServiceImpl();
		PerformanceHandler handler = new PerformanceHandler(target);
		proxy = (ForumService)Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), handler);
	}
	
	@Test
	public void testAddTopic() {
		proxy.addTopic(123);
	}

	@Test
	public void testRemoveTopic() {
		proxy.removeTopic(321);
	}

}
