package com.guhanjie.jdk.basicDataStructure;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class HashSetTest {
	public static void main(String[] args) {
		Set<Student> stuSet = new HashSet<Student>();
		Student stt = new Student();
		System.out.println("Student:"+stt.age);
		Student student = new Student("guhanjie", 26);
		stuSet.add(student);
		student.age = 25;
		stuSet.add(student);
		System.out.println(stuSet.size());
		Iterator<Student> it = stuSet.iterator();
		while(it.hasNext()) {
			Student ss = it.next();
			System.out.println(ss.name+" "+ss.age);
		}
	}
}

class Student {
	String name;
	int age;
	public Student() {}
	public Student(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	@Override
	public int hashCode() {
		return this.age;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Student) {
			Student that = (Student)obj;
			return true;
//			return this.name.equals(that.name);
		}
		return false;
	}
}
