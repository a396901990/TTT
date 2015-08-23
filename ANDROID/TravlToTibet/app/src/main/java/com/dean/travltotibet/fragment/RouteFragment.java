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

import com.dean.greendao.Geocode;
import com.dean.greendao.Routes;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.ChartActivity;
import com.dean.travltotibet.adapter.PlanListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class RouteFragment extends Fragment {

    private View root;

    private ChartActivity chartActivity;

    /**
     * 更新路线监听器
     */
    public interface RouteListener {
        void updateRoute(String start, String end);

        void updateHeader(String start, String end, String date);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.route_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chartActivity = (ChartActivity) getActivity();
//        initDropdownNavigation();
        updataPlanOverall("新藏线");
        initPlanList();
    }

    /**
     * 初始化总览视图
     */
    private void updataPlanOverall(final String routeName) {
        // 根据routeName获取路线名字 从数据库找出并跟新起点，终点和距离信息
        final String start = "叶城县";
        final String end = "拉萨";
        final String dis = "2650KM";

        TextView date = (TextView) root.findViewById(R.id.overall_route_date);
        TextView detail = (TextView) root.findViewById(R.id.overall_route_detail);
        TextView distance = (TextView) root.findViewById(R.id.overall_route_distance);

        date.setText(routeName);
        detail.setText(start + "-" + end);
        distance.setText(dis);

        RelativeLayout overall = (RelativeLayout) root.findViewById(R.id.overall_route);
        overall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChart(start, end, routeName);
            }
        });
    }

    /**
     * 初始化列表菜单
     */
    private void initPlanList() {
        ArrayList<PlanListAdapter.PlanListItem> mPlans = new ArrayList<PlanListAdapter.PlanListItem>();

        // 获取数据库路线
        final List<Routes> routes = TTTApplication.getDbHelper().getRoutsList();

        for (Routes r : routes) {
            mPlans.add(new PlanListAdapter.PlanListItem("DAY" + r.getId(), r.getStart(), r.getEnd(), r.getDistance()));
        }

        PlanListAdapter adapter = new PlanListAdapter(getActivity());
        adapter.setData(mPlans);

        ListView planList = (ListView) root.findViewById(R.id.plan_list);
        planList.setAdapter(adapter);
        planList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlanListAdapter.PlanListItem planItem = (PlanListAdapter.PlanListItem) parent.getItemAtPosition(position);
                String start = planItem.getPlanDetailStart();
                String end = planItem.getPlanDetailEnd();
                String date = planItem.getPlanDate();

                // 更新chart视图
                updateChart(start, end, date);
            }
        });
    }

    /**
     * 根据路线的起始和终点获取数据，并更新chart视图
     *
     * @param start 初始地点
     * @param end   终点
     */
    public void updateChart(String start, String end, String date) {

        // 更新chart视图
        chartActivity.getChartFragment().updateRoute(start, end);
        chartActivity.getChartFragment().updateHeader(start, end, date);

        // 关闭菜单
        if (chartActivity.getSlidingMenu().isSecondaryMenuShowing()) {
            chartActivity.getSlidingMenu().toggle();
        }
    }


//
//    /**
//     * 初始化下拉菜单
//     */
//    private void initDropdownNavigation()
//    {
//        ArrayList<PlanSpinnerAdapter.PlanNavItem> mPlans = new ArrayList<PlanSpinnerAdapter.PlanNavItem>();
//
//        // 获取数据库路线
//        List<Routes> routes = TTTApplication.getDbHelper().getRoutsList();
//
//        // 为下拉菜单赋值
//        // 第一个初始默认，每次加载会调用第一个的onItemSelected方法
//        mPlans.add(new PlanSpinnerAdapter.PlanNavItem("新藏线", "叶城县", "拉萨", "2793KM"));
//        // 其他路线
//        for (Routes r : routes) {
//            mPlans.add(new PlanSpinnerAdapter.PlanNavItem("D"+r.getId(), r.getStart() ,r.getEnd(), r.getDistance()));
//        }
//
//        PlanSpinnerAdapter adapter = new PlanSpinnerAdapter(getActivity());
//        adapter.setData(mPlans);
//
//        Spinner spinner = (Spinner) root.findViewById(R.id.trap_route);
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                PlanSpinnerAdapter.PlanNavItem planNavItem = (PlanSpinnerAdapter.PlanNavItem) parent.getItemAtPosition(position);
//                String date = planNavItem.getPlanDate();
//                String start = planNavItem.getPlanDetailStart();
//                String end = planNavItem.getPlanDetailEnd();
//
//                // 根据路线的起始和终点 获取数据
//                List<Geocode> geocodes = TTTApplication.getDbHelper().getGeocodeListWithName(start, end);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }
}
