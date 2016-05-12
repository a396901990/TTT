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
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.model.ScenicInfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundScenicDetailFragment extends BaseRefreshFragment {

    private View root;

    private ScenicInfo mScenicInfo;

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

        initLoadingBackground(root);
        initRefreshView();
        onRefresh();
    }

    private void initRefreshView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        // 设置下拉刷新
        setSwipeRefreshLayout(mSwipeRefreshLayout);
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

        if (mScenicInfo == null) {
            return;
        }
        // 简介介绍
        View scenicDetailContent = root.findViewById(R.id.scenic_detail_content);
        TextView scenicDetail = (TextView) root.findViewById(R.id.scenic_detail);
        String detail = mScenicInfo.getScenicOverview();
        if (TextUtils.isEmpty(detail)) {
            scenicDetailContent.setVisibility(View.GONE);
        } else {
            scenicDetailContent.setVisibility(View.VISIBLE);
            scenicDetail.setText(detail);
        }

        // 收费
        View scenicFeeContent = root.findViewById(R.id.scenic_fee_content);
        TextView scenicFee = (TextView) root.findViewById(R.id.scenic_fee);
        String fee = mScenicInfo.getScenicDetail();
        if (TextUtils.isEmpty(fee)) {
            scenicFeeContent.setVisibility(View.GONE);
        } else {
            scenicFeeContent.setVisibility(View.VISIBLE);
            scenicFee.setText(fee);
        }

        // 时间
        View scenicOpeningContent = root.findViewById(R.id.scenic_opening_content);
        TextView scenicOpening = (TextView) root.findViewById(R.id.scenic_opening);
        String opening = mScenicInfo.getScenicOpentime();
        if (TextUtils.isEmpty(opening)) {
            scenicOpeningContent.setVisibility(View.GONE);
        } else {
            scenicOpeningContent.setVisibility(View.VISIBLE);
            scenicOpening.setText(opening);
        }

        // 交通
        View scenicTrafficContent = root.findViewById(R.id.scenic_traffic_content);
        TextView scenicTraffic = (TextView) root.findViewById(R.id.scenic_traffic);
        String traffic = aroundBaseActivity.getDir() ? mScenicInfo.getScenicFTraffic() : mScenicInfo.getScenicRTraffic();
        if (TextUtils.isEmpty(traffic)) {
            scenicTrafficContent.setVisibility(View.GONE);
        } else {
            scenicTrafficContent.setVisibility(View.VISIBLE);
            scenicTraffic.setText(traffic);
        }

        // 地址
        View scenicAddressContent = root.findViewById(R.id.scenic_address_content);
        TextView scenicAddress = (TextView) root.findViewById(R.id.scenic_address);
        String address = mScenicInfo.getScenicAddress();
        if (TextUtils.isEmpty(address)) {
            scenicAddressContent.setVisibility(View.GONE);
        } else {
            scenicAddressContent.setVisibility(View.VISIBLE);
            scenicAddress.setText(address);
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        getLoadingBackgroundManager().resetLoadingView();
        mScenicInfo = null;
        toDo(ON_LOADING, 800);
    }

    @Override
    public void onLoading() {
        super.onLoading();
        getScenicInfo();
    }


    @Override
    public void LoadingSuccess() {
        if (mScenicInfo == null) {
            getLoadingBackgroundManager().loadingFaild(getString(R.string.no_result), null);
        } else {
            getLoadingBackgroundManager().loadingSuccess();
            initContentView();
        }
        finishRefresh();
    }

    @Override
    public void LoadingError() {
        super.LoadingError();
        if (mScenicInfo == null) {
            mScenicInfo = new ScenicInfo();
        }
        initContentView();
        getLoadingBackgroundManager().loadingFaild(getString(R.string.network_no_result), new LoadingBackgroundManager.LoadingRetryCallBack() {
            @Override
            public void retry() {
                onRefresh();
            }
        });
        finishRefresh();
    }
}