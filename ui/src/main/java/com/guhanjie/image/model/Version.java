package com.guhanjie.image.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Version {
	private String versionName;
	private List<App> appList;
	private List<String> authenrizeItemList;
	
	public List<App> getAppList() {
		return appList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
	}

	public List<String> getAuthenrizeItemList() {
		return authenrizeItemList;
	}

	public void setAuthenrizeItemList(List<String> authenrizeItemList) {
		this.authenrizeItemList = authenrizeItemList;
	}

	public Version(int i, List<App> appList) {
		versionName = "°æ±¾Ãû³Æ"+i;
		setAppList(appList);
//		appList = new ArrayList<App>();
//		authenrizeItemList = new ArrayList<String>();
//		Random random = new Random();
//		for(App app : appList) {
//			if(random.nextInt(2) % 2 == 0) {
//				appList.add(app);
//				List<String> aa = app.getAuthenrizeItemList();
//				for(String authenrizeItem : aa) {
//					if(random.nextInt(2) % 2 == 0)
//						authenrizeItemList.add(authenrizeItem);
//				}
//			}
//		}
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
}
