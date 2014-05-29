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
		appName = "应用名称"+i;
		authenrizeItemList = new ArrayList<String>();
		authenrizeItemList.add("授权点1：balabala...");
		authenrizeItemList.add("授权点2：balabala...");
		authenrizeItemList.add("授权点3：balabala...");
		authenrizeItemList.add("授权点4：balabala...");
		authenrizeItemList.add("授权点5：balabala...");
	}
}
