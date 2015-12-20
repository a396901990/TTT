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
public class RoutePlanFragment extends Fragment implements PlanAdapter.PlanItemListener {

    private View root;

    private RouteActivity routeActivity;

    private View headerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.route_plan_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
        headerView = root.findViewById(R.id.overall_route);

        updateRouteOverall();
        initPlanList();
    }

    /**
     * 初始化总览视图
     */
    private void updateRouteOverall() {

        View overall = root.findViewById(R.id.ripple_view);

        TextView date = (TextView) overall.findViewById(R.id.plan_date);
        TextView detail_start = (TextView) overall.findViewById(R.id.plan_detail_start);
        TextView detail_end = (TextView) overall.findViewById(R.id.plan_detail_end);
        TextView distance = (TextView) overall.findViewById(R.id.plan_distance);

        // 获取当前的路线
        Route route = routeActivity.getCurrentRoute();

        final String start = route.getStart();
        final String end = route.getEnd();
        final String dis = route.getDistance();
        final String name = route.getName();
        
        date.setText("总览："+name);
        detail_start.setText(start);
        detail_end.setText(end);
        distance.setText(dis);

        // 切换到路线总览
        overall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(true, null);
            }
        });
    }

    /**
     * 初始化列表菜单
     */
    private void initPlanList() {
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

    // 更新视图
    public void update(boolean isRoute, Plan plan) {

        // 更新标题栏文字
        routeActivity.updateHeader(isRoute, plan);

        // 关闭菜单
        if (routeActivity.getSlidingMenu().isMenuShowing()) {
            routeActivity.getSlidingMenu().toggle();
        }
    }

    @Override
    public void onPlanClick(Plan plan) {
        update(false, plan);
    }

    public void showHeaderView() {
        headerView.setVisibility(View.VISIBLE);
    }

    public void hideHeaderView() {
        headerView.setVisibility(View.GONE);
    }

}
