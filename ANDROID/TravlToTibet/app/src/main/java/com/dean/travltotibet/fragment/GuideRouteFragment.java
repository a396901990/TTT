package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.TimelineExpandAdapter;
import com.dean.travltotibet.adapter.TimelineExpandAdapter.ChildTimelineEntity;
import com.dean.travltotibet.adapter.TimelineExpandAdapter.GroupTimelineEntity;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class GuideRouteFragment extends BaseRouteFragment {

    private View root;

    private RouteActivity mActivity;

    public static GuideRouteFragment newInstance() {
        GuideRouteFragment newFragment = new GuideRouteFragment();
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_route_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (RouteActivity) getActivity();

        initOverallView();
        initTimelineRouteListView();
    }

    /**
     * 初始化路线时间轴列表
     */
    private void initTimelineRouteListView() {
        ExpandableListView timelineList = (ExpandableListView) root.findViewById(R.id.timeline_list);
        TimelineExpandAdapter timelineAdapter = new TimelineExpandAdapter(mActivity, getListData());
        timelineList.setAdapter(timelineAdapter);
        timelineList.setGroupIndicator(null); // 去掉默认带的箭头
        timelineList.setSelection(0);// 设置默认选中项
        timelineList.setClickable(false);
        // 遍历所有group,将所有项设置成默认展开
        int groupCount = timelineList.getCount();
        for (int i = 0; i < groupCount; i++) {
            timelineList.expandGroup(i);
        }
    }

    private List<GroupTimelineEntity> getListData() {
        List<GroupTimelineEntity> groupList;
        String[] strArray = new String[]{"叶城", "菩萨村", "南京矿山"};
        String[] markArray = new String[]{"G2191835", "G2191835", "G2191835"};
        String[][] childTimeArray = new String[][]{
                {"俯卧撑十次", "仰卧起坐二十次", "大喊我爱紫豪二十次", "每日赞紫豪一次"},
                {"亲，快快滴点赞哦~"}, {"没有赞的，赶紧去赞哦~"}};

        groupList = new ArrayList<GroupTimelineEntity>();
        for (int i = 0; i < strArray.length; i++) {
            GroupTimelineEntity groupStatusEntity = new GroupTimelineEntity();
            groupStatusEntity.setTitleName(strArray[i]);

            List<ChildTimelineEntity> childList = new ArrayList<ChildTimelineEntity>();

            ChildTimelineEntity childStatusEntity = new ChildTimelineEntity();
            childStatusEntity.setTitleHeight(markArray[i]);
            childStatusEntity.setTitleMilestone(markArray[i]);

            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < childTimeArray[i].length; j++) {
                sb.append(childTimeArray[i][j]);
                sb.append("\n");
            }
            childStatusEntity.setRouteDetail(sb.toString());
            childList.add(childStatusEntity);

            groupStatusEntity.setChildList(childList);
            groupList.add(groupStatusEntity);
        }
        return groupList;
    }

    /**
     * 初始化简介视图
     */
    private void initOverallView() {

        String start = mActivity.getPlanStart();
        String end = mActivity.getPlanEnd();
        String distance = mActivity.getPlanDistance();

        updateOverallView(start, end, distance);
    }

    /**
     * 根据start，end去数据库取值更新overall视图
     *
     * @param start
     * @param end
     * @param distance
     */
    private void updateOverallView(String start, String end, String distance) {
        TextView start_name = (TextView) root.findViewById(R.id.route_start_name);
        TextView start_height = (TextView) root.findViewById(R.id.route_start_height);
        TextView start_milestone = (TextView) root.findViewById(R.id.route_start_milestone);

        TextView end_name = (TextView) root.findViewById(R.id.route_end_name);
        TextView end_height = (TextView) root.findViewById(R.id.route_end_height);
        TextView end_milestone = (TextView) root.findViewById(R.id.route_end_milestone);

        TextView overall_distance = (TextView) root.findViewById(R.id.route_distance);

        start_name.setText(start);
        end_name.setText(end);
        overall_distance.setText(distance);

        String start_road = TTTApplication.getDbHelper().getRoadWithName(start);
        if (!TextUtils.isEmpty(start_road))
            start_road = start_road.split("/")[1];

        String end_road = TTTApplication.getDbHelper().getRoadWithName(end);
        if (!TextUtils.isEmpty(end_road))
            end_road = end_road.split("/")[1];

        String startHeight = StringUtil.formatDoubleToInteger(TTTApplication.getDbHelper().getElevationWithName(start));
        String endHeight = StringUtil.formatDoubleToInteger(TTTApplication.getDbHelper().getElevationWithName(end));
        String startMilestone = StringUtil.formatDoubleToInteger(TTTApplication.getDbHelper().getMilestoneWithName(start));
        String endMilestone = StringUtil.formatDoubleToInteger(TTTApplication.getDbHelper().getMilestoneWithName(end));

        start_height.setText(String.format(Constants.GUIDE_OVERALL_HEIGHT_FORMAT, startHeight));
        end_height.setText(String.format(Constants.GUIDE_OVERALL_HEIGHT_FORMAT, endHeight));
        start_milestone.setText(String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, start_road, startMilestone));
        end_milestone.setText(String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, end_road, endMilestone));
    }

    @Override
    public void updateRoute(String start, String end, String date, String distance) {
        super.updateRoute(start, end, date, distance);
        updateOverallView(start, end, distance);
    }
}
