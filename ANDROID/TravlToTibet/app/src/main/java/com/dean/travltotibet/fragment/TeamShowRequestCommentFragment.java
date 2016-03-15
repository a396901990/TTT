package com.dean.travltotibet.fragment;

import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.TeamRequest;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamShowRequestCommentFragment extends BaseCommentFragment {

    @Override
    public String getCommentType() {
        return Comment.TEAM_REQUEST_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        return ((BaseCommentActivity) getActivity()).getObj().getObjectId();
    }
}
