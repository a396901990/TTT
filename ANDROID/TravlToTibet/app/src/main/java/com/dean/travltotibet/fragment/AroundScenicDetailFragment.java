package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.model.ScenicInfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundScenicDetailFragment extends RefreshFragment {

    private View root;

    private ScenicInfo mScenicInfo;

    private View noResultView;

    private View contentView;

    private AroundBaseActivity aroundBaseActivity;

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_scenic_detail_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aroundBaseActivity = (AroundBaseActivity) getActivity();

        noResultView = root.findViewById(R.id.no_result_view);
        contentView = root.findViewById(R.id.content_view);

        initRefreshView();
        refresh();
    }

    private void initRefreshView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        // 设置下拉刷新
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.half_dark_gray));
        //mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public void getScenicInfo() {

        BmobQuery<ScenicInfo> query = new BmobQuery<ScenicInfo>();
        query.getObject(getActivity(), aroundBaseActivity.getTypeObjectId(), new GetListener<ScenicInfo>() {

            @Override
            public void onSuccess(ScenicInfo object) {
                mScenicInfo = object;
                toDo(LOADING_SUCCESS, 0);
            }

            @Override
            public void onFailure(int code, String arg0) {
                toDo(LOADING_ERROR, 0);
            }

        });
    }

    private void initContentView() {

        // 简介介绍
        View scenicDetailContent = root.findViewById(R.id.scenic_detail_content);
        TextView scenicDetail = (TextView) root.findViewById(R.id.scenic_detail);
        String detail = mScenicInfo.getScenicOverview();
        if (TextUtils.isEmpty(detail)) {
            scenicDetailContent.setVisibility(View.GONE);
        } else {
            scenicDetail.setText(detail);
        }

        // 收费
        View scenicFeeContent = root.findViewById(R.id.scenic_fee_content);
        TextView scenicFee = (TextView) root.findViewById(R.id.scenic_fee);
        String fee = mScenicInfo.getScenicDetail();
        if (TextUtils.isEmpty(fee)) {
            scenicFeeContent.setVisibility(View.GONE);
        } else {
            scenicFee.setText(fee);
        }

        // 时间
        View scenicOpeningContent = root.findViewById(R.id.scenic_opening_content);
        TextView scenicOpening = (TextView) root.findViewById(R.id.scenic_opening);
        String opening = mScenicInfo.getScenicOpentime();
        if (TextUtils.isEmpty(opening)) {
            scenicOpeningContent.setVisibility(View.GONE);
        } else {
            scenicOpening.setText(opening);
        }

        // 交通
        View scenicTrafficContent = root.findViewById(R.id.scenic_traffic_content);
        TextView scenicTraffic = (TextView) root.findViewById(R.id.scenic_traffic);
        String traffic = aroundBaseActivity.getDir() ? mScenicInfo.getScenicFTraffic() : mScenicInfo.getScenicRTraffic();
        if (TextUtils.isEmpty(traffic)) {
            scenicTrafficContent.setVisibility(View.GONE);
        } else {
            scenicTraffic.setText(traffic);
        }

        // 地址
        View scenicAddressContent = root.findViewById(R.id.scenic_address_content);
        TextView scenicAddress = (TextView) root.findViewById(R.id.scenic_address);
        String address = mScenicInfo.getScenicAddress();
        if (TextUtils.isEmpty(address)) {
            scenicAddressContent.setVisibility(View.GONE);
        } else {
            scenicAddress.setText(address);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void refresh() {
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        startUpdate();
        contentView.setVisibility(View.INVISIBLE);
        noResultView.setVisibility(View.GONE);
        toDo(ON_LOADING, 800);
    }

    @Override
    public void onLoading() {
        getScenicInfo();
    }

    @Override
    public void LoadingSuccess() {
        finishUpdate();
        contentView.setVisibility(View.VISIBLE);
        noResultView.setVisibility(View.GONE);
        initContentView();
    }

    @Override
    public void LoadingError() {
        finishUpdate();
        contentView.setVisibility(View.INVISIBLE);
        noResultView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingMore() {

    }

    @Override
    public void LoadingMoreSuccess() {

    }

    @Override
    public void LoadingMoreError() {

    }

    public void startUpdate() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void finishUpdate() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}