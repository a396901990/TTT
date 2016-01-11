package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.RouteGuideDetailAdapter;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/8/15.
 */
public class GuideLineFragment extends BaseGuideFragment {

    private View root;

    private RouteActivity routeActivity;

    private RouteGuideDetailAdapter mAdapter;

    private ListView mListView;

    private ArrayList<Geocode> mDataResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_line_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
        initView();
    }

    private void initView() {
        mListView = (ListView) root.findViewById(R.id.detail_list);
        mAdapter = new RouteGuideDetailAdapter(getActivity());
        // 设置正反
        mAdapter.setIsForward(routeActivity.isForward());
        // 初始化数据adapter并赋值
        mDataResult = getListData(routeActivity.getCurrentStart(), routeActivity.getCurrentEnd());

        if (mDataResult != null) {
            mAdapter.setData(mDataResult);
            mListView.setAdapter(mAdapter);
            mAdapter.setExpandableListener(new RouteGuideDetailAdapter.ExpandableListener() {
                @Override
                public void onExpand() {
                }

                @Override
                public void onCollapse() {
                }
            });
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
    }

    @Override
    public void update() {
        updateTimelineView();
    }
}
