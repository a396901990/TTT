package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.activity.RouteInfoActivity;
import com.dean.travltotibet.adapter.RoutePlanListAdapter;
import com.dean.travltotibet.ui.SquareView;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/19/15.
 * <p/>
 * 用来控制route类型和计划
 */
public class RoutePlanFragment extends Fragment {

    private RouteInfoActivity routeInfoActivity;

    private View root;

    public RoutePlanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.route_plan_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        routeInfoActivity = (RouteInfoActivity) getActivity();

        initPlanList();
    }

    private void initPlanList() {
        ListView mListView = (ListView) root.findViewById(R.id.plan_list);
        ArrayList<RoutePlan> plans = (ArrayList<RoutePlan>) TTTApplication.getDbHelper().getRoutePlans(routeInfoActivity.getRouteName(),"", true);
        RoutePlanListAdapter mAdapter = new RoutePlanListAdapter(routeInfoActivity);
        mAdapter.setData(plans);

        mListView.setAdapter(mAdapter);
    }


}
