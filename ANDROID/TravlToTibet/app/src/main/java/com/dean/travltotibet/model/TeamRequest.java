package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class TeamRequest extends BmobObject {

    public String userId;

    public String userName;

    public String date;

    public String destination;

    public String type;

    public String title;

    public String contact;

    public String content;

    private int comments;

    private int watch;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDestination() {
        return destination;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getWatch() {
        return watch;
    }

    public void setWatch(int watch) {
        this.watch = watch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
