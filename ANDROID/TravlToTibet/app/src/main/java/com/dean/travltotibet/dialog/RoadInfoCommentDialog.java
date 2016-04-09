package com.dean.travltotibet.dialog;

import android.os.Bundle;

import com.dean.travltotibet.activity.RoadInfoDetailActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.RoadInfo;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class RoadInfoCommentDialog extends ReplyCommentDialog {

    private RoadInfo roadInfo;

    private RoadInfoDetailActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (RoadInfoDetailActivity) getActivity();
        roadInfo = mActivity.getRoadInfo();
    }

    @Override
    public String getCommentType() {
        return Comment.ROAD_INFO_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        return roadInfo.getObjectId();
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