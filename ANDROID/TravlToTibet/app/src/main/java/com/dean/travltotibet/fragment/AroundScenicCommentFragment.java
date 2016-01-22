package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dean.greendao.Scenic;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.HotelComment;
import com.dean.travltotibet.model.PrepareFile;
import com.dean.travltotibet.model.ScenicComment;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class AroundScenicCommentFragment extends AroundCommentFragment {

    private AroundBaseActivity aroundActivity;

    private Scenic mScenic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aroundActivity = (AroundBaseActivity) getActivity();
        mScenic = (Scenic) aroundActivity.getAroundObj();
    }

    @Override
    public void goComment() {
        super.goComment();
        DialogFragment dialogFragment = new AroundScenicCommentDialog();
        Bundle bundle = new Bundle();
        bundle.putFloat(IntentExtra.INTENT_AROUND_RATING, getRating());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), AroundScenicCommentDialog.class.getName());
    }

    @Override
    public void getCommentData() {
        super.getCommentData();

        final ArrayList<Comment> comments = null;
        BmobQuery<ScenicComment> query = new BmobQuery<>();
        query.addWhereEqualTo("route", mScenic.getRoute());
        query.addWhereEqualTo("scenic_belong", mScenic.getScenic_f_belong());
        query.addWhereEqualTo("scenic_name", mScenic.getScenic_name());
        query.findObjects(getActivity(), new FindListener<ScenicComment>() {
            @Override
            public void onSuccess(List<ScenicComment> list) {
                for (ScenicComment scenicComment : list) {
                    Comment comment = scenicComment;
                    comments.add(comment);
                }
                setComments(comments);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

}