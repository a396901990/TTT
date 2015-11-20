package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.dean.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.model.Location;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteMapFragment extends BaseRouteFragment implements BaiduMap.OnMapClickListener,
        OnGetRoutePlanResultListener {

    private View rootView;

    private View contentView;

    private RouteActivity routeActivity;

    private MapView mMapView = null;

    private BaiduMap mBaiduMap;

    Button normail, satile;

    private Marker mMarkerA;
    private Marker mMarkerB;
    private InfoWindow mInfoWindow;

    // 搜索模块，也可去掉地图模块独立使用
    private RoutePlanSearch mSearch = null;

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
        contentView = inflater.inflate(R.layout.route_map_fragment_view, null, false);

        // 获取地图控件引用
        mMapView = (MapView) contentView.findViewById(R.id.id_bmapView);
    }

    @Override
    protected void onLoadPrepared() {
        initMap();
        initBtn();
        initMarkerClickEvent();
    }

    /** 初始化地图数据 */
    private void initMap() {
        mBaiduMap = mMapView.getMap();

        //地图点击事件处理
        mBaiduMap.setOnMapClickListener(this);
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }

    @Override
    protected void onLoading() {
        searchRoute();
    }

    @Override
    protected void onLoadFinished() {
        ((ViewGroup) rootView).addView(contentView);
    }

    /** 搜索路线 */
    public void searchRoute() {
        //重置浏览节点的路线数据
        mBaiduMap.clear();

        Location startLocation = TTTApplication.getDbHelper().getLocationWithName(routeActivity.getPlanStart());
        LatLng startLL = new LatLng(startLocation.getLatitude(), startLocation.getLongitude());

        Location endLocation = TTTApplication.getDbHelper().getLocationWithName(routeActivity.getPlanEnd());
        LatLng endLL = new LatLng(endLocation.getLatitude(), endLocation.getLongitude());

        // 初始化start，end图标
        initStartEndMarkIcon(startLL, endLL);

        //设置起终点信息
        PlanNode stNode = PlanNode.withLocation(startLL);
        PlanNode enNode = PlanNode.withLocation(endLL);
        // 实际使用中请对起点终点城市进行正确的设定
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

    /** 初始化start，end图标 */
    private void initStartEndMarkIcon(LatLng startLL, LatLng endLL) {
        BitmapDescriptor bdStart = BitmapDescriptorFactory.fromResource(R.drawable.start_icon);
        BitmapDescriptor bdEnd = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);

        OverlayOptions ooStart = new MarkerOptions().position(startLL).icon(bdStart).zIndex(9);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooStart));
        OverlayOptions ooEnd = new MarkerOptions().position(endLL).icon(bdEnd).zIndex(5);
        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooEnd));
    }

    /** 添加覆盖物点击事件 */
    private void initMarkerClickEvent() {

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {
                Button button = new Button(getActivity());
                button.setBackgroundResource(R.drawable.popup);
                InfoWindow.OnInfoWindowClickListener listener = null;
                // start
                if (marker == mMarkerA) {
                    button.setText("起点");
                    listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                            mBaiduMap.hideInfoWindow();
                        }
                    };
                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                } else if (marker == mMarkerB) {
                    button.setText("终点");
                    listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                            mBaiduMap.hideInfoWindow();
                        }
                    };
                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });
    }

    private void addMarkIcon() {
//        OverlayOptions overlayOptions = null;
//        overlayOptions = new MarkerOptions().position(startLL)
//                .icon(bitmapdes).zIndex(5);
//        mBaiduMap.addOverlay(overlayOptions);
//
//        OverlayOptions aoverlayOptions = null;
//        aoverlayOptions = new MarkerOptions().position(endLL)
//                .icon(bitmapdes).zIndex(5);
//        mBaiduMap.addOverlay(aoverlayOptions);

//构建文字Option对象，用于在地图上添加文字
//        OverlayOptions textOption = new TextOptions()
//                .bgColor(0xAAFFFF00)
//                .fontSize(24)
//                .fontColor(0xFFFF00FF)
//                .text("库地达坂")
//                .position(ll);
//在地图上添加该文字对象并显示
//        mBaiduMap.addOverlay(textOption);
    }

    private void initBtn() {
        normail = (Button) contentView.findViewById(R.id.normal);
        satile = (Button) contentView.findViewById(R.id.normal);

        normail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }
        });

        satile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            }
        });
    }

    @Override
    public void updateRoute() {
        searchRoute();
    }

    @Override
    public void onMapClick(LatLng point) {
        mBaiduMap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            // 错误
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            result.getSuggestAddrInfo();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    /** 定制RouteOverly */
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.transparent_mask_icon);
        }

        @Override
        public int getLineColor() {
            return TTTApplication.getColor(R.color.dark_green);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.transparent_mask_icon);
        }
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mSearch.destroy();
        mMapView.onDestroy();
        super.onDestroy();
    }

}
