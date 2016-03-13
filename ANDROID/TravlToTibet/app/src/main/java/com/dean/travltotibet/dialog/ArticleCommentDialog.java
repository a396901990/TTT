package com.dean.travltotibet.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.ArticleCommentActivity;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.model.ArticleComment;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.util.IntentExtra;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DeanGuo on 2/19/16.
 */
public class ArticleCommentDialog extends BaseCommentDialog {

    private Article mArticle;

    private ArticleCommentActivity articleActivity;

    private Comment replyComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        articleActivity = (ArticleCommentActivity) getActivity();
        mArticle = articleActivity.getArticle();

        if (getArguments() != null) {
            replyComment = (Comment) getArguments().getSerializable(IntentExtra.INTENT_COMMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (replyComment != null) {
            String hint = replyComment.getUser_name();
            setReplyHint("回复@"+hint);
        }
        return view;
    }

    @Override
    public void submitCommit() {
        ArticleComment articleComment = new ArticleComment();

        articleComment.setArticle_id(mArticle.getObjectId());

        articleComment.setArticle_title(mArticle.getTitle());

        // 评论
        articleComment.setComment(getComment());
        // 评分
        articleComment.setRating(getRatting());
        // user id
        articleComment.setUser_id(TTTApplication.getUserInfo().getUserId());
        // user name
        articleComment.setUser_name(TTTApplication.getUserInfo().getUserName());
        // pic url
        articleComment.setUser_icon(TTTApplication.getUserInfo().getUserIcon());


        articleComment.setLike(0);
        articleComment.setDislike(0);
        if (replyComment != null) {
            articleComment.setQuote_id(replyComment.getObjectId());
        } else {
            articleComment.setQuote_id("");
        }

        articleComment.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                getHandle().sendEmptyMessage(SUBMIT_SUCCESS);
            }

            @Override
            public void onFailure(int code, String msg) {
                getHandle().sendEmptyMessage(SUBMIT_FAILURE);
            }
        });
        super.submitCommit();
    }

    @Override
    public boolean isShowRattingBar() {
        return false;
    }
}