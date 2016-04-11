package com.dean.travltotibet.model;

import com.dean.travltotibet.R;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class RoadInfo extends BmobObject {

    public final static String PASS_STATUS = "P";

    public final static String WAIT_STATUS = "W";

    public final static String NO_PASS_STATUS = "N";

    public final static String WARRING = "W";

    public final static String IMPORTANT = "I";

    public final static String NORMAL = "N";

    public String title;

    public String route;

    public String content;

    public int comment;

    public String priority;

    public String status;

    private UserInfo user;

    private ImageFile imageFile;

    private String belong;

    private int watch;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public ImageFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(ImageFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public static int getPriorityIcon(String priority) {
        if (WARRING.equals(priority)) {
            return R.drawable.common_warning_badge;
        } else if (IMPORTANT.equals(priority)) {
            return R.drawable.common_caution_badge;
        } else if (NORMAL.equals(priority)) {
            return R.drawable.common_info_badge;
        }

        return 0;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public int getWatch() {
        return watch;
    }

    public void setWatch(int watch) {
        this.watch = watch;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
