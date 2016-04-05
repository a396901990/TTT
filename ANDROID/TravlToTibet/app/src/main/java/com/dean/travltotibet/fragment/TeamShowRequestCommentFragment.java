package com.dean.travltotibet.fragment;

import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.base.BaseInsideCommentFragment;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamShowRequestCommentFragment extends BaseInsideCommentFragment {

    @Override
    public String getCommentType() {
        return Comment.TEAM_REQUEST_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        if (getActivity() == null) {
            return null;
        }

        if (((BaseCommentActivity) getActivity()).getObj() == null) {
            return null;
        }
        return ((BaseCommentActivity) getActivity()).getObj().getObjectId();
    }
}
