package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.greendao.Scenic;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.ScenicComment;
import com.dean.travltotibet.util.SystemUtil;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DeanGuo on 1/20/16.
 * 选择旅行类型
 */
public class AroundScenicCommentDialog extends BaseCommentDialog {

    private Scenic mScenic;

    private AroundBaseActivity aroundActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aroundActivity = (AroundBaseActivity) getActivity();
        mScenic = (Scenic) aroundActivity.getAroundObj();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void submitCommit() {
        // scenic
        ScenicComment scenicComment = new ScenicComment();
        // route name
        scenicComment.setRoute(mScenic.getRoute());
        // scenic name
        scenicComment.setScenic_name(mScenic.getScenic_name());
        // scenic belong (默认f_belong)
        scenicComment.setScenic_belong(mScenic.getScenic_f_belong());

        // 评论
        scenicComment.setComment(getComment());
        // 评分
        scenicComment.setRating(getRatting());
        // user name
        scenicComment.setUser_name(getUserName());
        // current time
        scenicComment.setComment_time(SystemUtil.getCurrentTime());

        scenicComment.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                getHandle().sendEmptyMessage(SUBMIT_SUCCESS);
            }

            @Override
            public void onFailure(int code, String msg) {
                getHandle().sendEmptyMessage(SUBMIT_FAILURE);
            }
        });
    }
}