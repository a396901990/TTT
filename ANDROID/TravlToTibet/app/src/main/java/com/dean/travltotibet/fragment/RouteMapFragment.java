package com.dean.travltotibet.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.dean.greendao.Geocode;
import com.dean.mapapi.overlayutil.BikingRouteOverlay;
import com.dean.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.dean.mapapi.overlayutil.LineOverlay;
import com.dean.mapapi.overlayutil.OverlayManager;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.RotateLoading;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.util.MenuUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteMapFragment extends BaseRouteFragment implements BaiduMap.OnMapClickListener {

    private final static int SATELLITE = 0;
    private final static String SATELLITE_TITLE = "卫星地图";

    private final static int NORMAL = 1;
    private final static String NORMAL_TITLE = "2D平面图";

    private final static int OVERVIEW = 2;
    private final static String OVERVIEW_TITLE = "3D俯视图";

    private final static String DISPLAY_MORE_TITLE = "显示山峰信息";

    private final static String HIDDEN_MORE_TITLE = "隐藏山峰信息";

    private int currentShowType = 1;  // 默认显示2d平面

    private View rootView;

    private View contentView;

    private RouteActivity routeActivity;

    private MapView mMapView = null;

    private BaiduMap mBaiduMap;

    private InfoWindow mInfoWindow;

    private LineOverlay overlay;

    // 默认俯视角度
    private final static int OVERLOOK_ANGLE = -45;

    private com.dean.travltotibet.ui.fab.FloatingActionButton satellite;
    private com.dean.travltotibet.ui.fab.FloatingActionButton normalMap;
    private com.dean.travltotibet.ui.fab.FloatingActionButton overViewMap;
    private com.dean.travltotibet.ui.fab.FloatingActionButton showMore;

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
        initMapView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_content_frame, container, false);
        return rootView;
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
    }

    @Override
    protected void onLoading() {
        drawRoute();
    }

    @Override
    protected void onLoadFinished() {
        ((ViewGroup) rootView).addView(contentView);
    }

    public void drawRoute() {
        mBaiduMap.clear();

        // start loading view
        showLoading();

        // set overlay
        overlay = new LineOverlay(mBaiduMap);
        List<Geocode> mGeocodes = TTTApplication.getDbHelper().getGeocodeListWithNameAndRoute(routeActivity.getRouteName(), routeActivity.getCurrentStart(), routeActivity.getCurrentEnd(), routeActivity.isForward());
        overlay.setData(mGeocodes);

        // 当不显示更多和路线时不显示更多
        boolean isShowMore = showMore.getTag() != null ? (boolean) showMore.getTag() : true;
        boolean isPlan = !routeActivity.isRoute();
        overlay.setShowMore(isPlan && isShowMore);

        // add to map
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                overlay.addToMap();
                overlay.zoomToSpan();
                dismissLoading();
            }
        }, 1000);
    }

    /**
     * 添加覆盖物点击事件
     */
    private void initMarkerClickEvent() {

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {

                LayoutInflater layoutInflater = LayoutInflater.from(routeActivity);
                ViewGroup showLayout = (ViewGroup) layoutInflater.inflate(R.layout.map_show_detail_layout, null);

                InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                    public void onInfoWindowClick() {
                        mBaiduMap.hideInfoWindow();
                    }
                };

                LatLng ll = marker.getPosition();
                Bundle extraInfo = marker.getExtraInfo();
                if (extraInfo != null) {

                    // title
                    TextView title = (TextView) showLayout.findViewById(R.id.map_show_title);
                    title.setText(extraInfo.getString(LineOverlay.GEO_NAME));

                    // height
                    TextView height = (TextView) showLayout.findViewById(R.id.map_show_height);
                    height.setText(extraInfo.getString(LineOverlay.GEO_HEIGHT));

                    // milestone
                    TextView mile = (TextView) showLayout.findViewById(R.id.map_show_mile);
                    mile.setText(extraInfo.getString(LineOverlay.GEO_MILESTONE));

                    // show pop window
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(showLayout), ll, -47, listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });
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
    }

    @Override
    public void updateRoute() {
        drawRoute();
    }

    @Override
    public void fabBtnEvent() {
    }

    @Override
    public void initMenu(final FloatingActionMenu menu) {

        super.initMenu(menu);


        // 显示更多(默认显示,但提示隐藏)
        showMore = MenuUtil.getFAB(getActivity(), HIDDEN_MORE_TITLE, GoogleMaterial.Icon.gmd_gps_off);
        showMore.setTag(true);
        menu.addMenuButton(showMore);

        // 卫星地图
        satellite = MenuUtil.initFAB(getActivity(), SATELLITE_TITLE, R.drawable.ic_ab_back_icon);
        menu.addMenuButton(satellite);
        // 2D平面图
        normalMap = MenuUtil.initFAB(getActivity(), NORMAL_TITLE, R.drawable.ic_ab_back_icon);
        menu.addMenuButton(normalMap);
        // 3D俯视图
        overViewMap = MenuUtil.initFAB(getActivity(), OVERVIEW_TITLE, R.drawable.ic_ab_back_icon);
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

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShow = (boolean) showMore.getTag();
                // 如果当前是显示,点击后则隐藏,还应该是显示的提示
                if (isShow) {
                    showMore.setTag(false);
                    showMore.setLabelText(DISPLAY_MORE_TITLE);
                    showMore.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_gps_fixed, Color.WHITE));
                } else {
                    showMore.setTag(true);
                    showMore.setLabelText(HIDDEN_MORE_TITLE);
                    showMore.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_gps_off, Color.WHITE));
                }
                // 重画路线
                drawRoute();
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

    public void showLoading() {
        View viewContent = rootView.findViewById(R.id.loading_content_view);
        if (viewContent != null) {
            viewContent.setVisibility(View.VISIBLE);
            RotateLoading loadingView = (RotateLoading) rootView.findViewById(R.id.rotate_loading);
            loadingView.start();
        }
    }

    public void dismissLoading() {
        // stop loading view
        View viewContent = rootView.findViewById(R.id.loading_content_view);
        if (viewContent != null) {
            RotateLoading loadingView = (RotateLoading) rootView.findViewById(R.id.rotate_loading);
            loadingView.stop();
            viewContent.setVisibility(View.INVISIBLE);
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
        mMapView.onDestroy();
        super.onDestroy();
    }

}
