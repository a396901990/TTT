package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.ExpandableTextView;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 12/8/15.
 */
public class GuideOverViewFragment extends BaseGuideFragment {

    private RouteActivity routeActivity;

    private View root;

    private ExpandableTextView overviewDescribe;

    private View shadeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_overview_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
        updateOverView();
    }

    private void updateOverView() {
        TextView start = (TextView) root.findViewById(R.id.overview_start);
        TextView end = (TextView) root.findViewById(R.id.overview_end);
        TextView date = (TextView) root.findViewById(R.id.overview_plan);
        TextView distance = (TextView) root.findViewById(R.id.overview_distance);
        RatingBar rank = (RatingBar) root.findViewById(R.id.overview_rank);

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

    /**
     * 初始化ExpandableTextView
     */
    private void initDescribeExpandableTextView() {
        overviewDescribe = (ExpandableTextView) root.findViewById(R.id.overview_describe);
        shadeView = root.findViewById(R.id.bottom_shade);

        // 设置文字
        overviewDescribe.setText(routeActivity.getPlanDescribe());

        // 设置监听状态
        overviewDescribe.setOnExpandListener(new ExpandableTextView.OnExpandListener() {
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
                overviewDescribe.toggle();
            }
        });

    }

    @Override
    public void update() {
        updateOverView();
    }
}
