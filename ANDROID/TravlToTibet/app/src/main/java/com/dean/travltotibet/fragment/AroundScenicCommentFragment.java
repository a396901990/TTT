package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.View;

import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.base.NewBaseRatingCommentFragment;
import com.dean.travltotibet.dialog.AroundScenicCommentDialog;
import com.dean.travltotibet.dialog.BaseCommentDialog;
import com.dean.travltotibet.model.BaseCommentBmobObject;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.HotelInfo;
import com.dean.travltotibet.model.ScenicInfo;

import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class AroundScenicCommentFragment extends NewBaseRatingCommentFragment {

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
    public BaseCommentBmobObject getCommentObject() {
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setObjectId(aroundBaseActivity.getTypeObjectId());
        return scenicInfo;
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
        // 将评论添加到当前team request的关联中
        ScenicInfo scenicInfo = (ScenicInfo) getCommentObject();
        BmobRelation commentRelation = new BmobRelation();
        commentRelation.add(comment);
        scenicInfo.setReplyComments(commentRelation);
        scenicInfo.update(getActivity(), new UpdateListener() {
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