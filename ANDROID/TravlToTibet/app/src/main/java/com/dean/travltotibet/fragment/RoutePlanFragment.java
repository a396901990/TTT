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

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.activity.RouteInfoActivity;
import com.dean.travltotibet.ui.SquareView;
import com.dean.travltotibet.util.Constants;

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
        ListView listView = (ListView) root.findViewById(R.id.plan_list);
    }


}
