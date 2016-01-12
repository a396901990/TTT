package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

    private View root, headerView, footerView;

    private RouteActivity routeActivity;

    private ArrayList<Geocode> mDataResult;

    private ListView mListView;

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
        setHeaderView(mListView);
        setFooterView(mListView);

        RouteGuideDetailAdapter mAdapter = new RouteGuideDetailAdapter(getActivity());
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

    private void setFooterView(ListView listView) {

        if (listView.getFooterViewsCount() == 0) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            footerView = inflater.inflate(R.layout.guide_line_footer_view, null);
            listView.addFooterView(footerView);
        }

    }

    private void setHeaderView (ListView listView) {
        if (listView.getHeaderViewsCount() == 0) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            headerView = inflater.inflate(R.layout.guide_line_header_view, null);
            listView.addHeaderView(headerView);
        }

        // 描述
        if (headerView != null) {
            TextView overviewDetail = (TextView) headerView.findViewById(R.id.overview_detail);
            overviewDetail.setText(routeActivity.getCurrentPlan().getDescribe());
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
        initView();
    }

    @Override
    public void update() {
        updateTimelineView();
    }
}
