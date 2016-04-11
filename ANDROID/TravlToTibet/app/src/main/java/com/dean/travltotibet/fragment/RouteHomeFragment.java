package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.greendao.Plan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.PlanListAdapter;
import com.dean.travltotibet.model.RoadInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class RouteHomeFragment extends BaseGuideFragment implements PlanListAdapter.PlanItemListener {

    private View root;

    private RouteActivity routeActivity;

    private ListView listView;

    private View roadMessageView;

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

        listView = (ListView) root.findViewById(R.id.plan_list);
        listView.setAdapter(adapter);
        roadMessageView = getRoadMessageView();
        listView.addHeaderView(roadMessageView);
        getRoadMessageInfo();
    }

    private void getRoadMessageInfo() {
        BmobQuery<RoadInfo> query = new BmobQuery<>();
        query.order("-comment,-createdAt");
        query.addQueryKeys("title,priority");
        query.addWhereEqualTo("route", routeActivity.getRouteName());
//        query.addWhereEqualTo("status", TeamRequest.PASS_STATUS);
//        query.setLimit(3);

        query.findObjects(getActivity(), new FindListener<RoadInfo>() {
            @Override
            public void onSuccess(List<RoadInfo> list) {
                setUpMessage(list, roadMessageView);
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

    @Override
    public void update() {

    }
}
