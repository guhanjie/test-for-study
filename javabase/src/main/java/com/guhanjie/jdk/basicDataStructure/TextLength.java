package com.guhanjie.jdk.basicDataStructure;


public class TextLength {
	public static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			String str = String.valueOf(text.charAt(i));
			if (str.getBytes().length > 1) {
				length++;
			}
		}
		length = (length % 2 == 0) ? length / 2 : length / 2 + 1;
		return length;
	}
	
	public static void main(String[] args) {
		getLength("�й�123T()");
	}
}
