package com.dean.travltotibet.fragment;

import android.os.Bundle;

import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.activity.RoadInfoDetailActivity;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.base.BaseInsideCommentFragment;
import com.dean.travltotibet.base.NewBaseInsideCommentFragment;
import com.dean.travltotibet.model.BaseCommentBmobObject;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 4/9/16.
 */
public class RoadInfoCommentFragment extends NewBaseInsideCommentFragment {

    private RoadInfoDetailActivity roadInfoDetailActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        roadInfoDetailActivity = (RoadInfoDetailActivity) getActivity();
    }

    @Override
    public String getCommentType() {
        return Comment.ROAD_INFO_COMMENT;
    }



    @Override
    public BaseCommentBmobObject getCommentObject() {
        if (roadInfoDetailActivity == null) {
            return null;
        }
        return roadInfoDetailActivity.getRoadInfo();
    }
}
