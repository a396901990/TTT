package com.dean.travltotibet.model;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class Article extends BaseCommentBmobObject {

    private String id;
    private String title;
    private String subTitle;
    private String author;
    private String titleImage;
    private String type;
    private Number like;
    private Number watch;
    private String url;
    private Boolean isDisplay;
    private String shareUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Number getWatch() {
        return watch;
    }

    public void setWatch(Number watch) {
        this.watch = watch;
    }

    public Number getLike() {
        return like;
    }

    public void setLike(Number like) {
        this.like = like;
    }
}
