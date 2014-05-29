package com.guhanjie.image.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {

	private String isvName;
	private List<App> appList;
	private List<Version> versionList;
	
	public Model() {
		isvName = "ÊÛÂô°ü";
		appList = new ArrayList<App>();
		Random random = new Random();
		int appSize = random.nextInt(5)+1;
		for(int i=1; i<=appSize; i++) {
			appList.add(new App(i));
		}
		versionList = new ArrayList<Version>();
		int versionSize = random.nextInt(5)+1;
		for(int i=1; i<=versionSize; i++) {
			versionList.add(new Version(i, appList));
		}
	}

	public String getIsvName() {
		return isvName;
	}

	public void setIsvName(String isvName) {
		this.isvName = isvName;
	}

	public List<App> getAppList() {
		return appList;
	}

	public List<Version> getVersionList() {
		return versionList;
	}

	public void setVersionList(List<Version> versionList) {
		this.versionList = versionList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
	}

}