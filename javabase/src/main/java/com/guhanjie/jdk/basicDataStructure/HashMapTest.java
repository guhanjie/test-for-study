package com.guhanjie.jdk.basicDataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class HashMapTest {
	
	public static void main(String[] args) {
		Map<Long, String> apps = new HashMap<Long, String>();
		apps.put(1234L, "guhanjie");
		apps.put(1234L, "yinxia");
		StringBuilder appNameString = new StringBuilder();
		Collection<String> appNames = apps.values();
		Iterator<String> iterator = appNames.iterator();
		while(iterator.hasNext()) {
			appNameString.append(iterator.next());
			appNameString.append(" ");
		}
		System.out.println(appNames);
	}

}
