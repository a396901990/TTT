package com.dean.travltotibet.fragment;

import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.base.BaseInsideCommentFragment;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 4/9/16.
 */
public class RoadInfoCommentFragment extends BaseInsideCommentFragment {

    @Override
    public String getCommentType() {
        return Comment.ROAD_INFO_COMMENT;
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
