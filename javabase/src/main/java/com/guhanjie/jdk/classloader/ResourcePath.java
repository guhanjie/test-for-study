package com.guhanjie.jdk.classloader;

import java.io.File;

public class ResourcePath {

	public static void main(String[] args) throws Exception{
		//当前线程上下文类加载器的根路径:  /D:/workspace/hadoop.auth/target/classes/
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());
		//加载当前类的类加载器的根路径:	 /D:/workspace/hadoop.auth/target/classes/
		System.out.println(ResourceLoaderUtil.class.getClassLoader().getResource("").toURI().getPath());
		//当前类的当前路径:  /D:/workspace/hadoop.auth/target/classes/com/pinganfu/hadoop/auth/
		System.out.println(ResourceLoaderUtil.class.getResource("").toURI().getPath());
		//当前类的根路径:  /D:/workspace/hadoop.auth/target/classes/
		System.out.println(ResourceLoaderUtil.class.getResource("/").toURI().getPath());
		//系统类加载器（应用类加载器）:  sun.misc.Launcher$AppClassLoader@73d16e93
		System.out.println(ClassLoader.getSystemClassLoader());
		//系统类加载器（应用类加载器）的根路径:  /D:/workspace/hadoop.auth/target/classes/
		System.out.println(ClassLoader.getSystemResource("").toURI().getPath());
		//系统类加载（扩展类加载器）:  sun.misc.Launcher$ExtClassLoader@15db9742
		System.out.println(ClassLoader.getSystemClassLoader().getParent());
		//系统类加载器（扩展类加载器）的根路径:  /D:/workspace/hadoop.auth/target/classes/
		System.out.println(ClassLoader.getSystemClassLoader().getParent().getSystemResource("").toURI().getPath());
		//系统类加载器（根类加载器为null，是native code）:  null
		System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
		//绝对路径:  D:\
		System.out.println(new File("/").getAbsolutePath());
		//用户当前目录:  D:\workspace\hadoop.auth
		System.out.println(System.getProperty("user.dir").toString());
		//用户家目录:  D:\Users\guhanjie
		System.out.println(System.getProperty("user.home").toString());
		
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
