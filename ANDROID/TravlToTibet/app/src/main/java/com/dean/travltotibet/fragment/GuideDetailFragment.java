package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.GuideDetailAdapter;
import com.dean.travltotibet.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 12/8/15.
 */
public class GuideDetailFragment extends BaseGuideFragment {

    private View root;

    private RouteActivity routeActivity;

    private GuideDetailAdapter mAdapter;

    private ExpandableListView mListView;

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
        mListView = (ExpandableListView) root.findViewById(R.id.detail_list);
        mAdapter = new GuideDetailAdapter(getActivity());
        // 设置正反
        mAdapter.setIsForward(routeActivity.isForward());
        // 初始化数据adapter并赋值
        mDataResult = getListData(routeActivity.getCurrentStart(), routeActivity.getCurrentEnd());

        if (mDataResult != null) {
            mAdapter.setData(mDataResult);
            mListView.setAdapter(mAdapter);
            // 当group被点击时触发
            mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    if (mListView.isGroupExpanded(groupPosition)) {
                        mListView.collapseGroup(groupPosition);
                    } else {
                        mListView.expandGroup(groupPosition);
                    }
                    return true;
                }

            });
            // 默认打开第一个
            mListView.expandGroup(0);
        }
    }

    private ArrayList<Geocode> getListData(String start, String end) {

        // 根据起点终点获取数据
        String routeName = routeActivity.getCurrentRoute().getRoute();
        boolean isForward = routeActivity.isForward();
        ArrayList<Geocode> geocodes = (ArrayList<Geocode>) TTTApplication.getDbHelper().getGeocodeListWithNameAndRoute(routeName, start, end, isForward);

        return geocodes;
    }

    private void updateTimelineView() {
        String start = routeActivity.getCurrentStart();
        String end = routeActivity.getCurrentEnd();

        mAdapter.setData(getListData(start, end));
        // 遍历所有group,将所有项设置成默认关闭
        int groupCount = mAdapter.getGroupCount();
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
