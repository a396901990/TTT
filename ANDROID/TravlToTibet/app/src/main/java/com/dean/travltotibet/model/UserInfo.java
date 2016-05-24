package com.dean.travltotibet.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class UserInfo extends BmobUser{
	public static String MALE = "m";
	public static String FEMALE = "f";

	private String userId;
	private String userIcon;
	private String userName;
	private String userGender;

	private BmobRelation QARequest;

	private BmobRelation TeamRequest;

	private BmobRelation QAFavorite;

	private BmobRelation TeamFavorite;

	private BmobRelation UserMessage;

	private BmobRelation Moment;

	public BmobRelation getUserMessage() {
		return UserMessage;
	}

	public void setUserMessage(BmobRelation userMessage) {
		UserMessage = userMessage;
	}

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

	public BmobRelation getQARequest() {
		return QARequest;
	}

	public void setQARequest(BmobRelation QARequest) {
		this.QARequest = QARequest;
	}

	public BmobRelation getTeamRequest() {
		return TeamRequest;
	}

	public void setTeamRequest(BmobRelation teamRequest) {
		TeamRequest = teamRequest;
	}

	public BmobRelation getQAFavorite() {
		return QAFavorite;
	}

	public void setQAFavorite(BmobRelation QAFavorite) {
		this.QAFavorite = QAFavorite;
	}

	public BmobRelation getTeamFavorite() {
		return TeamFavorite;
	}

	public void setTeamFavorite(BmobRelation teamFavorite) {
		TeamFavorite = teamFavorite;
	}

	public BmobRelation getMoment() {
		return Moment;
	}

	public void setMoment(BmobRelation moment) {
		Moment = moment;
	}
}
