package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.greendao.Plan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RoadInfoCreateActivity;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.PlanListAdapter;
import com.dean.travltotibet.adapter.SimpleRoadInfoAdapter;
import com.dean.travltotibet.model.RoadInfo;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class RouteHomeFragment extends Fragment implements PlanListAdapter.PlanItemListener {

    private View root;

    private RouteActivity routeActivity;

    private SimpleRoadInfoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.route_home_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();

        initPlanList();
    }

    /**
     * 初始化列表菜单
     */
    private void initPlanList() {
        // 获取数据库路线
        final ArrayList<Plan> plans = (ArrayList<Plan>) TTTApplication.getDbHelper().getPlanList(routeActivity.getRoutePlanId());

        PlanListAdapter adapter = new PlanListAdapter(getActivity());
        adapter.setListener(this);
        adapter.setData(plans);

        ListView listView = (ListView) root.findViewById(R.id.plan_list);
        listView.setAdapter(adapter);

        setHeaderView(listView);
    }

    private void setHeaderView(ListView listView) {
        View roadInfoView = LayoutInflater.from(getActivity()).inflate(R.layout.road_info_view, null);
        RecyclerView mRecyclerView = (RecyclerView) roadInfoView.findViewById(R.id.road_info_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new SimpleRoadInfoAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        listView.addHeaderView(roadInfoView);

        View feedbackBtn = roadInfoView.findViewById(R.id.feedback_btn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoadInfoCreateActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROUTE, routeActivity.getRouteName());
                getActivity().startActivity(intent);
            }
        });

        getRoadInfo();
    }

    private void getRoadInfo() {
        BmobQuery<RoadInfo> query = new BmobQuery<>();
        query.order("-comment,-createdAt");
        query.addQueryKeys("title,priority");
        query.addWhereEqualTo("route", routeActivity.getRouteName());
//        query.addWhereEqualTo("status", TeamRequest.PASS_STATUS);
//        query.setLimit(3);

        query.findObjects(getActivity(), new FindListener<RoadInfo>() {
            @Override
            public void onSuccess(List<RoadInfo> list) {
                adapter.setData((ArrayList<RoadInfo>) list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
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

}
