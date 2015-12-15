package com.dean.travltotibet.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dean.greendao.Hotel;
import com.dean.greendao.RecentRoute;
import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.PrepareRoutePlanListAdapter;
import com.dean.travltotibet.ui.CustomDialog;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/23/15.
 */
public class HotelDetailDialog extends DialogFragment {

    private View contentLayout;
    private Hotel mHotel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.hotel_detail_dialog_fragment, null);

        if (getArguments() != null) {
            mHotel = (Hotel) getArguments().getSerializable(Constants.INTENT_HOTEL);
        }

        initContentView();

        // 创建对话框，设置标题，布局，关闭响应
        CustomDialog dialog = new CustomDialog(getActivity());
        dialog.setTitle(mHotel.getHotel_name());
        dialog.setCustomContentView(contentLayout);
        return dialog;
    }

    private void initContentView() {
        TextView hotelDetail = (TextView) contentLayout.findViewById(R.id.hotel_detail);
        TextView hotelTel = (TextView) contentLayout.findViewById(R.id.hotel_tel);
        TextView hotelAddress = (TextView) contentLayout.findViewById(R.id.hotel_address);

        View hotelDetailContent = contentLayout.findViewById(R.id.hotel_detail_content);
        View hotelTelContent = contentLayout.findViewById(R.id.hotel_tel_content);
        View hotelAddressContent = contentLayout.findViewById(R.id.hotel_address_content);

        // 细节
        String detail = mHotel.getHotel_detail().replace("#", "\n");
        if (!TextUtils.isEmpty(detail)) {
            hotelDetail.setText(detail);
        } else {
            hotelDetailContent.setVisibility(View.GONE);
        }

        // 电话
        String tel = mHotel.getHotel_tel().replace("#", "\n");
        if (!TextUtils.isEmpty(tel)) {
            hotelTel.setText(tel);
        } else {
            hotelTelContent.setVisibility(View.GONE);
        }

        // 地址
        String address = mHotel.getHotel_address();
        if (!TextUtils.isEmpty(address)) {
            hotelAddress.setText(address);
        } else {
            hotelAddressContent.setVisibility(View.GONE);
        }
    }

}
