package com.system.pojo;

public class Template {
	private String name;
	
	private String userName;
	
	public Template(String name, String userName) {
		this.name = name;
		this.userName = userName;
	}
	
	public Template() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
