package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.View;

import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.dialog.AroundHotelCommentDialog;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class AroundHotelCommentFragment extends BaseRatingCommentFragment {

    private AroundBaseActivity aroundBaseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aroundBaseActivity = (AroundBaseActivity) getActivity();
    }
    @Override
    public String getCommentType() {
        return Comment.HOTEL_COMMENT;
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
        BaseCommentDialog dialogFragment = new AroundHotelCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), AroundHotelCommentDialog.class.getName());
    }

    @Override
    public void onCommentSuccess() {
        refresh();
    }

    @Override
    public void onCommentFailed() {

    }
}