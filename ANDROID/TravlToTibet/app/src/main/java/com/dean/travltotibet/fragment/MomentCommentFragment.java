package com.dean.travltotibet.fragment;

import android.os.Bundle;

import com.dean.travltotibet.activity.MomentDetailActivity;
import com.dean.travltotibet.base.NewBaseInsideCommentFragment;
import com.dean.travltotibet.model.BaseCommentBmobObject;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 5/23/16.
 */
public class MomentCommentFragment extends NewBaseInsideCommentFragment {

    MomentDetailActivity momentDetailActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        momentDetailActivity = (MomentDetailActivity) getActivity();
    }

    @Override
    public String getCommentType() {
        return Comment.MOMENT_COMMENT;
    }

    @Override
    public BaseCommentBmobObject getCommentObject() {
        if (momentDetailActivity == null) {
            return null;
        }
        return momentDetailActivity.getMoment();
    }
}