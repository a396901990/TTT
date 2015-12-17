package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dean.greendao.Plan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.ExpandableTextView;
import com.dean.travltotibet.ui.numberprogressbar.NumberProgressBar;
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
        updateOverView();
    }

    private void updateOverView() {

        mPlan = routeActivity.getCurrentPlan();

        TextView start = (TextView) root.findViewById(R.id.overview_start);
        TextView end = (TextView) root.findViewById(R.id.overview_end);
        TextView date = (TextView) root.findViewById(R.id.overview_plan);
        TextView distance = (TextView) root.findViewById(R.id.overview_distance);
        TextView hours = (TextView) root.findViewById(R.id.overview_hours);
        TextView describe= (TextView) root.findViewById(R.id.overview_describe);

        // 设置评分条
        NumberProgressBar rateHard = (NumberProgressBar) root.findViewById(R.id.rate_hard);
        NumberProgressBar rateView = (NumberProgressBar) root.findViewById(R.id.rate_view);
        NumberProgressBar rateRoad = (NumberProgressBar) root.findViewById(R.id.rate_road);
        rateHard.setProgress(Integer.parseInt(mPlan.getRank_hard()));
        rateView.setProgress(Integer.parseInt(mPlan.getRank_view()));
        rateRoad.setProgress(Integer.parseInt(mPlan.getRank_road()));

        // 设置路线起始&终点
        start.setText(mPlan.getStart());
        end.setText(mPlan.getEnd());

        // 设置计划（DAY1/26天）
        String planDate = mPlan.getDay();
        String planDays = TTTApplication.getDbHelper().getPlanDays(routeActivity.getRoutePlanId());
        date.setText(String.format(Constants.BRIEF_DAY, planDate, planDays));

        // 设置长度
        distance.setText(mPlan.getDistance());

        // 消耗时间
        hours.setText(mPlan.getHours());

        // 描述
        describe.setText(mPlan.getDescribe());
    }

    @Override
    public void update() {
        updateOverView();
    }
}
