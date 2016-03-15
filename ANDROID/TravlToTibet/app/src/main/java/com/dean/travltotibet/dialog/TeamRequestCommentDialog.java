package com.dean.travltotibet.dialog;

import android.os.Bundle;

import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.TeamRequest;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamRequestCommentDialog extends ReplyCommentDialog {

    private TeamRequest mTeamRequest;

    private TeamShowRequestDetailActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (TeamShowRequestDetailActivity) getActivity();
        mTeamRequest = mActivity.getTeamRequest();
    }

    @Override
    public String getCommentType() {
        return Comment.TEAM_REQUEST_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        return mTeamRequest.getObjectId();
    }

    @Override
    public String getCommentTypeDesc() {
        return "";
    }

    @Override
    public boolean isShowRattingBar() {
        return false;
    }
}