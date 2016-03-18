package com.dean.travltotibet.dialog;

import android.os.Bundle;

import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.Comment;

/**
 * Created by DeanGuo on 1/20/16.
 * 选择旅行类型
 */
public class AroundHotelCommentDialog extends ReplyCommentDialog {


    private AroundBaseActivity aroundActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aroundActivity = (AroundBaseActivity) getActivity();
    }

    @Override
    public String getCommentType() {
        return Comment.HOTEL_COMMENT;
    }

    @Override
    public String getCommentTypeObjectId() {
        return aroundActivity.getTypeObjectId();
    }

    @Override
    public String getCommentTypeDesc() {
        return aroundActivity.getHeaderName();
    }

    @Override
    public boolean isShowRattingBar() {
        return true;
    }
}