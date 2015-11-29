package com.dean.travltotibet.activity;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.BaseRouteFragment;
import com.dean.travltotibet.fragment.RouteChartFragment;
import com.dean.travltotibet.fragment.RouteGuideFragment;
import com.dean.travltotibet.fragment.RouteMapFragment;
import com.dean.travltotibet.fragment.RoutePlanFragment;
import com.dean.travltotibet.util.CompatHelper;
import com.dean.travltotibet.util.Constants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by DeanGuo on 7/19/15.
 * RouteActivity控制路线
 */
public class RouteActivity
        extends SlidingFragmentActivity {

    private final static int PAGE_HEIGHT = 0;
    private final static int PAGE_MAP = 1;
    private final static int PAGE_GUIDE = 2;

    private Activity activity;

    private RoutePlanFragment planFragment;

    private SlidingMenu slidingMenu;

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private View headerView;

    private TextView heightTab;
    private TextView mapTab;
    private TextView guideTab;

    // 当前计划
    private String planDate;
    private String planStart;
    private String planEnd;
    private String planDistance;
    private String planDescribe;
    private String planRank;

    // 当前路线
    private Route currentRoute;

    // 当前线路名称
    private String routeName;

    // 当前线路类型
    private String routeType;

    // 当前路线计划的id
    private int routePlanId;

    // 当前路线计划的days
    private boolean isRoute;

    // 当前是否向前，也就是正向反向 f/r
    private boolean isForward;

    // mPage当前页码
    private int currentPage = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_main_layout);
        activity = this;

        // 恢复数据
        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(Constants.ROUTE_ACTIVITY_CURRENT_PAGE_STATUS_KEY);
        }

        Intent intent = getIntent();
        if (intent != null) {
            routeName = intent.getStringExtra(Constants.INTENT_ROUTE);
            routeType = intent.getStringExtra(Constants.INTENT_ROUTE_TYPE);
            routePlanId = (int) intent.getLongExtra(Constants.INTENT_ROUTE_PLAN_ID, 0);
            isForward = intent.getBooleanExtra(Constants.INTENT_ROUTE_DIR, true);
        }

        // 设置路线信息
        currentRoute = TTTApplication.getDbHelper().getRouteInfo(routeName, routeType, isForward());
        initPlan(savedInstanceState);
        initPlanMenu();
        initHeader();
        initViewPager();

        // 跟新信息
        updateHeader(isRoute, planStart, planEnd, planDate, planDistance, planRank, planDescribe);
    }

    private void initPlan(Bundle savedInstanceState) {
        // 恢复数据
        if (savedInstanceState != null) {
            isRoute = savedInstanceState.getBoolean(Constants.ROUTE_ACTIVITY_IS_ROUTE);

            planStart = savedInstanceState.getString(Constants.ROUTE_ACTIVITY_PLAN_START_STATUS_KEY);
            planEnd = savedInstanceState.getString(Constants.ROUTE_ACTIVITY_PLAN_END_STATUS_KEY);
            planDate = savedInstanceState.getString(Constants.ROUTE_ACTIVITY_PLAN_DATE_STATUS_KEY);
            planDistance = savedInstanceState.getString(Constants.ROUTE_ACTIVITY_PLAN_DISTANCE_STATUS_KEY);
            planDescribe = savedInstanceState.getString(Constants.ROUTE_ACTIVITY_PLAN_DESCRIBE_STATUS_KEY);
            planRank = savedInstanceState.getString(Constants.ROUTE_ACTIVITY_PLAN_RANK_STATUS_KEY);
        }
        // 默认总路线数据
        else {
            isRoute = true;
            planStart = currentRoute.getStart();
            planEnd = currentRoute.getEnd();
            planDate = currentRoute.getName();
            planDistance = currentRoute.getDistance();
            planDescribe = currentRoute.getDescribe();
            planRank = currentRoute.getRank();
        }
    }

    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        mAdapter = new ViewPageFragmentAdapter(getFragmentManager());

        // 为adapter添加数据
        mAdapter.add(RouteChartFragment.class, null);
        mAdapter.add(RouteMapFragment.class, null);
        mAdapter.add(RouteGuideFragment.class, null);
        mPager.setAdapter(mAdapter);

        mPager.setOffscreenPageLimit(1);
        // 设置页面变化监听
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // 每次点击不同按钮式重置所有按钮颜色和背景
                resetColorAndBackground();

                // 设置新颜色
                btnSelected(position);

                // 更新路线
                updateRoute();
            }
        });

        // 设置当前页面，设置默认点击
        btnSelected(currentPage);
        mPager.setCurrentItem(currentPage);
    }

    private void initHeader() {

        headerView = findViewById(R.id.chart_header);

        // 初始化header menu两侧按钮
        initHeaderButton();

        heightTab = (TextView) findViewById(R.id.height_tab);
        mapTab = (TextView) findViewById(R.id.map_tab);
        guideTab = (TextView) findViewById(R.id.guide_tab);

        heightTab.setOnClickListener(new TabBtnOnClickListener(PAGE_HEIGHT));
        mapTab.setOnClickListener(new TabBtnOnClickListener(PAGE_MAP));
        guideTab.setOnClickListener(new TabBtnOnClickListener(PAGE_GUIDE));
    }

    /**
     * Tab按钮监听
     */
    public class TabBtnOnClickListener implements View.OnClickListener {
        private int index = 0;

        public TabBtnOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // 变换fragment
            mPager.setCurrentItem(index);
        }
    }

    /**
     * 选中btn变换颜色和背景
     */
    public void btnSelected(int index) {

        switch (index) {
            case PAGE_HEIGHT:
                heightTab.setBackgroundResource(R.drawable.header_border_activited);
                heightTab.setTextColor(getResources().getColor(R.color.white));
                break;
            case PAGE_MAP:
                mapTab.setBackgroundResource(R.drawable.header_border_activited);
                mapTab.setTextColor(getResources().getColor(R.color.white));
                break;
            case PAGE_GUIDE:
                guideTab.setBackgroundResource(R.drawable.header_border_activited);
                guideTab.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }
    }

    /**
     * 每次点击不同按钮式重置所有按钮颜色和背景
     */
    private void resetColorAndBackground() {
//        headerView.setBackgroundResource(R.color.dark_blue);
        heightTab.setBackgroundResource(R.color.white);
        mapTab.setBackgroundResource(R.color.white);
        guideTab.setBackgroundResource(R.color.white);
        heightTab.setTextColor(getResources().getColor(R.color.half_dark_gray));
        mapTab.setTextColor(getResources().getColor(R.color.half_dark_gray));
        guideTab.setTextColor(getResources().getColor(R.color.half_dark_gray));
    }

    /**
     * 初始化侧滑菜单
     */
    private void initPlanMenu() {

        // plan fragment
        Fragment rightFragment = getFragmentManager().findFragmentById(R.id.routePlanFragment);
        if (rightFragment == null) {
            planFragment = new RoutePlanFragment();
            getFragmentManager().beginTransaction().replace(R.id.routePlanFragment, planFragment).commit();
        } else {
            planFragment = (RoutePlanFragment) rightFragment;
        }

        // 设置主菜单
        setBehindContentView(R.layout.route_plan_fragment_layout);
        // 初始化menu
        slidingMenu = super.getSlidingMenu();

        // 设置触摸屏幕的模式 全屏：TOUCHMODE_FULLSCREEN ；边缘：TOUCHMODE_MARGIN ；不打开：TOUCHMODE_NONE
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        // 设置是否淡入淡出
        slidingMenu.setFadeEnabled(true);
        // 设置淡入淡出的值，只在setFadeEnabled设置为true时有效
        slidingMenu.setFadeDegree(0.5f);
        // 设置偏移量。说明：设置menu全部打开后，主界面剩余部分与屏幕边界的距离，值写在dimens里面:60dp
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置阴影的图片
        //slidingMenu.setShadowDrawable(R.drawable.shadow);
        // 设置边缘阴影的宽度，通过dimens资源文件中的ID设置
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动方向
        slidingMenu.setMode(SlidingMenu.RIGHT);
        //slidingMenu.setMenu(R.layout.menu_fragment_layout);

//        // slidingMenu.setBehindScrollScale(1.0f);
//        slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow);
//        //设置右边（二级）侧滑菜单
//        slidingMenu.setSecondaryMenu(R.layout.route_plan_fragment_layout);

        // 设置滑动时actionbar是否跟着移动，SLIDING_WINDOW=跟着移动;SLIDING_CONTENT=不跟着移动
        //menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
    }

    /**
     * 初始化header按钮
     */
    private void initHeaderButton() {
        // 返回
        View backBtn = this.findViewById(R.id.back_btn);
        // 选择plan
        View routeBtn = this.findViewById(R.id.header_menu_date);
        // 旋转
        View rotateBtn = this.findViewById(R.id.rotate_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });

        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompatHelper.rotateScreen(activity);
            }
        });
    }

    /**
     * 更新标题栏文字
     *
     * @param start
     * @param end
     * @param date
     */
    public void updateHeader(boolean isRoute, String start, String end, String date, String distance, String rank, String describe) {
        this.isRoute = isRoute;
        planDate = date;
        planStart = start;
        planEnd = end;
        planDistance = distance;
        planRank = rank;
        planDescribe = describe;

        TextView header_date = (TextView) this.findViewById(R.id.header_menu_date);
        TextView menuBtn = (TextView) this.findViewById(R.id.back_btn);
        TextView header_distance = (TextView) this.findViewById(R.id.header_distance);

        if (isRoute)
            header_date.setText(getString(R.string.route_plan_title));
        else
            header_date.setText(String.format(Constants.HEADER_DAY, date));

        menuBtn.setText(String.format(Constants.HEADER_START_END, start, end));
        header_distance.setText(String.format(Constants.HEADER_DISTANCE, distance));

        updateRoute();
    }

    /**
     * 更新当前fragment的路线
     */
    public void updateRoute() {
        if (mAdapter.getAllFragments().size() > 0) {
            BaseRouteFragment fragment = (BaseRouteFragment) mAdapter.getFragment(mPager.getCurrentItem());
            if (fragment.isAdded() && fragment.isLoaded() && !fragment.isCurrentPlan(planStart, planEnd)) {
                fragment.updateRoute();
                fragment.setCurrentPlan(planStart, planEnd);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // current page
        outState.putInt(Constants.ROUTE_ACTIVITY_CURRENT_PAGE_STATUS_KEY, currentPage);

        // isRoute
        outState.putBoolean(Constants.ROUTE_ACTIVITY_IS_ROUTE, isRoute);

        // plan status
        outState.putString(Constants.ROUTE_ACTIVITY_PLAN_START_STATUS_KEY, planStart);
        outState.putString(Constants.ROUTE_ACTIVITY_PLAN_END_STATUS_KEY, planEnd);
        outState.putString(Constants.ROUTE_ACTIVITY_PLAN_DATE_STATUS_KEY, planDate);
        outState.putString(Constants.ROUTE_ACTIVITY_PLAN_DISTANCE_STATUS_KEY, planDistance);
        outState.putString(Constants.ROUTE_ACTIVITY_PLAN_DESCRIBE_STATUS_KEY, planDescribe);
        outState.putString(Constants.ROUTE_ACTIVITY_PLAN_RANK_STATUS_KEY, planRank);
    }

    @Override
    public SlidingMenu getSlidingMenu() {
        return slidingMenu;
    }

    public String getPlanDate() {
        return planDate;
    }

    public String getPlanStart() {
        return planStart;
    }


    public String getPlanRank() {
        return planRank;
    }

    public String getPlanDescribe() {
        return planDescribe;
    }

    public String getPlanEnd() {
        return planEnd;
    }

    public String getPlanDistance() {
        return planDistance;
    }

    public RoutePlanFragment getPlanFragment() {
        return planFragment;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }

    public void showMenu() {
        slidingMenu.showMenu();
    }

    public boolean isForward() {
        return isForward;
    }

    public void setIsForwrad(boolean isForwrad) {
        this.isForward = isForwrad;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getRoutePlanId() {
        return routePlanId;
    }

    public void setRoutePlanId(int routePlanId) {
        this.routePlanId = routePlanId;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public boolean isRoute() {
        return isRoute;
    }
}
