package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.graphics.Rect;
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
import com.dean.travltotibet.ui.SpaceItemDecoration;
import com.dean.travltotibet.util.ScreenUtil;

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

        updateRouteOverall();
        initPlanList();
    }

    /**
     * 初始化总览视图
     */
    private void updateRouteOverall() {
        headerView = root.findViewById(R.id.overall_route);
        View overall = headerView.findViewById(R.id.ripple_view);
        final TextView overallText = (TextView) headerView.findViewById(R.id.overall_text);

        // 获取当前的路线
        final Route route = routeActivity.getCurrentRoute();
        overallText.setText(route.getName());

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
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtil.dip2px(getActivity(), 8)));

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
