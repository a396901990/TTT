package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.GuidelineExpandAdapter;
import com.dean.travltotibet.adapter.GuidelineExpandAdapter.ChildGuidelineEntity;
import com.dean.travltotibet.adapter.GuidelineExpandAdapter.GroupGuidelineEntity;
import com.dean.travltotibet.ui.ExpandableTextView;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteGuideFragment extends BaseRouteFragment {

    private View rootView;

    private View contentView;

    private RouteActivity routeActivity;

    private GuidelineExpandAdapter timelineAdapter;

    private ExpandableListView timelineList;

    private List<GroupGuidelineEntity> dataList;

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

        timelineList = (ExpandableListView) contentView.findViewById(R.id.timeline_list);
        timelineAdapter = new GuidelineExpandAdapter(getActivity());
    }

    @Override
    protected void onLoading() {
        // 初始化数据adapter并赋值
        dataList = getListData(routeActivity.getPlanStart(), routeActivity.getPlanEnd());
        initExpandableTextView();
    }

    @Override
    protected void onLoadFinished() {
        timelineAdapter.setData(dataList);
        timelineList.setAdapter(timelineAdapter);
        timelineList.expandGroup(0);

        ((ViewGroup) rootView).addView(contentView);
    }

    @Override
    public void updateRoute() {
        updateTimelineView();
    }
    private List<GroupGuidelineEntity> getListData(String start, String end) {

        // 根据起点终点获取数据
        String routeName = routeActivity.getCurrentRoute().getRoute();
        boolean isForward = routeActivity.isForward();
        List<Geocode> geocodes = TTTApplication.getDbHelper().getNonPathGeocodeListWithNameAndRoute(routeName, start, end, isForward);

        // groupList存放所有数据
        List<GroupGuidelineEntity> groupList = new ArrayList<GroupGuidelineEntity>();

        for (Geocode geocode : geocodes) {
            // title模型对象
            GroupGuidelineEntity groupStatusEntity = new GroupGuidelineEntity();
            // 为title设置值
            groupStatusEntity.setTitleName(geocode.getName());

            List<ChildGuidelineEntity> childList = new ArrayList<ChildGuidelineEntity>();
            // child子view的模型
            ChildGuidelineEntity childStatusEntity = new ChildGuidelineEntity();

            // 为子视图设置高度
            String height = StringUtil.formatDoubleToInteger(geocode.getElevation());
            height = String.format(Constants.GUIDE_OVERALL_HEIGHT_FORMAT, height);
            childStatusEntity.setTitleHeight(height);

            // 为子视图设置位置
            String road = geocode.getRoad();
            if (!TextUtils.isEmpty(road)) {
                road = road.split("/")[1];
            }

            String milestone = StringUtil.formatDoubleToFourInteger(geocode.getMilestone());
            milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, road, milestone);
            childStatusEntity.setTitleMilestone(milestone);

            // 为子视图设置详细攻略信息
            childStatusEntity.setRouteDetail(geocode.getAddress());
//            StringBuffer sb = new StringBuffer();
//            for (int j = 0; j < childTimeArray[i].length; j++) {
//                sb.append(childTimeArray[i][j]);
//                sb.append("\n");
//            }

            childList.add(childStatusEntity);
            groupStatusEntity.setChildList(childList);
            groupList.add(groupStatusEntity);
        }

        return groupList;
    }

    /**
     * 初始化ExpandableTextView
     */
    private void initExpandableTextView() {
        briefDescribe = (ExpandableTextView) contentView.findViewById(R.id.guide_brief_describe);
        shadeView = contentView.findViewById(R.id.bottom_shade);

        // 设置文字
        //String detail = TTTApplication.getDbHelper().getRouteDetail(infoRouteActivity.getRoute());
        //expandableTextView.setText(detail);
        briefDescribe.setText("下面说下思路吧，就是先获取TextView完全展开时的最大maxLines记录下来，让后再把TextView的maxLine设置为你想指定的任何值，我这里指定的是1，这样加载完成VIew之后我们看到的TextView就不是完全展开的，这样做的主要目的是拿到完全展开的maxLines，让后用户点击的时候不断的更新maxLine大小即可"
        );

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

        timelineAdapter.setData(getListData(start, end));
        // 遍历所有group,将所有项设置成默认关闭
        int groupCount = timelineList.getCount();
        for (int i = 0; i < groupCount; i++) {
            timelineList.collapseGroup(i);
        }
        // 打开第一个
        timelineList.expandGroup(0);
    }
}
