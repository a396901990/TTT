package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dean.greendao.Geocode;
import com.dean.greendao.Hotel;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.GuideHotelAdapter;
import com.dean.travltotibet.ui.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 12/8/15.
 */
public class GuideHotelFragment extends BaseGuideFragment {

    private View root;

    private RouteActivity routeActivity;

    private GuideHotelAdapter mAdapter;

    private AnimatedExpandableListView mListView;

    private ArrayList<GuideHotelAdapter.PlaceHotel> mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_hotel_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
        initView();
    }

    private void initView() {
        mListView = (AnimatedExpandableListView) root.findViewById(R.id.list_view);
        mAdapter = new GuideHotelAdapter(getActivity());

        // 初始化数据adapter并赋值
        mData = getListData();

        if (mData != null) {
            mAdapter.setData(mData);
            mListView.setAdapter(mAdapter);
            mListView.expandGroup(0);
            mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    if (mListView.isGroupExpanded(groupPosition)) {
                        mListView.collapseGroupWithAnimation(groupPosition);
                    } else {
                        mListView.expandGroupWithAnimation(groupPosition);
                    }
                    return true;
                }

            });
        }
    }

    private ArrayList<GuideHotelAdapter.PlaceHotel> getListData() {

        ArrayList<GuideHotelAdapter.PlaceHotel> placeHotels = new ArrayList<>();

        // 根据起点终点获取数据
        String routeName = routeActivity.getCurrentRoute().getRoute();

        String planStart = routeActivity.getPlanStart();
        String planEnd = routeActivity.getPlanEnd();

        // 获取反向地点数据列表
        List<Geocode> places = TTTApplication.getDbHelper().getNonPathGeocodeListWithNameAndRoute(routeName, planStart, planEnd, false);
        for (Geocode place : places) {
            // 地点名称（标题）
            String placeName = place.getName();
            // 该地点下得所有旅店
            ArrayList<Hotel> hotels = (ArrayList<Hotel>) TTTApplication.getDbHelper().getHotelList(routeName, placeName);

            // 旅店不为零则加入数据中
            if (hotels.size()!=0) {
                GuideHotelAdapter.PlaceHotel placeHotel = new GuideHotelAdapter.PlaceHotel(placeName, hotels);
                placeHotels.add(placeHotel);
            }
        }

        return placeHotels;
    }

    private void updateView() {
        mData = getListData();
        if (mData != null) {
            mAdapter.setData(mData);
        }
        // 遍历所有group,将所有项设置成默认关闭
        int groupCount = mListView.getCount();
        for (int i = 0; i < groupCount; i++) {
            mListView.collapseGroup(i);
        }
        // 打开第一个
        mListView.expandGroup(0);
    }

    @Override
    public void update() {
        updateView();
    }
}
