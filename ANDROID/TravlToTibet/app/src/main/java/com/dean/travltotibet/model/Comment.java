package com.dean.travltotibet.model;

import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;

import java.util.Comparator;
import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class Comment extends BmobObject {

    public  final static String ARTICLE_COMMENT = "article_comment";
    public  final static String TEAM_REQUEST_COMMENT = "team_request_comment";
    public  final static String HOTEL_COMMENT = "hotel_comment";
    public  final static String SCENIC_COMMENT = "scenic_comment";

    private int like;
    private int dislike;
    private String rating;
    private String quote_id;
    private String quote_text;
    private String quote_user_name;
    private String comment;
    private String user_id;
    private String user_icon;
    private String user_name;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(String quote_id) {
        this.quote_id = quote_id;
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

    public String getQuote_text() {
        return quote_text;
    }

    public void setQuote_text(String quote_text) {
        this.quote_text = quote_text;
    }

    public String getQuote_user_name() {
        return quote_user_name;
    }

    public void setQuote_user_name(String quote_user_name) {
        this.quote_user_name = quote_user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static Comparator<Comment> likeComparator = new Comparator<Comment>() {
        @Override
        public int compare(Comment c1, Comment c2) {
            if (c1.getLike() > c2.getLike()) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    public static Comparator<Comment> timeComparator = new Comparator<Comment>() {
        @Override
        public int compare(Comment c1, Comment c2) {
            Date date1 = DateUtil.parse(c1.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
            Date date2 = DateUtil.parse(c2.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);

            if (date1.getTime() > date2.getTime()) {
                return -1;
            } else {
                return 1;
            }
        }
    };
}
