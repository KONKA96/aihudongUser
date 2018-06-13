package com.model;

public class Version {
	private String appname;
	private String appversion;
	public Version() {
		super();
	}
	public Version(String appname, String appversion) {
		super();
		this.appname = appname;
		this.appversion = appversion;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getAppversion() {
		return appversion;
	}
	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}
	@Override
	public String toString() {
		return "Version [appname=" + appname + ", appversion=" + appversion + "]";
	}
	
}
