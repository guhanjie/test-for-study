package com.guhanjie.image.model;

import java.util.ArrayList;
import java.util.List;

public class App {
	private String appName;
	private List<String> authenrizeItemList;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public List<String> getAuthenrizeItemList() {
		return authenrizeItemList;
	}
	public void setAuthenrizeItemList(List<String> authenrizeItemList) {
		this.authenrizeItemList = authenrizeItemList;
	}
	public App(int i) {
		appName = "Ӧ������"+i;
		authenrizeItemList = new ArrayList<String>();
		authenrizeItemList.add("��Ȩ��1��balabala...");
		authenrizeItemList.add("��Ȩ��2��balabala...");
		authenrizeItemList.add("��Ȩ��3��balabala...");
		authenrizeItemList.add("��Ȩ��4��balabala...");
		authenrizeItemList.add("��Ȩ��5��balabala...");
	}
}
