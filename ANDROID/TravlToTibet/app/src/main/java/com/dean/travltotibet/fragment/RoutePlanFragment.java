package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dean.greendao.Plan;
import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.PlanListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class RoutePlanFragment extends Fragment {

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
//        initDropdownNavigation();
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
        final String rank = route.getRank();
        final String des = route.getDescribe();

        RelativeLayout overall = (RelativeLayout) root.findViewById(R.id.overall_route);

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
                updateChart(true, start, end, name, dis, rank, des);
            }
        });
    }

    /**
     * 初始化列表菜单
     */
    private void initPlanList() {
        final ArrayList<PlanListAdapter.PlanListItem> mPlans = new ArrayList<PlanListAdapter.PlanListItem>();

        // 获取数据库路线
        final List<Plan> plans = TTTApplication.getDbHelper().getPlanList(routeActivity.getRoutePlanId());

        for (Plan plan : plans) {
            mPlans.add(new PlanListAdapter.PlanListItem("DAY" + plan.getDay(), plan.getStart(), plan.getEnd(), plan.getDistance()));
        }

        PlanListAdapter adapter = new PlanListAdapter(getActivity());
        adapter.setData(mPlans);

        ListView planList = (ListView) root.findViewById(R.id.plan_list);
        planList.setAdapter(adapter);
        planList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plan plan = plans.get(position);

                // 更新chart视图
                updateChart(false, plan.getStart(), plan.getEnd(), plan.getDay(), plan.getDistance(), plan.getRank(), plan.getDescribe());
            }
        });
    }

    /**
     * 根据路线的起始和终点获取数据，并更新chart视图
     *
     * @param start 初始地点
     * @param end   终点
     */
    public void updateChart(boolean isRoute, String start, String end, String date, String distance, String rank, String describe) {

        // 更新标题栏文字
        routeActivity.updateHeader(isRoute, start, end, date, distance, rank, describe);

        // 关闭菜单
        if (routeActivity.getSlidingMenu().isMenuShowing()) {
            routeActivity.getSlidingMenu().toggle();
        }
    }

}
