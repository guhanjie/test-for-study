package com.guhanjie.jdk.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

public class PerformanceHandler implements InvocationHandler {

	private Object target;
	
	public PerformanceHandler(Object target) {
		this.target = target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("Before method invoke, print time: "+ new Date());
		Object obj = method.invoke(target, args);
		System.out.println("After method invoke, print time: "+ new Date());
		return obj;
	}

}
