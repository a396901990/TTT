package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.model.Location;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteMapFragment extends BaseRouteFragment {

    private View rootView;

    private View contentView;

    private RouteActivity routeActivity;

    private MapView mMapView = null;

    BaiduMap mBaiduMap;

    public static RouteMapFragment newInstance() {
        return new RouteMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(routeActivity.getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_content_frame, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        contentView = inflater.inflate(R.layout.map_fragment_view, null, false);

        // 获取地图控件引用
        mMapView = (MapView) contentView.findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
    }

    @Override
    protected void onLoadPrepared() {
        initBtn();
    }

    @Override
    protected void onLoading() {
        Location location = TTTApplication.getDbHelper().getLocationWithName(routeActivity.getPlanStart());
        LatLng ll = new LatLng(location.getLatitude(),
                location.getLongitude());
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

    @Override
    protected void onLoadFinished() {
        ((ViewGroup) rootView).addView(contentView);
    }

    private void initBtn() {
        Button normail = (Button) contentView.findViewById(R.id.normal);
        Button satile = (Button) contentView.findViewById(R.id.normal);

        normail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }
        });

        satile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            }
        });
    }

    @Override
    public void updateRoute() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
