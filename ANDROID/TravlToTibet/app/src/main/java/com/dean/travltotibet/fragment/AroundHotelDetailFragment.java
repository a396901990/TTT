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
import com.dean.travltotibet.model.HotelInfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundHotelDetailFragment extends BaseRefreshFragment {

    private View root;

    private HotelInfo hotelInfo;

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

        initLoadingBackground(root);
        initRefreshView();
        onRefresh();
    }


    private void initRefreshView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        setSwipeRefreshLayout(mSwipeRefreshLayout);
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
    public void onRefresh() {
        super.onRefresh();
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        getLoadingBackgroundManager().resetLoadingView();
        hotelInfo = null;
        toDo(ON_LOADING, 800);
    }

    @Override
    public void onLoading()
    {
        super.onLoading();
        getHotelInfo();
    }

    @Override
    public void LoadingSuccess() {
        if (hotelInfo == null) {
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