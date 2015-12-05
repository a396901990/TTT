package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.GuideDetailAdapter;
import com.dean.travltotibet.ui.ExpandableTextView;
import com.dean.travltotibet.ui.InsideScrollAnimatedExpandableListView;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteGuideFragment extends BaseRouteFragment {

    private View rootView;

    private View contentView;

    private RouteActivity routeActivity;

    private GuideDetailAdapter mAdapter;

    private InsideScrollAnimatedExpandableListView mListView;

    private ArrayList<Geocode> mDataResult;

    private ExpandableTextView briefDescribe;

    private View shadeView;

    public static RouteGuideFragment newInstance() {
        RouteGuideFragment newFragment = new RouteGuideFragment();
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_content_frame, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
    }

    @Override
    protected void onLoadPrepared() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        contentView = inflater.inflate(R.layout.guide_route_fragment_view, null, false);

        mListView = (InsideScrollAnimatedExpandableListView) contentView.findViewById(R.id.guide_list);
        mAdapter = new GuideDetailAdapter(getActivity());
    }

    @Override
    protected void onLoading() {
        // 初始化数据adapter并赋值
        mDataResult = getListData(routeActivity.getPlanStart(), routeActivity.getPlanEnd());
        updateBriefView();
    }

    private void updateBriefView() {
        TextView start = (TextView) contentView.findViewById(R.id.guide_brief_start);
        TextView end = (TextView) contentView.findViewById(R.id.guide_brief_end);
        TextView date = (TextView) contentView.findViewById(R.id.guide_brief_plan);
        TextView distance = (TextView) contentView.findViewById(R.id.guide_brief_distance);
        RatingBar rank = (RatingBar) contentView.findViewById(R.id.guide_brief_rank);

        start.setText(routeActivity.getPlanStart());
        end.setText(routeActivity.getPlanEnd());

        String planDate = routeActivity.getPlanDate();
        String planDays = TTTApplication.getDbHelper().getPlanDays(routeActivity.getRoutePlanId());
        if (routeActivity.isRoute()) {
            date.setText(String.format(Constants.BRIEF_DAY_ROUTE, planDate, planDays));
        } else {
            date.setText(String.format(Constants.BRIEF_DAY, planDate, planDays));
        }

        distance.setText(routeActivity.getPlanDistance());
        rank.setNumStars(Integer.parseInt(routeActivity.getPlanRank()));

        initDescribeExpandableTextView();
    }

    @Override
    protected void onLoadFinished() {
        if (mDataResult != null) {
            mAdapter.setData(mDataResult);
            mListView.setAdapter(mAdapter);
            mListView.expandGroupWithAnimation(0);
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
        ((ViewGroup) rootView).addView(contentView);
    }

    @Override
    public void updateRoute() {
        updateTimelineView();
        updateBriefView();
    }

    private ArrayList<Geocode> getListData(String start, String end) {

        // 根据起点终点获取数据
        String routeName = routeActivity.getCurrentRoute().getRoute();
        boolean isForward = routeActivity.isForward();
        ArrayList<Geocode> geocodes = (ArrayList<Geocode>) TTTApplication.getDbHelper().getNonPathGeocodeListWithNameAndRoute(routeName, start, end, isForward);

        return geocodes;
    }

    /**
     * 初始化ExpandableTextView
     */
    private void initDescribeExpandableTextView() {
        briefDescribe = (ExpandableTextView) contentView.findViewById(R.id.guide_brief_describe);
        shadeView = contentView.findViewById(R.id.bottom_shade);

        // 设置文字
        briefDescribe.setText(routeActivity.getPlanDescribe());

        // 设置监听状态
        briefDescribe.setOnExpandListener(new ExpandableTextView.OnExpandListener() {
            // 展开时显示阴影
            @Override
            public void onExpand(ExpandableTextView parent) {
                shadeView.setVisibility(View.INVISIBLE);
            }
        }).setOnCollapseListener(new ExpandableTextView.OnCollapseListener() {
            // 收起时关闭阴影
            @Override
            public void onCollapse(ExpandableTextView parent) {
                shadeView.setVisibility(View.VISIBLE);
            }
        }).setOnClickListener(new View.OnClickListener() {
            @Override
            // 点击打开关闭阴影
            public void onClick(View v) {
                briefDescribe.toggle();
            }
        });

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
        mListView.expandGroupWithAnimation(0);
    }
}
