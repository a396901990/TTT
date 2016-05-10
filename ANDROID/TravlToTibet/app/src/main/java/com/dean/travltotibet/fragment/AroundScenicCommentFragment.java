package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.View;

import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.dialog.AroundScenicCommentDialog;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class AroundScenicCommentFragment extends BaseRatingCommentFragment {

    private AroundBaseActivity aroundBaseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aroundBaseActivity = (AroundBaseActivity) getActivity();
    }

    @Override
    public String getCommentType() {
        return Comment.SCENIC_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        return aroundBaseActivity.getTypeObjectId();
    }

    @Override
    public void initCommentAction() {
        aroundBaseActivity.getFloatingBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentAction();
            }
        });
    }

    public void commentAction() {
        BaseCommentDialog dialogFragment = new AroundScenicCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), AroundScenicCommentDialog.class.getName());
    }

    @Override
    public void onCommentSuccess(Comment comment) {
        onRefresh();
    }

    @Override
    public void onCommentFailed() {

    }
}