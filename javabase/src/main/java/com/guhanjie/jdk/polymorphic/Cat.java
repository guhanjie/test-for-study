package com.guhanjie.jdk.polymorphic;


public class Cat extends Animal {

	public String name = "cat";
	public void mimi(String a) {
		System.out.println("Cat:"+a);
	}
	
	public static void main(String[] args) {
		Animal animal = new Animal();
		animal.mimi("xx");
		animal = new Cat();
		animal.mimi("xoxo");
	}
}
