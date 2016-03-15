package com.dean.travltotibet.dialog;

import android.os.Bundle;

import com.dean.travltotibet.activity.ArticleCommentActivity;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 2/19/16.
 */
public class ArticleCommentDialog extends ReplyCommentDialog {

    private Article mArticle;

    private ArticleCommentActivity articleActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleActivity = (ArticleCommentActivity) getActivity();
        mArticle = articleActivity.getArticle();
    }

    @Override
    public String getCommentType() {
        return Comment.ARTICLE_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        return mArticle.getObjectId();
    }

    @Override
    public String getCommentTypeDesc() {
        return mArticle.getTitle();
    }

    @Override
    public boolean isShowRattingBar() {
        return false;
    }
}