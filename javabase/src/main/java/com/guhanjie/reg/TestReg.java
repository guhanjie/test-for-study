package com.guhanjie.reg;

import java.util.Scanner;
import java.util.regex.Pattern;


public class TestReg {

	public static boolean isID(String str){ 
	    Pattern pattern = Pattern.compile("(^\\d{15}$)|(^\\d{17}(\\d|x|X)$)"); 
	    return pattern.matcher(str).matches();    
	 } 
	
	public static boolean isNumeric(String id){
    	Pattern pattern = Pattern.compile("[0-9]*");
    	return pattern.matcher(id).matches();
    }
	
	public static boolean is_zh_en(String str) {
		Pattern pattern = Pattern.compile("^[a-zA-Z\u4E00-\u9FA5]{1,16}$");
		return pattern.matcher(str).matches();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		while(true) {
			String string = scanner.nextLine();
			System.out.println(is_zh_en(string));
		}
	}

}
