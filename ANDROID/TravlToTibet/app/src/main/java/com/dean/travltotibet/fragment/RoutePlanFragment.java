package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.greendao.Plan;
import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.PlanAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class RoutePlanFragment extends Fragment implements PlanAdapter.PlanItemListener{

    private View root;

    private RouteActivity routeActivity;

    /**
     * 更新路线监听器
     */
    public interface RouteListener {
        void updateRoute(String start, String end);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.route_plan_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
        updatePlanOverall();
        initPlanList();
    }

    /**
     * 初始化总览视图
     */
    private void updatePlanOverall() {

        // 获取当前的路线
        Route route = routeActivity.getCurrentRoute();

        final String start = route.getStart();
        final String end = route.getEnd();
        final String dis = route.getDistance();
        final String name = route.getName();
        final String des = route.getDescribe();
        final String rank_hard = route.getRank_hard();
        final String rank_view = route.getRank_view();
        final String rank_road = route.getRank_road();

        View overall = root.findViewById(R.id.ripple_view);

        TextView date = (TextView) overall.findViewById(R.id.plan_date);
        TextView detail_start = (TextView) overall.findViewById(R.id.plan_detail_start);
        TextView detail_end = (TextView) overall.findViewById(R.id.plan_detail_end);
        TextView distance = (TextView) overall.findViewById(R.id.plan_distance);

        date.setText(name);
        detail_start.setText(start);
        detail_end.setText(end);
        distance.setText(dis);

        overall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChart(true, start, end, name, dis, des, rank_hard, rank_view, rank_road);
            }
        });
    }

    /**
     * 初始化列表菜单
     */
    private void initPlanList() {
        final ArrayList<Plan> mPlans = new ArrayList<Plan>();

        // 获取数据库路线
        final ArrayList<Plan> plans = (ArrayList<Plan>) TTTApplication.getDbHelper().getPlanList(routeActivity.getRoutePlanId());

        PlanAdapter adapter = new PlanAdapter(getActivity());
        adapter.setPlanListener(this);
        adapter.setData(plans);

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.plan_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 根据路线的起始和终点获取数据，并更新chart视图
     *
     * @param start 初始地点
     * @param end   终点
     */
    public void updateChart(boolean isRoute, String start, String end, String date, String distance, String describe, String rank_hard, String rank_view, String rank_road) {

        // 更新标题栏文字
        routeActivity.updateHeader(isRoute, start, end, date, distance, describe, rank_hard, rank_view, rank_road);

        // 关闭菜单
        if (routeActivity.getSlidingMenu().isMenuShowing()) {
            routeActivity.getSlidingMenu().toggle();
        }
    }

    @Override
    public void onPlanClick(Plan plan) {
        updateChart(false, plan.getStart(), plan.getEnd(), plan.getDay(), plan.getDistance(), plan.getDescribe(), plan.getRank_hard(), plan.getRank_view(), plan.getRank_road());
    }

}
