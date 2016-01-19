package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.adapter.WhereGoItemGridAdapter;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 11/28/15.
 */
public class WhereGoFragment extends Fragment {

    private View root;

    private GridView longGridView;
    private GridView middleGridView;
    private GridView shortGridView;

    private final static int LONG_DIS_RANGE = 1000;
    private final static int MIDDLE_DIS_RANGE = 300;
    private final static int SHORT_DIS_RANGE = 0;

    private WhereGoItemGridAdapter longAdapter;
    private WhereGoItemGridAdapter middleAdapter;
    private WhereGoItemGridAdapter shortAdapter;

    private ArrayList<Route> routes;
    private ArrayList<Route> longData = new ArrayList<Route>();
    private ArrayList<Route> middleData = new ArrayList<Route>();
    private ArrayList<Route> shortData = new ArrayList<Route>();

    public WhereGoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.where_go_fragment_layout, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        initAdapter();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        longGridView = (GridView) root.findViewById(R.id.lang_distance_grid);
        middleGridView = (GridView) root.findViewById(R.id.middle_distance_grid);
        shortGridView = (GridView) root.findViewById(R.id.short_distance_grid);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        longAdapter = new WhereGoItemGridAdapter(getActivity());
        middleAdapter = new WhereGoItemGridAdapter(getActivity());
        shortAdapter = new WhereGoItemGridAdapter(getActivity());

        longAdapter.setData(longData);
        middleAdapter.setData(middleData);
        shortAdapter.setData(shortData);

        longGridView.setAdapter(longAdapter);
        longGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到InfoRouteActivity(类型BIKE)
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROUTE, longData.get(position).getRoute());
                intent.putExtra(IntentExtra.INTENT_ROUTE_NAME, longData.get(position).getName());
                intent.putExtra(IntentExtra.INTENT_ROUTE_TYPE, TravelType.BIKE);
                startActivity(intent);
            }
        });

        middleGridView.setAdapter(middleAdapter);
        middleGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到InfoRouteActivity(类型BIKE)
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROUTE, middleData.get(position).getRoute());
                intent.putExtra(IntentExtra.INTENT_ROUTE_NAME, middleData.get(position).getName());
                intent.putExtra(IntentExtra.INTENT_ROUTE_TYPE, TravelType.BIKE);
                startActivity(intent);
            }
        });

        shortGridView.setAdapter(shortAdapter);
        shortGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到InfoRouteActivity(类型BIKE)
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROUTE, shortData.get(position).getRoute());
                intent.putExtra(IntentExtra.INTENT_ROUTE_NAME, shortData.get(position).getName());
                intent.putExtra(IntentExtra.INTENT_ROUTE_TYPE, TravelType.BIKE);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 获取所有路线信息
        routes = (ArrayList<Route>) TTTApplication.getDbHelper().getRoutsList();

        for (Route route : routes) {
            int distance;
            if (route.getDistance().contains("KM")) {
                distance = Integer.parseInt(route.getDistance().split("KM")[0]);
            } else {
                distance = Integer.parseInt(route.getDistance());
            }

            longData.add(route);
            middleData.add(route);
            shortData.add(route);
//            // 长线 longDistance
//            if (distance >= LONG_DIS_RANGE) {
//                longData.add(route);
//            }
//            // 中线 middleDistance
//            else if (distance >= MIDDLE_DIS_RANGE && distance < LONG_DIS_RANGE) {
//                middleData.add(route);
//            }
//            // 短线 shortDistance
//            else if (distance >= SHORT_DIS_RANGE && distance < MIDDLE_DIS_RANGE) {
//                shortData.add(route);
//            }
        }
    }


}
