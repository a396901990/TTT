package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class QARequest extends BmobObject {

    public final static String PASS_STATUS = "P";

    public final static String WAIT_STATUS = "W";

    public final static String NO_PASS_STATUS = "N";

    public String status;

    public String title;

    public String content;

    public String userName;

    public String userIcon;

    public String userGender;

    private Number comments;

    private Number watch;

    private UserInfo user;

    private BmobRelation questionUsers;

    private BmobRelation answers;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public BmobRelation getQuestionUsers() {
        return questionUsers;
    }

    public void setQuestionUsers(BmobRelation questionUsers) {
        this.questionUsers = questionUsers;
    }

    public BmobRelation getAnswers() {
        return answers;
    }

    public void setAnswers(BmobRelation answers) {
        this.answers = answers;
    }

    public Number getWatch() {
        return watch;
    }

    public void setWatch(Number watch) {
        this.watch = watch;
    }

    public Number getComments() {
        return comments;
    }

    public void setComments(Number comments) {
        this.comments = comments;
    }
}