package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.GuideDetailAdapter;
import com.dean.travltotibet.ui.AnimatedExpandableListView;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/8/15.
 */
public class GuideHotelFragment extends BaseGuideFragment {

    private View root;

    private RouteActivity routeActivity;

    private GuideDetailAdapter mAdapter;

    private AnimatedExpandableListView mListView;

    private ArrayList<Geocode> mDataResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_detail_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
        initView();
    }

    private void initView() {
        mListView = (AnimatedExpandableListView) root.findViewById(R.id.detail_list);
        mAdapter = new GuideDetailAdapter(getActivity());

        // 初始化数据adapter并赋值
        mDataResult = getListData(routeActivity.getPlanStart(), routeActivity.getPlanEnd());

        if (mDataResult != null) {
            mAdapter.setData(mDataResult);
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

    private ArrayList<Geocode> getListData(String start, String end) {

        // 根据起点终点获取数据
        String routeName = routeActivity.getCurrentRoute().getRoute();
        boolean isForward = routeActivity.isForward();
        ArrayList<Geocode> geocodes = (ArrayList<Geocode>) TTTApplication.getDbHelper().getNonPathGeocodeListWithNameAndRoute(routeName, start, end, isForward);

        return geocodes;
    }

    private void updateTimelineView() {
        String start = routeActivity.getPlanStart();
        String end = routeActivity.getPlanEnd();

        mAdapter.setData(getListData(start, end));
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
        updateTimelineView();
    }
}
