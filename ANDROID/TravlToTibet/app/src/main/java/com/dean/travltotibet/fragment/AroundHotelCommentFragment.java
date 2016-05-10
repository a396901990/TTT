package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.View;

import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.base.NewBaseRatingCommentFragment;
import com.dean.travltotibet.dialog.AroundHotelCommentDialog;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.model.BaseCommentBmobObject;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.HotelInfo;

import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class AroundHotelCommentFragment extends NewBaseRatingCommentFragment {

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
    public BaseCommentBmobObject getCommentObject() {
        HotelInfo hotelInfo = new HotelInfo();
        hotelInfo.setObjectId(aroundBaseActivity.getTypeObjectId());
        return hotelInfo;
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
    public void onCommentSuccess(Comment comment) {
        // 将评论添加到当前team request的关联中
        HotelInfo hotelInfo = (HotelInfo) getCommentObject();
        BmobRelation commentRelation = new BmobRelation();
        commentRelation.add(comment);
        hotelInfo.setReplyComments(commentRelation);
        hotelInfo.update(getActivity(), new UpdateListener() {
            @Override
            public void onSuccess() {
                onRefresh();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    @Override
    public void onCommentFailed() {

    }
}