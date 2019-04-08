package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:application.xml");
        MyService ms = ac.getBean(MyService.class);
//        try {
//            ms.throw1();
//        } catch (Exception e) {
////          e.printStackTrace();
//        }
//        try {
//            ms.throw2();
//        } catch (Exception e) {
////          e.printStackTrace();
//        }
        People p = ms.sayHello("Jack");
        System.out.println("===Return:" + p.getName());
        System.out.println("obj in Return:" + p + " hascode:" + p.hashCode());
        
    }
}