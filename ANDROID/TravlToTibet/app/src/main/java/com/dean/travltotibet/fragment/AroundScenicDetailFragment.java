package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundScenicDetailFragment extends AroundBaseFragment {

    private View root;

    private Scenic mScenic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_scenic_detail_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mScenic = (Scenic) getAroundActivity().getAroundObj();
        initContentView();
    }

    private void initContentView() {

        // 简介介绍
        View scenicDetailContent = root.findViewById(R.id.scenic_detail_content);
        TextView scenicDetail = (TextView) root.findViewById(R.id.scenic_detail);
        String detail = mScenic.getScenic_overview();
        if (TextUtils.isEmpty(detail)) {
            scenicDetailContent.setVisibility(View.GONE);
        } else {
            scenicDetail.setText(detail);
        }

        // 收费
        View scenicFeeContent = root.findViewById(R.id.scenic_fee_content);
        TextView scenicFee = (TextView) root.findViewById(R.id.scenic_fee);
        String fee = mScenic.getScenic_detail();
        if (TextUtils.isEmpty(fee)) {
            scenicFeeContent.setVisibility(View.GONE);
        } else {
            scenicFee.setText(fee);
        }

        // 时间
        View scenicOpeningContent = root.findViewById(R.id.scenic_opening_content);
        TextView scenicOpening = (TextView) root.findViewById(R.id.scenic_opening);
        String opening = mScenic.getScenic_opentime();
        if (TextUtils.isEmpty(opening)) {
            scenicOpeningContent.setVisibility(View.GONE);
        } else {
            scenicOpening.setText(opening);
        }

        // 交通
        View scenicTrafficContent = root.findViewById(R.id.scenic_traffic_content);
        TextView scenicTraffic = (TextView) root.findViewById(R.id.scenic_traffic);
        String traffic = getAroundActivity().getDir() ? mScenic.getScenic_f_traffic() : mScenic.getScenic_r_traffic();
        if (TextUtils.isEmpty(traffic)) {
            scenicTrafficContent.setVisibility(View.GONE);
        } else {
            scenicTraffic.setText(traffic);
        }

        // 地址
        View scenicAddressContent = root.findViewById(R.id.scenic_address_content);
        TextView scenicAddress = (TextView) root.findViewById(R.id.scenic_address);
        String address = mScenic.getScenic_address();
        if (TextUtils.isEmpty(address)) {
            scenicAddressContent.setVisibility(View.GONE);
        } else {
            scenicAddress.setText(address);
        }
    }

    @Override
    public void onTabChanged() {
        getAroundActivity().getFloatingBtn().setVisibility(View.GONE);
    }
}