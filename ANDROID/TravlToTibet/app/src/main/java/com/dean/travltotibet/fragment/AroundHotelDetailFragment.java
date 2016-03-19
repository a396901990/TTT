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
import com.dean.travltotibet.model.HotelInfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundHotelDetailFragment extends RefreshFragment {

    private View root;

    private HotelInfo hotelInfo;

    private View noResultView;

    private View contentView;

    private AroundBaseActivity aroundBaseActivity;

    protected SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_hotel_detail_fragment_view, container, false);
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

    private void initContentView() {
        TextView hotelDetail = (TextView) root.findViewById(R.id.hotel_detail);
        TextView hotelTel = (TextView) root.findViewById(R.id.hotel_tel);
        TextView hotelAddress = (TextView) root.findViewById(R.id.hotel_address);

        View hotelDetailContent = root.findViewById(R.id.hotel_detail_content);
        View hotelTelContent = root.findViewById(R.id.hotel_tel_content);
        View hotelAddressContent = root.findViewById(R.id.hotel_address_content);

        // 细节
        String detail = hotelInfo.getHotelDetail();
        if (!TextUtils.isEmpty(detail)) {
            hotelDetailContent.setVisibility(View.VISIBLE);
            hotelDetail.setText(detail);
        } else {
            hotelDetailContent.setVisibility(View.GONE);
        }

        // 电话
        String tel = hotelInfo.getHotelTel();
        if (!TextUtils.isEmpty(tel)) {
            hotelTelContent.setVisibility(View.VISIBLE);
            hotelTel.setText(tel);
        } else {
            hotelTelContent.setVisibility(View.GONE);
        }

        // 地址
        String address = hotelInfo.getHotelAddress();
        if (!TextUtils.isEmpty(address)) {
            hotelAddressContent.setVisibility(View.VISIBLE);
            hotelAddress.setText(address);
        } else {
            hotelAddressContent.setVisibility(View.GONE);
        }
    }


    public void getHotelInfo() {

        BmobQuery<HotelInfo> query = new BmobQuery<HotelInfo>();
        query.getObject(getActivity(), aroundBaseActivity.getTypeObjectId(), new GetListener<HotelInfo>() {

            @Override
            public void onSuccess(HotelInfo object) {
                hotelInfo = object;
                toDo(LOADING_SUCCESS, 0);
            }

            @Override
            public void onFailure(int code, String arg0) {
                toDo(LOADING_ERROR, 0);
            }

        });
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
    public void onLoading()
    {
        getHotelInfo();
    }

    @Override
    public void LoadingSuccess() {
        finishUpdate();
        contentView.setVisibility(View.VISIBLE);
        noResultView.setVisibility(View.GONE);
        TextView noResultText = (TextView) root.findViewById(R.id.no_result_text);
        if (noResultText != null) {
            noResultText.setText(getString(R.string.no_result));
        }
        initContentView();
    }

    @Override
    public void LoadingError() {
        finishUpdate();
        contentView.setVisibility(View.INVISIBLE);
        noResultView.setVisibility(View.VISIBLE);
        TextView noResultText = (TextView) root.findViewById(R.id.no_result_text);
        if (noResultText != null) {
            noResultText.setText(getString(R.string.no_network_result));
        }
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