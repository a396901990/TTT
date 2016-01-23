package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.os.Bundle;

import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.HotelComment;
import com.dean.travltotibet.model.ScenicComment;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class AroundHotelCommentFragment extends AroundCommentFragment {

    private AroundBaseActivity aroundActivity;

    private Hotel mHotel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aroundActivity = (AroundBaseActivity) getActivity();
        mHotel = (Hotel) aroundActivity.getAroundObj();
    }

    @Override
    public void goComment() {
        super.goComment();
        DialogFragment dialogFragment = new AroundHotelCommentDialog();
        Bundle bundle = new Bundle();
        bundle.putFloat(IntentExtra.INTENT_AROUND_RATING, getRating());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), AroundHotelCommentDialog.class.getName());
    }

    @Override
    public void getCommentData() {
        super.getCommentData();

        final ArrayList<Comment> comments = new ArrayList<>();

        BmobQuery<HotelComment> query = new BmobQuery<>();
        query.addWhereEqualTo("hotel_belong", mHotel.getHotel_belong());
        query.addWhereEqualTo("hotel_name", mHotel.getHotel_name());
        query.findObjects(getActivity(), new FindListener<HotelComment>() {
            @Override
            public void onSuccess(List<HotelComment> list) {
                for (HotelComment hotelComment : list) {
                    Comment comment = hotelComment;
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