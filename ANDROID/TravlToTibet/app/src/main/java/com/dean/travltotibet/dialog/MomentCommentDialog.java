package com.dean.travltotibet.dialog;

import android.os.Bundle;

import com.dean.travltotibet.activity.MomentDetailActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.Moment;

/**
 * Created by DeanGuo on 5/23/16.
 */
public class MomentCommentDialog extends ReplyCommentDialog {

    private Moment moment;

    private MomentDetailActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MomentDetailActivity) getActivity();
        moment = mActivity.getMoment();
    }

    @Override
    public String getCommentType() {
        return Comment.MOMENT_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        return moment.getObjectId();
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