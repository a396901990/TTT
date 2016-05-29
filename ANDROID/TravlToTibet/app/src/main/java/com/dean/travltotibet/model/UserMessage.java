package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 5/11/16.
 */
public class UserMessage extends BmobObject {
    public final static String TEAM_REQUEST_TITLE = "回复了您发布的结伴";
    public final static String QA_REQUEST_TITLE = "回答了您关注的问题";
    public final static String MOMENT_REQUEST_TITLE = "回复了您发布的直播";

    public final static String TEAM_REQUEST_TYPE = "teamRequest";
    public final static String QA_REQUEST_TYPE = "qaRequest";
    public final static String MOMENT_TYPE = "moment";

    public final static String READ_STATUS = "R";
    public final static String UNREAD_STATUS = "UR";

    public String status;
    private String message;
    private String messageTitle;
    private UserInfo receiveUser;
    private UserInfo sendUser;
    private String type;
    private String typeObjectId;

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserInfo getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(UserInfo receiveUser) {
        this.receiveUser = receiveUser;
    }

    public UserInfo getSendUser() {
        return sendUser;
    }

    public void setSendUser(UserInfo sendUser) {
        this.sendUser = sendUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeObjectId() {
        return typeObjectId;
    }

    public void setTypeObjectId(String typeObjectId) {
        this.typeObjectId = typeObjectId;
    }

}
