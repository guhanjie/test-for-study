package com.guhanjie.jdk.classloader;

import java.io.File;

public class ResourcePath {

	public static void main(String[] args) throws Exception{
		Test tt = new Test();		
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());
		System.out.println(Test.class.getClassLoader().getResource("").toURI().getPath());
		System.out.println(Test.class.getResource("").toURI().getPath());
		System.out.println(Test.class.getResource("/").toURI().getPath());
		System.out.println(ClassLoader.getSystemResource("").toURI().getPath());
		System.out.println(new File("/").getAbsolutePath());
		System.out.println(System.getProperty("user.home"));
	}
}