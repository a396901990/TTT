package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.HotelComment;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DeanGuo on 1/20/16.
 * 选择旅行类型
 */
public class AroundHotelCommentDialog extends BaseCommentDialog {

    private Hotel mHotel;

    private AroundBaseActivity aroundActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aroundActivity = (AroundBaseActivity) getActivity();
        mHotel = (Hotel) aroundActivity.getAroundObj();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void submitCommit() {
        HotelComment hotelComment = new HotelComment();
        // route name
        hotelComment.setRoute(mHotel.getRoute());
        // hotel name
        hotelComment.setHotel_name(mHotel.getHotel_name());
        // hotel belong
        hotelComment.setHotel_belong(mHotel.getHotel_belong());
        // 评论
        hotelComment.setComment(getComment());
        // 评分
        hotelComment.setRating(getRatting());
        // user id
        hotelComment.setUser_id(TTTApplication.getUserInfo().getUserId());
        // user name
        hotelComment.setUser_name(TTTApplication.getUserInfo().getUserName());
        // pic url
        hotelComment.setUser_icon(TTTApplication.getUserInfo().getUserIcon());

        hotelComment.setLike(0);
        hotelComment.setDislike(0);

        hotelComment.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                getHandle().sendEmptyMessage(SUBMIT_SUCCESS);
            }

            @Override
            public void onFailure(int code, String msg) {
                getHandle().sendEmptyMessage(SUBMIT_FAILURE);
            }
        });
        super.submitCommit();
    }

    @Override
    public boolean isShowRattingBar() {
        return true;
    }
}