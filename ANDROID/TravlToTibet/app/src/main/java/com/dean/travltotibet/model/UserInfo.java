package com.dean.travltotibet.model;

public class UserInfo {
	public static String MALE = "m";
	public static String FEMALE = "f";

	private String userId;
	private String userIcon;
	private String userName;
	private String userGender;

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public static enum Gender {BOY, GIRL}

}
