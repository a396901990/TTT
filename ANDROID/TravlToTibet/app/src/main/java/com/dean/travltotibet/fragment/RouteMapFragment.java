package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.dean.travltotibet.ui.RotateLoading;
import com.dean.travltotibet.ui.SwitchButton;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.util.MenuUtil;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteMapFragment extends BaseRouteFragment implements BaiduMap.OnMapClickListener,
        OnGetRoutePlanResultListener {

    private final static int SATELLITE = 0;

    private final static int NORMAL = 1;

    private final static int OVERVIEW = 2;

    private int currentShowType = 1;

    private View rootView;

    private View contentView;

    private RouteActivity routeActivity;

    private MapView mMapView = null;

    private BaiduMap mBaiduMap;

    private InfoWindow mInfoWindow;

    // 搜索模块，也可去掉地图模块独立使用
    private RoutePlanSearch mSearch = null;

    private DrivingRouteOverlay overlay;

    private final static int OVERLOOK_ANGLE = -45;

    private com.dean.travltotibet.ui.fab.FloatingActionButton satellite;
    private com.dean.travltotibet.ui.fab.FloatingActionButton normalMap;
    private com.dean.travltotibet.ui.fab.FloatingActionButton overViewMap;

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

        initMapView();
    }

    private void initMapView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        contentView = inflater.inflate(R.layout.route_map_fragment_view, null, false);

        // 获取地图控件引用
        BaiduMapOptions mapOptions = new BaiduMapOptions();
        //mapOptions.scaleControlEnabled(false); // 隐藏比例尺控件
        mapOptions.compassEnabled(true);
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

    /**
     * 初始化地图数据
     */
    private void initMap() {
        if (mMapView == null) {
            initMapView();
        }
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

        // start loading view
        View viewContent = rootView.findViewById(R.id.loading_content_view);
        if (viewContent != null) {
            viewContent.setVisibility(View.VISIBLE);
            RotateLoading loadingView = (RotateLoading) rootView.findViewById(R.id.rotate_loading);
            loadingView.start();
        }
    }

    /**
     * 搜索路线
     */
    public void searchRoute() {

//        if (overlay != null) {
//            overlay.removeFromMap();
//        }
        mBaiduMap.clear();

        // start loading view
        View viewContent = rootView.findViewById(R.id.loading_content_view);
        if (viewContent != null) {
            viewContent.setVisibility(View.VISIBLE);
            RotateLoading loadingView = (RotateLoading) rootView.findViewById(R.id.rotate_loading);
            loadingView.start();
        }

        Location startLocation = TTTApplication.getDbHelper().getLocationWithName(routeActivity.getPlanStart());
        LatLng startLL = new LatLng(startLocation.getLatitude(), startLocation.getLongitude());

        Location endLocation = TTTApplication.getDbHelper().getLocationWithName(routeActivity.getPlanEnd());
        LatLng endLL = new LatLng(endLocation.getLatitude(), endLocation.getLongitude());

        //设置起终点信息
        final PlanNode stNode = PlanNode.withLocation(startLL);
        final PlanNode enNode = PlanNode.withLocation(endLL);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 实际使用中请对起点终点城市进行正确的设定
                mSearch.drivingSearch((new DrivingRoutePlanOption())
                        .from(stNode)
                        .to(enNode));
            }
        });
    }

    /**
     * 初始化图标
     */
    private void initMarkIcon() {
//        BitmapDescriptor bdStart = BitmapDescriptorFactory.fromResource(R.drawable.start_icon);
//        BitmapDescriptor bdEnd = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//
////        OverlayOptions ooStart = new MarkerOptions().position(startLL).icon(bdStart).zIndex(9);
////        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooStart));
////        OverlayOptions ooEnd = new MarkerOptions().position(endLL).icon(bdEnd).zIndex(5);
////        mMarkerB = (Marker) (mBaiduMap.addOverlay(ooEnd));
    }

    /**
     * 添加覆盖物点击事件
     */
    private void initMarkerClickEvent() {

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {

                LayoutInflater layoutInflater = LayoutInflater.from(routeActivity);
                ViewGroup showLayout = (ViewGroup) layoutInflater.inflate(R.layout.map_show_detail_layout, null);
                InfoWindow.OnInfoWindowClickListener listener = null;
                TextView title = (TextView) showLayout.findViewById(R.id.map_show_title);
                TextView height = (TextView) showLayout.findViewById(R.id.map_show_height);
                TextView mile = (TextView) showLayout.findViewById(R.id.map_show_mile);

                // start
                if (DrivingRouteOverlay.START_MARKER.equals(marker.getTitle())) {
                    listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                            mBaiduMap.hideInfoWindow();
                        }
                    };
                    LatLng ll = marker.getPosition();

                    title.setText(routeActivity.getPlanStart());
                    height.setText(TTTApplication.getDbHelper().getElevationWithNameString(routeActivity.getPlanStart()));
                    mile.setText(TTTApplication.getDbHelper().getRoadMileWithName(routeActivity.getPlanStart()));

                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(showLayout), ll, -47, listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                // end
                else if (DrivingRouteOverlay.END_MARKER.equals(marker.getTitle())) {
                    listener = new InfoWindow.OnInfoWindowClickListener() {
                        public void onInfoWindowClick() {
                            mBaiduMap.hideInfoWindow();
                        }
                    };
                    LatLng ll = marker.getPosition();

                    title.setText(routeActivity.getPlanEnd());
                    height.setText(TTTApplication.getDbHelper().getElevationWithNameString(routeActivity.getPlanEnd()));
                    mile.setText(TTTApplication.getDbHelper().getRoadMileWithName(routeActivity.getPlanEnd()));

                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(showLayout), ll, -47, listener);
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

        // 转换视图
        final View changeViewBtn = contentView.findViewById(R.id.change_view_cancel_btn);
        // 转换视图，取消
        final View changeViewCancelBtn = contentView.findViewById(R.id.change_view_cancel_btn);
        // 扩展视图
        final View changeExtendView = contentView.findViewById(R.id.change_extended_view);

        changeViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeViewBtn.setVisibility(View.INVISIBLE);
                changeViewCancelBtn.setVisibility(View.VISIBLE);
                changeExtendView.setVisibility(View.VISIBLE);
            }
        });

        changeViewCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeViewBtn.setVisibility(View.VISIBLE);
                changeViewCancelBtn.setVisibility(View.INVISIBLE);
                changeExtendView.setVisibility(View.INVISIBLE);
            }
        });

        // 三个视图按钮
        final ImageButton satellite = (ImageButton) contentView.findViewById(R.id.satellite);
        final ImageButton normal = (ImageButton) contentView.findViewById(R.id.normal);
        final ImageButton overlook = (ImageButton) contentView.findViewById(R.id.overlook);

        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                satellite.setActivated(true);
                normal.setActivated(false);
                overlook.setActivated(false);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            }
        });

        normal.setActivated(true);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normal.setActivated(true);
                satellite.setActivated(false);
                overlook.setActivated(false);
                MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).overlook(0).build();
                MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
                mBaiduMap.animateMapStatus(u);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }
        });

        overlook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlook.setActivated(true);
                normal.setActivated(false);
                satellite.setActivated(false);
                MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).overlook(OVERLOOK_ANGLE).build();
                MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
                mBaiduMap.animateMapStatus(u);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }
        });

        // 底图标注
        SwitchButton switchButton = (SwitchButton) contentView.findViewById(R.id.switch_btn);
        switchButton.setOnStatusChangeListener(new SwitchButton.OnStatusChangeListener() {
            @Override
            public void onChange(SwitchButton.STATUS status) {

                switch (status) {
                    case ON:
                        mBaiduMap.showMapPoi(true);
                        break;
                    case OFF:
                        mBaiduMap.showMapPoi(false);
                        break;
                }
            }
        });
    }

    @Override
    public void updateRoute() {
        searchRoute();
    }

    @Override
    public void fabBtnEvent() {
        final View changeExtendView = contentView.findViewById(R.id.change_extended_view);
        changeExtendView.setVisibility(View.VISIBLE);
    }

    @Override
    public void initMenu(final FloatingActionMenu menu) {
        menu.removeAllMenuButtons();

        // 卫星地图
        satellite = MenuUtil.initFAB(getActivity(), "卫星地图", R.drawable.ic_ab_back_icon);
        menu.addMenuButton(satellite);
        // 2D平面图
        normalMap = MenuUtil.initFAB(getActivity(), "2D平面图", R.drawable.ic_ab_back_icon);
        menu.addMenuButton(normalMap);
        // 3D俯视图
        overViewMap = MenuUtil.initFAB(getActivity(), "3D俯视图", R.drawable.ic_ab_back_icon);
        menu.addMenuButton(overViewMap);


        // 设置默认点击
        setCurrentShowType(currentShowType);

        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentShowType(SATELLITE);
            }
        });

        normalMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentShowType(NORMAL);
            }
        });

        overViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentShowType(OVERVIEW);
            }
        });
    }

    public void setCurrentShowType(int currentShowType) {
        this.currentShowType = currentShowType;

        resetShowType();

        MapStatus ms;
        MapStatusUpdate u;

        switch (currentShowType) {
            case SATELLITE:
                satellite.setImageResource(R.drawable.action_bar_check);
                satellite.setColorNormal(TTTApplication.getMyColor(R.color.colorAccentDark));

                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case NORMAL:
                normalMap.setImageResource(R.drawable.action_bar_check);
                normalMap.setColorNormal(TTTApplication.getMyColor(R.color.colorAccentDark));

                ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).overlook(0).build();
                u = MapStatusUpdateFactory.newMapStatus(ms);
                mBaiduMap.animateMapStatus(u);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case OVERVIEW:
                overViewMap.setImageResource(R.drawable.action_bar_check);
                overViewMap.setColorNormal(TTTApplication.getMyColor(R.color.colorAccentDark));

                ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).overlook(OVERLOOK_ANGLE).build();
                u = MapStatusUpdateFactory.newMapStatus(ms);
                mBaiduMap.animateMapStatus(u);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
        }
    }

    public void resetShowType() {
        satellite.setImageResource(R.drawable.ic_ab_back_icon);
        normalMap.setImageResource(R.drawable.ic_ab_back_icon);
        overViewMap.setImageResource(R.drawable.ic_ab_back_icon);

        satellite.setColorNormal(TTTApplication.getMyColor(R.color.colorAccent));
        normalMap.setColorNormal(TTTApplication.getMyColor(R.color.colorAccent));
        overViewMap.setColorNormal(TTTApplication.getMyColor(R.color.colorAccent));
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
            overlay = new CustomDrivingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }

        // stop loading view
        View viewContent = rootView.findViewById(R.id.loading_content_view);
        if (viewContent != null) {
            RotateLoading loadingView = (RotateLoading) rootView.findViewById(R.id.rotate_loading);
            loadingView.stop();
            viewContent.setVisibility(View.INVISIBLE);
        }
    }

    public class CustomDrivingRouteOverlay extends DrivingRouteOverlay {

        public CustomDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public int getLineColor() {
            return TTTApplication.getMyColor(R.color.colorPrimaryDark);
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
