package com.dean.travltotibet.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.HotelComment;

import cn.bmob.v3.listener.SaveListener;

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