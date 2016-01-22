package com.dean.travltotibet.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class Comment extends BmobObject {

    private String rating;
    private String image_url;
    private String route;
    private String comment_time;
    private String comment;
    private String user_name;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
