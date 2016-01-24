package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
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
public class AroundHotelDetailFragment extends AroundBaseFragment {

    private View root;

    private Hotel mHotel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_hotel_detail_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHotel = (Hotel) getAroundActivity().getAroundObj();
        initContentView();
    }

    private void initContentView() {
        TextView hotelDetail = (TextView) root.findViewById(R.id.hotel_detail);
        TextView hotelTel = (TextView) root.findViewById(R.id.hotel_tel);
        TextView hotelAddress = (TextView) root.findViewById(R.id.hotel_address);

        View hotelDetailContent = root.findViewById(R.id.hotel_detail_content);
        View hotelTelContent = root.findViewById(R.id.hotel_tel_content);
        View hotelAddressContent = root.findViewById(R.id.hotel_address_content);

        // 细节
        String detail = mHotel.getHotel_detail();
        if (!TextUtils.isEmpty(detail)) {
            hotelDetailContent.setVisibility(View.VISIBLE);
            hotelDetail.setText(detail);
        } else {
            hotelDetailContent.setVisibility(View.GONE);
        }

        // 电话
        String tel = mHotel.getHotel_tel();
        if (!TextUtils.isEmpty(tel)) {
            hotelTelContent.setVisibility(View.VISIBLE);
            hotelTel.setText(tel);
        } else {
            hotelTelContent.setVisibility(View.GONE);
        }

        // 地址
        String address = mHotel.getHotel_address();
        if (!TextUtils.isEmpty(address)) {
            hotelAddressContent.setVisibility(View.VISIBLE);
            hotelAddress.setText(address);
        } else {
            hotelAddressContent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTabChanged() {
        getAroundActivity().getFloatingBtn().setVisibility(View.GONE);
    }
}