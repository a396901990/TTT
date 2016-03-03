package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.TeamRequestComment;
import com.dean.travltotibet.util.IntentExtra;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamRequestCommentDialog extends BaseCommentDialog {

    private TeamRequest mTeamRequest;

    private BaseCommentActivity mActivity;

    private Comment replyComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (BaseCommentActivity) getActivity();
        mTeamRequest = (TeamRequest) mActivity.getObj();

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
        TeamRequestComment teamRequestComment = new TeamRequestComment();

        teamRequestComment.setTeam_request_id(mTeamRequest.getObjectId());

        // 评论
        teamRequestComment.setComment(getComment());
        // 评分
        teamRequestComment.setRating(getRatting());
        // user id
        teamRequestComment.setUser_id(TTTApplication.getUserInfo().getUserId());
        // user name
        teamRequestComment.setUser_name(TTTApplication.getUserInfo().getUserName());
        // pic url
        teamRequestComment.setUser_icon(TTTApplication.getUserInfo().getUserIcon());

        teamRequestComment.setLike(0);
        teamRequestComment.setDislike(0);
        if (replyComment != null) {
            teamRequestComment.setQuote_id(replyComment.getObjectId());
            teamRequestComment.setQuote_text(replyComment.getComment());
            teamRequestComment.setQuote_user_name(replyComment.getUser_name());
        }

        teamRequestComment.save(getActivity(), new SaveListener() {
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