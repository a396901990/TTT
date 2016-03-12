package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class UserInfo extends BmobUser{
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
}
