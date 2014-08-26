package com.guhanjie.jdk.basicDataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ArraytoList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Long id = null;
		String str = String.valueOf(id);
		System.out.println(str);
		
		String[] names = new String[3];
		names[0] = "guhanjie";
		names[1] = "yinxia";
		names[2] = "www";
		System.out.println(Arrays.asList(names));
		
		List<String> list = new ArrayList<String>();
		list.add("ddd");
		list.add("ccc");
		System.out.println(list);
		
		Byte bb = (byte) 2;
		if(bb == 2L) {
			System.out.println("byte into integer");
		}
	}

}
