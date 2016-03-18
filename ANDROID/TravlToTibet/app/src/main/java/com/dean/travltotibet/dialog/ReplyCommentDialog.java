package com.dean.travltotibet.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.util.IntentExtra;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DeanGuo on 3/3/16.
 */
public abstract class ReplyCommentDialog extends BaseCommentDialog {

    private Comment replyComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    // 获取评论类型
    public abstract String getCommentType();

    public abstract String getCommentTypeObjectId();

    public abstract String getCommentTypeDesc();

    @Override
    public void submitCommit() {
        super.submitCommit();
        if (!TTTApplication.hasLoggedIn()) {
            return;
        }
        Comment comment = new Comment();

        // type
        comment.setType(getCommentType());

        // type desc
        comment.setType_desc(getCommentTypeDesc());

        // type object id
        comment.setType_object_id(getCommentTypeObjectId());

        // 评论
        comment.setComment(getComment());
        // 评分
        comment.setRating(getRatting());
        // user id
        comment.setUser_id(TTTApplication.getUserInfo().getUserId());
        // user name
        comment.setUser_name(TTTApplication.getUserInfo().getUserName());
        // pic url
        comment.setUser_icon(TTTApplication.getUserInfo().getUserIcon());

        comment.setLike(0);
        comment.setDislike(0);
        if (replyComment != null) {
            comment.setQuote_id(replyComment.getObjectId());
            comment.setQuote_text(replyComment.getComment());
            comment.setQuote_user_name(replyComment.getUser_name());
        }

        comment.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                getHandle().sendEmptyMessage(SUBMIT_SUCCESS);
            }

            @Override
            public void onFailure(int code, String msg) {
                getHandle().sendEmptyMessage(SUBMIT_FAILURE);
            }
        });
    }

    @Override
    public boolean isShowRattingBar() {
        return false;
    }
}