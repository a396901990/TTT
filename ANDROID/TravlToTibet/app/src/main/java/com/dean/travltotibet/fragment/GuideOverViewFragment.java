package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.ui.ExpandableTextView;
import com.dean.travltotibet.ui.ratingview.RatingBar;

import android.widget.TextView;

import com.dean.greendao.Plan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.ratingview.RatingView;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 12/8/15.
 */
public class GuideOverViewFragment extends BaseGuideFragment {

    private RouteActivity routeActivity;

    private View root;

    private Plan mPlan;

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
        if (!routeActivity.isRoute()) {
            updateOverView();
        }
    }

    private void updateOverView() {

        mPlan = routeActivity.getCurrentPlan();

        TextView start = (TextView) root.findViewById(R.id.overview_start);
        TextView end = (TextView) root.findViewById(R.id.overview_end);
        TextView date = (TextView) root.findViewById(R.id.overview_plan);
        TextView distance = (TextView) root.findViewById(R.id.overview_distance);
        TextView hours = (TextView) root.findViewById(R.id.overview_hours);

        // 设置评分条
        RatingView ratingView = (RatingView) root.findViewById(R.id.rating_view);
        ratingView.removeAll();
        RatingBar rateHard = new RatingBar(Integer.parseInt(mPlan.getRank_hard()), "难度");
        RatingBar rateView = new RatingBar(Integer.parseInt(mPlan.getRank_view()), "风景");
        RatingBar rateRoad = new RatingBar(Integer.parseInt(mPlan.getRank_road()), "路况");

        ratingView.addRatingBar(rateHard);
        ratingView.addRatingBar(rateView);
        ratingView.addRatingBar(rateRoad);
        ratingView.show();

        // 设置路线起始&终点
        start.setText(mPlan.getStart());
        end.setText(mPlan.getEnd());

        // 设置计划（DAY1/26天）
        String planDate = mPlan.getDay();
        // String planDays = TTTApplication.getDbHelper().getPlanDays(routeActivity.getRoutePlanId());
        date.setText(String.format(Constants.HEADER_DAY, planDate));

        // 设置长度
        distance.setText(mPlan.getDistance());

        // 消耗时间
        hours.setText(mPlan.getHours());

        // 描述
        final ExpandableTextView describe = (ExpandableTextView) root.findViewById(R.id.overview_describe);
        final View shadeView = root.findViewById(R.id.bottom_shade);
        shadeView.setVisibility(View.VISIBLE);
        describe.setExpanded(false);
        describe.setText(mPlan.getDescribe());

        // 设置监听状态
        describe.setOnExpandListener(new ExpandableTextView.OnExpandListener() {
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
                describe.toggle();
            }
        });
    }

    @Override
    public void update() {
        if (!routeActivity.isRoute()) {
            updateOverView();
        }
    }
}
