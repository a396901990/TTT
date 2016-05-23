package com.dean.travltotibet.model;

/**
 * Created by DeanGuo on 5/23/16.
 */
public class Moment extends BaseCommentBmobObject {

    public final static String PASS_STATUS = "P";

    public final static String WAIT_STATUS = "W";

    public final static String NO_PASS_STATUS = "N";

    public String content;

    public Number comment;

    public String status;

    private UserInfo user;

    private ImageFile imageFile;

    private Number watch;

    private Number like;

    private String location;

    public Number getWatch() {
        return watch;
    }

    public void setWatch(Number watch) {
        this.watch = watch;
    }

    public Number getComment() {
        return comment;
    }

    public void setComment(Number comment) {
        this.comment = comment;
    }

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

    public Number getLike() {
        return like;
    }

    public void setLike(Number like) {
        this.like = like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
