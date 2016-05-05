package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 12/17/15.
 */
public class FeedBack extends BmobObject {
    private String feedback;
    private String email;
    private String phone;
    private String other;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
