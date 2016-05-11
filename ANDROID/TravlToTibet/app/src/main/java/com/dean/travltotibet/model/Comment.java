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

    public  final static String ARTICLE_COMMENT = "article";
    public  final static String TEAM_REQUEST_COMMENT = "teamRequest";
    public  final static String HOTEL_COMMENT = "hotel";
    public  final static String SCENIC_COMMENT = "scenic";
    public  final static String ROAD_INFO_COMMENT = "roadInfo";
    public  final static String ANSWER_COMMENT = "answer";

    private String type_object_id;
    private String type;
    private String type_desc;
    private String rating;
    private String quote_id; // old logic
    private String comment;
    private Comment commentQuote;
    private UserInfo user;
    private String user_icon; // old logic
    private String user_name; // old logic
    private int like;
    private int dislike;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_desc() {
        return type_desc;
    }

    public void setType_desc(String type_desc) {
        this.type_desc = type_desc;
    }

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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Comment getCommentQuote() {
        return commentQuote;
    }

    public void setCommentQuote(Comment commentQuote) {
        this.commentQuote = commentQuote;
    }

    public String getType_object_id() {
        return type_object_id;
    }

    public void setType_object_id(String type_object_id) {
        this.type_object_id = type_object_id;
    }
}
