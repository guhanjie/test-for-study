package com.guhanjie.tools;

import java.util.Date;
import java.util.GregorianCalendar;


public class TestCalendar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		System.out.println(gc.getTime());
		gc.add(GregorianCalendar.MONTH, Integer.MAX_VALUE);
		System.out.println(maxOfDay(gc.getTime()));
	}
	
	public static Date maxOfDay(Date date){
    	GregorianCalendar gc = new GregorianCalendar();
    	gc.setTime(date);
    	gc.set(GregorianCalendar.HOUR, 59);
    	gc.set(GregorianCalendar.MINUTE, 59);
    	gc.set(GregorianCalendar.SECOND, 59);
    	return gc.getTime();
    }

}
