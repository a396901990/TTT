package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.UiSettings;
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

    private Marker mMarkerA;
    private Marker mMarkerB;
    private InfoWindow mInfoWindow;

    // 搜索模块，也可去掉地图模块独立使用
    private RoutePlanSearch mSearch = null;

    private UiSettings mUiSettings;

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
        BaiduMapOptions mapOptions = new BaiduMapOptions();
        //mapOptions.scaleControlEnabled(false); // 隐藏比例尺控件
        mapOptions.zoomControlsEnabled(false);//隐藏缩放按钮
        mMapView = new MapView(getActivity(), mapOptions);
        mMapView.setClickable(true);

        ViewGroup mapViewContent = (ViewGroup) contentView.findViewById(R.id.map_view_content);
        mapViewContent.addView(mMapView);
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
        mUiSettings = mBaiduMap.getUiSettings();

        // 开启指南针
        mUiSettings.setCompassEnabled(true);
        // mBaiduMap.showMapPoi(false);

        //地图点击事件处理
        mBaiduMap.setOnMapClickListener(this);
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

        // 俯视
//        MapStatus ms = new MapStatus.Builder().overlook(-30).build();
//        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
//        mBaiduMap.animateMapStatus(u, 1000);
    }

    @Override
    protected void onLoading() {
        searchRoute();
    }

    @Override
    protected void onLoadFinished() {
        ((ViewGroup) rootView).addView(contentView);
        View view = rootView.findViewById(R.id.map_loading_bar);
        view.setVisibility(View.VISIBLE);
    }

    /** 搜索路线 */
    public void searchRoute() {
        //重置浏览节点的路线数据
        mBaiduMap.clear();
        // show loading bar
        View loadingBar = rootView.findViewById(R.id.map_loading_bar);
        if (loadingBar != null) {
            loadingBar.setVisibility(View.VISIBLE);
        }

        Location startLocation = TTTApplication.getDbHelper().getLocationWithName(routeActivity.getPlanStart());
        LatLng startLL = new LatLng(startLocation.getLatitude(), startLocation.getLongitude());

        Location endLocation = TTTApplication.getDbHelper().getLocationWithName(routeActivity.getPlanEnd());
        LatLng endLL = new LatLng(endLocation.getLatitude(), endLocation.getLongitude());

        // 初始化start，end图标
        initMarkIcon(startLL, endLL);

        //设置起终点信息
        PlanNode stNode = PlanNode.withLocation(startLL);
        PlanNode enNode = PlanNode.withLocation(endLL);
        // 实际使用中请对起点终点城市进行正确的设定
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

    /** 初始化图标 */
    private void initMarkIcon(LatLng startLL, LatLng endLL) {
//        BitmapDescriptor bdStart = BitmapDescriptorFactory.fromResource(R.drawable.start_icon);
//        BitmapDescriptor bdEnd = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//
////        OverlayOptions ooStart = new MarkerOptions().position(startLL).icon(bdStart).zIndex(9);
////        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooStart));
////        OverlayOptions ooEnd = new MarkerOptions().position(endLL).icon(bdEnd).zIndex(5);
////        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooEnd));
    }

    /** 添加覆盖物点击事件 */
    private void initMarkerClickEvent() {

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {
                Button button = new Button(getActivity());
                button.setBackgroundResource(R.drawable.popup);
                InfoWindow.OnInfoWindowClickListener listener = null;
                // start
                if (DrivingRouteOverlay.START_MARKER.equals(marker.getTitle())) {
                    button.setText("起点");
                    listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                            mBaiduMap.hideInfoWindow();
                        }
                    };
                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                // end
                else if (DrivingRouteOverlay.END_MARKER.equals(marker.getTitle())) {
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
        // 放大
        View zoomIn = contentView.findViewById(R.id.zoom_in_btn);
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapStatusUpdate u = MapStatusUpdateFactory.zoomIn();
                mBaiduMap.animateMapStatus(u);
            }
        });

        // 缩小
        View zoomOut = contentView.findViewById(R.id.zoom_out_btn);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapStatusUpdate u = MapStatusUpdateFactory.zoomOut();
                mBaiduMap.animateMapStatus(u);
            }
        });

//        normail = (Button) contentView.findViewById(R.id.normal);
//        satile = (Button) contentView.findViewById(R.id.saitlite);
//
//        normail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//            }
//        });
//
//        satile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//            }
//        });
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

        // hidden loading bar
        View loadingBar = rootView.findViewById(R.id.map_loading_bar);
        loadingBar.setVisibility(View.GONE);
    }

    /** 定制RouteOverly */
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public int getLineColor() {
            return TTTApplication.getColor(R.color.dark_green);
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
        if (mSearch != null) {
            mSearch.destroy();
        }
        mMapView.onDestroy();
        super.onDestroy();
    }

}
