package com.tung.Entities;

public class User {
	private int userId;
	private String userName;
	private String passWord;
	
	
	public int getUserId(){
		return userId;
	}
	
	public void setUserId(int id){
		this.userId = id;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String name){
		this.userName = name;
	}
	
	public String getPassWord(){
		return passWord;
	}
	
	public void setPassWord(String pass){
		this.passWord = pass;
	}
}
