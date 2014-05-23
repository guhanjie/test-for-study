package com.guhanjie.jdk.dynamicproxy;

public class ForumServiceImpl implements ForumService{

	public void addTopic(int topicId) {
		System.out.println("add topic by "+topicId);
	}
	
	public void removeTopic(int topicId) {
		System.out.println("remove topic by "+topicId);
	}
}
