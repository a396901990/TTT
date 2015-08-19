package com.dean.travltotibet.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.greendao.Routes;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.PlanListAdapter;
import com.dean.travltotibet.adapter.PlanSpinnerAdapter;
import com.dean.travltotibet.model.AbstractPoint;
import com.dean.travltotibet.model.AbstractSeries;
import com.dean.travltotibet.model.IndicatorSeries;
import com.dean.travltotibet.model.MountainSeries;
import com.dean.travltotibet.model.Place;
import com.dean.travltotibet.ui.IndicatorChartView;
import com.dean.travltotibet.ui.RouteChartView;
import com.dean.travltotibet.ui.SlidingLayout;
import com.dean.travltotibet.util.ChartCrosshairUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class RouteFragment extends Fragment {

    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.route_layout, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initDropdownNavigation();
        //initPlanList();
    }

    /**
     * 初始化下拉菜单
     */
//    private void initPlanList()
//    {
//        ArrayList<PlanListAdapter.PlanListItem> mPlans = new ArrayList<PlanListAdapter.PlanListItem>();
//
//        // 获取数据库路线
//        List<Routes> routes = TTTApplication.getDbHelper().getRoutsList();
//
//        for (Routes r : routes) {
//            mPlans.add(new PlanListAdapter.PlanListItem("D"+r.getId(), r.getStart() ,r.getEnd(), r.getDistance()));
//        }
//
//        PlanListAdapter adapter = new PlanListAdapter(getActivity());
//        adapter.setData(mPlans);
//
//        ListView planList = (ListView) root.findViewById(R.id.plan_list);
//        planList.setAdapter(adapter);
//    }

    /**
     * 初始化下拉菜单
     */
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
