package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.activity.AroundScenicActivity;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundHotelDetailFragment extends Fragment {

    private View root;

    private Hotel mHotel;

    private AroundBaseActivity aroundActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_hotel_detail_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aroundActivity = (AroundBaseActivity) getActivity();
        mHotel = (Hotel) aroundActivity.getAroundObj();
        initContentView();
    }

    private void initContentView() {

        // 景点详细介绍
        TextView scenicDetail = (TextView) root.findViewById(R.id.scenic_detail);
        scenicDetail.setText(mHotel.getHotel_name());
    }
}