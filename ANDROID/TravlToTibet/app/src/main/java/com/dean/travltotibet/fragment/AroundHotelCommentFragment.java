package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.View;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.dialog.AroundHotelCommentDialog;
import com.dean.travltotibet.dialog.AroundScenicCommentDialog;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.HotelComment;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

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