package com.dean.travltotibet.activity;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.BaseRouteFragment;
import com.dean.travltotibet.fragment.RouteChartFragment;
import com.dean.travltotibet.fragment.RouteGuideFragment;
import com.dean.travltotibet.fragment.RouteMapFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.MenuUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DeanGuo on 7/19/15.
 * RouteActivity控制路线
 */
public class RouteActivity
        extends AppCompatActivity {

    private SlidingMenu mSlidingMenu;

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    // 当前计划
    private String planDate;
    private String planStart;
    private String planEnd;
    private String planDistance;
    private String planDescribe;

    private String planRank_Hard;
    private String planRank_View;
    private String planRank_Road;

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

    // 右上角计划按钮
    private TextView mMenuDate;

    // 悬浮按钮菜单
    private FloatingActionMenu mFloatingActionMenu;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_view);

        // 恢复数据
        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(Constants.ROUTE_ACTIVITY_CURRENT_PAGE_STATUS_KEY);
        }

        Intent intent = getIntent();
        if (intent != null) {
            routeName = intent.getStringExtra(IntentExtra.INTENT_ROUTE);
            routeType = intent.getStringExtra(IntentExtra.INTENT_ROUTE_TYPE);
            routePlanId = (int) intent.getLongExtra(IntentExtra.INTENT_ROUTE_PLAN_ID, 0);
            isForward = intent.getBooleanExtra(IntentExtra.INTENT_ROUTE_DIR, true);
        }

        // 设置路线信息
        currentRoute = TTTApplication.getDbHelper().getRouteInfo(routeName, routeType, isForward());

        initToolBar();
        initPlan(savedInstanceState);
        initMenu();
        initFabActionMenu();
        initViewPagerAndTab();
        // initFabBtn();


        // 跟新信息
        updateHeader(isRoute, planStart, planEnd, planDate, planDistance, planDescribe, planRank_Hard, planRank_View, planRank_Road);
    }

    /**
     * 初始化fab
     */
    private void initFabActionMenu() {
        mFloatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu_down);
        mFloatingActionMenu.setMenuButtonColorNormal(TTTApplication.getMyColor(R.color.colorAccent));
        mFloatingActionMenu.setMenuButtonColorPressed(TTTApplication.getMyColor(R.color.colorAccentDark));
        mFloatingActionMenu.getMenuIconView().setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_menu, Color.WHITE));
        // fab动画
        MenuUtil.createCustomAnimation(mFloatingActionMenu);
        // 点击外侧关闭
        mFloatingActionMenu.setClosedOnTouchOutside(true);
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        // date按钮
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.menu_date, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.RIGHT;
        actionBar.setCustomView(v, layoutParams);

        mMenuDate = (TextView) v.findViewById(R.id.header_menu_date);
        mMenuDate.setText("选择攻略");
        mMenuDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });
    }

    public void initMenu() {
        mSlidingMenu = new SlidingMenu(this);
        // 设置滑动方向
        mSlidingMenu.setMode(SlidingMenu.RIGHT);
        // 设置触摸屏幕的模式 全屏：TOUCHMODE_FULLSCREEN ；边缘：TOUCHMODE_MARGIN ；不打开：TOUCHMODE_NONE
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置是否淡入淡出
        //mSlidingMenu.setFadeEnabled(true);
        mSlidingMenu.setFadeDegree(0.35f);

        // 设置边缘阴影的宽度，通过dimens资源文件中的ID设置
        // mSlidingMenu.setShadowDrawable(R.drawable.shadow);

        // 设置偏移量。说明：设置menu全部打开后，主界面剩余部分与屏幕边界的距离，值写在dimens里面:60dp
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);

        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setMenu(R.layout.route_plan_fragment_layout);
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
            planRank_Hard = savedInstanceState.getString(Constants.ROUTE_ACTIVITY_PLAN_RANK_HARD_STATUS_KEY);
            planRank_View = savedInstanceState.getString(Constants.ROUTE_ACTIVITY_PLAN_RANK_VIEW_STATUS_KEY);
            planRank_Road = savedInstanceState.getString(Constants.ROUTE_ACTIVITY_PLAN_RANK_ROAD_STATUS_KEY);
        }
        // 默认总路线数据
        else {
            isRoute = true;
            planStart = currentRoute.getStart();
            planEnd = currentRoute.getEnd();
            planDate = currentRoute.getName();
            planDistance = currentRoute.getDistance();
            planDescribe = currentRoute.getDescribe();
            planRank_Hard = currentRoute.getRank_hard();
            planRank_View = currentRoute.getRank_view();
            planRank_Road = currentRoute.getRank_road();
        }
    }

    private void initViewPagerAndTab() {
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new ViewPageFragmentAdapter(getFragmentManager());

        // 为adapter添加数据
        mAdapter.add(RouteChartFragment.class, null, "海拔");
        mAdapter.add(RouteMapFragment.class, null, "地图");
        mAdapter.add(RouteGuideFragment.class, null, "攻略");
        mPager.setAdapter(mAdapter);

        mPager.setOffscreenPageLimit(1);
        // 设置页面变化监听
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // 更新路线
                updateRoute();

                // 初始化菜单
                initFABMenu();
            }
        });

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
        initFABMenu();
    }

    private void initFabBtn() {
        FloatingActionButton mFabButton = (FloatingActionButton) this.findViewById(R.id.floating_action_button);
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabBtnEvent();
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
    public void updateHeader(boolean isRoute, String start, String end, String date, String distance, String describe, String rank_hard, String rank_view, String rank_road) {
        this.isRoute = isRoute;
        planDate = date;
        planStart = start;
        planEnd = end;
        planDistance = distance;
        planDescribe = describe;
        planRank_Hard = rank_hard;
        planRank_View = rank_view;
        planRank_Road = rank_road;

        if (isRoute)
            mMenuDate.setText(getString(R.string.route_plan_title));
        else
            mMenuDate.setText(String.format(Constants.HEADER_DAY, date));

        getSupportActionBar().setTitle(String.format(Constants.HEADER_START_END, start, end));
        getSupportActionBar().setSubtitle(distance);

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
                // 用于切换fragment避免重新加载逻辑的标志
                fragment.setCurrentPlan(planStart, planEnd);
            }
        }
    }

    /**
     * 点击当前fragment的菜单
     */
    public void fabBtnEvent() {
        if (mAdapter.getAllFragments().size() > 0) {
            BaseRouteFragment fragment = (BaseRouteFragment) mAdapter.getFragment(mPager.getCurrentItem());
            if (fragment.isAdded() && fragment.isLoaded()) {
                fragment.fabBtnEvent();
            }
        }
    }

    /**
     * 根据不同fragment设置menu
     */
    public void initFABMenu() {
        if (mAdapter.getAllFragments().size() > 0) {
            BaseRouteFragment fragment = (BaseRouteFragment) mAdapter.getFragment(mPager.getCurrentItem());
            if (fragment.isAdded()) {
                mFloatingActionMenu.removeAllMenuButtons();
                fragment.initMenu(mFloatingActionMenu);
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
        outState.putString(Constants.ROUTE_ACTIVITY_PLAN_RANK_HARD_STATUS_KEY, planRank_Hard);
        outState.putString(Constants.ROUTE_ACTIVITY_PLAN_RANK_VIEW_STATUS_KEY, planRank_View);
        outState.putString(Constants.ROUTE_ACTIVITY_PLAN_RANK_ROAD_STATUS_KEY, planRank_Road);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 结束
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public FloatingActionMenu getFloatingActionMenu() {
        return mFloatingActionMenu;
    }

    public String getPlanDate() {
        return planDate;
    }

    public String getPlanStart() {
        return planStart;
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

    public SlidingMenu getSlidingMenu() {
        return mSlidingMenu;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }

    public void showMenu() {
        mSlidingMenu.showMenu();
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

    public String getPlanRank_Hard() {
        return planRank_Hard;
    }

    public String getPlanRank_View() {
        return planRank_View;
    }

    public String getPlanRank_Road() {
        return planRank_Road;
    }
}
