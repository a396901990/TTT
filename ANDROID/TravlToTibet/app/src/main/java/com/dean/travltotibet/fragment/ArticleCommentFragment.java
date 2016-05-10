package com.dean.travltotibet.fragment;

import android.os.Bundle;

import com.dean.travltotibet.activity.ArticleActivity;
import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.base.BaseInsideCommentFragment;
import com.dean.travltotibet.base.NewBaseInsideCommentFragment;
import com.dean.travltotibet.model.BaseCommentBmobObject;
import com.dean.travltotibet.model.Comment;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 2/18/16.
 */
public class ArticleCommentFragment extends NewBaseInsideCommentFragment {

    ArticleActivity articleActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        articleActivity = (ArticleActivity) getActivity();
    }

    @Override
    public String getCommentType() {
        return Comment.ARTICLE_COMMENT;
    }

    @Override
    public BaseCommentBmobObject getCommentObject() {
        if (articleActivity == null) {
            return null;
        }
        return articleActivity.getArticle();
    }

}
