package com.dean.travltotibet.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.TimelineExpandAdapter;
import com.dean.travltotibet.adapter.TimelineExpandAdapter.ChildTimelineEntity;
import com.dean.travltotibet.adapter.TimelineExpandAdapter.GroupTimelineEntity;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.ProgressUtil;
import com.dean.travltotibet.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteGuideFragment extends BaseRouteFragment {

    private View root;

    private RouteActivity routeActivity;

    private TimelineExpandAdapter timelineAdapter;

    private ExpandableListView timelineList;

    public static RouteGuideFragment newInstance() {
        RouteGuideFragment newFragment = new RouteGuideFragment();
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_route_fragment_view, container, false);
        lazyLoad();
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();

        initTimelineRouteListView();
    }

    /**
     * 初始化路线时间轴列表
     */
    private void initTimelineRouteListView() {
        timelineList = (ExpandableListView) root.findViewById(R.id.timeline_list);
        // 初始化数据adapter并赋值
        timelineAdapter = new TimelineExpandAdapter(routeActivity, getListData(routeActivity.getPlanStart(), routeActivity.getPlanEnd()));
        timelineList.setAdapter(timelineAdapter);
        timelineList.expandGroup(0);
    }

    private List<GroupTimelineEntity> getListData(String start, String end) {

        // 根据起点终点获取数据
        String routeName = routeActivity.getCurrentRoute().getRoute();
        boolean isForward = routeActivity.isForward();
        List<Geocode> geocodes = TTTApplication.getDbHelper().getNonPathGeocodeListWithNameAndRoute(routeName, start, end, isForward);

        // groupList存放所有数据
        List<GroupTimelineEntity> groupList = new ArrayList<GroupTimelineEntity>();

        for (Geocode geocode : geocodes) {
            // title模型对象
            GroupTimelineEntity groupStatusEntity = new GroupTimelineEntity();
            // 为title设置值
            groupStatusEntity.setTitleName(geocode.getName());

            List<ChildTimelineEntity> childList = new ArrayList<ChildTimelineEntity>();
            // child子view的模型
            ChildTimelineEntity childStatusEntity = new ChildTimelineEntity();

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

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void updateRoute() {
        updateTimelineView();
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
