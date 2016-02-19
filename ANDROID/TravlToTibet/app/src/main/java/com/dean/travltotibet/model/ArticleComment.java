package com.dean.travltotibet.model;

/**
 * Created by DeanGuo on 2/19/16.
 */
public class ArticleComment extends Comment {

    private String article_id;
    private String article_title;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }
}
