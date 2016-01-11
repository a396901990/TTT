package com.dean.travltotibet.activity;

import com.dean.greendao.Plan;
import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.BaseRouteFragment;
import com.dean.travltotibet.fragment.RouteChartFragment;
import com.dean.travltotibet.fragment.RouteDetailFragment;
import com.dean.travltotibet.fragment.RouteGuideFragment;
import com.dean.travltotibet.fragment.RouteMapFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.ui.numberprogressbar.RatingBar;
import com.dean.travltotibet.ui.numberprogressbar.RatingView;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.MenuUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by DeanGuo on 7/19/15.
 * RouteActivity控制路线
 */
public class RouteActivity
        extends BaseActivity {

    private SlidingMenu mSlidingMenu;

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    // 当前路线
    private Route currentRoute;

    // 当前计划
    private Plan currentPlan;

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
    private int currentPage;

    private TextView mHeaderPlan;

    // 悬浮按钮菜单
    private FloatingActionMenu mFloatingActionMenu;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_view);

        // 恢复数据
        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(Constants.ROUTE_ACTIVITY_CURRENT_PAGE_STATUS_KEY);
            isRoute = savedInstanceState.getBoolean(Constants.ROUTE_ACTIVITY_IS_ROUTE);
        } else {
            currentPage = 0;
            isRoute = true;
        }

        // 获取当前路线信息
        Intent intent = getIntent();
        if (intent != null) {
            routeName = intent.getStringExtra(IntentExtra.INTENT_ROUTE);
            routeType = intent.getStringExtra(IntentExtra.INTENT_ROUTE_TYPE);
            routePlanId = (int) intent.getLongExtra(IntentExtra.INTENT_ROUTE_PLAN_ID, 0);
            isForward = intent.getBooleanExtra(IntentExtra.INTENT_ROUTE_DIR, true);
        }
        currentRoute = TTTApplication.getDbHelper().getRouteInfo(routeName, routeType, isForward());

        initToolBar();
        initMenu();
        initFabActionMenu();
        initViewPagerAndTab();
        // initFabBtn();

        // 跟新信息
        updateHeader(isRoute, currentPlan);
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
        setUpToolBar(toolbar);

        mHeaderPlan = (TextView) this.findViewById(R.id.header_plan_text);
        mHeaderPlan.setOnClickListener(new View.OnClickListener() {
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

    private void initViewPagerAndTab() {
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new ViewPageFragmentAdapter(getFragmentManager());

        // 为adapter添加数据
        mAdapter.add(RouteChartFragment.class, null, getString(R.string.elevation_text));
        mAdapter.add(RouteMapFragment.class, null, getString(R.string.map_text));
        mAdapter.add(RouteDetailFragment.class, null, getString(R.string.guide_text));
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
     */
    public void updateHeader(boolean isRoute, Plan plan) {
        this.isRoute = isRoute;

        // 设置当前plan
        setCurrentPlan(plan);

        TextView headerStartEnd = (TextView) this.findViewById(R.id.header_plan_start_end);
        TextView headerDistance = (TextView) this.findViewById(R.id.header_plan_distance);
        TextView headerDate = (TextView) this.findViewById(R.id.header_plan_day);
        RatingView ratingView = (RatingView) this.findViewById(R.id.rating_view);
        ratingView.removeAll();

        // 根据总览还是计划分别设置header view
        if (isRoute) {
            mHeaderPlan.setText(String.format(Constants.HEADER_PLAN_DAY, currentRoute.getDay()));

            ratingView.addRatingBar(new RatingBar(Integer.parseInt(getCurrentRoute().getRank_hard()), getString(R.string.rating_hard)));
            ratingView.addRatingBar(new RatingBar(Integer.parseInt(getCurrentRoute().getRank_view()), getString(R.string.rating_view)));
            ratingView.addRatingBar(new RatingBar(Integer.parseInt(getCurrentRoute().getRank_road()), getString(R.string.rating_road)));

            headerStartEnd.setText(String.format(Constants.HEADER_START_END, getCurrentStart(), getCurrentEnd()));
            headerDistance.setText(currentRoute.getDistance());
//            headerDate.setText(String.format(Constants.HEADER_PLAN_DAY, currentRoute.getDay()));
        } else {
            mHeaderPlan.setText(String.format(Constants.HEADER_DAY, currentPlan.getDay()));

            ratingView.addRatingBar(new RatingBar(Integer.parseInt(getCurrentPlan().getRank_hard()), getString(R.string.rating_hard)));
            ratingView.addRatingBar(new RatingBar(Integer.parseInt(getCurrentPlan().getRank_view()), getString(R.string.rating_view)));
            ratingView.addRatingBar(new RatingBar(Integer.parseInt(getCurrentPlan().getRank_road()), getString(R.string.rating_road)));

            headerStartEnd.setText(String.format(Constants.HEADER_START_END, getCurrentStart(), getCurrentEnd()));
            headerDistance.setText(currentPlan.getDistance());
//            headerDate.setText(currentPlan.getHours());
        }

        ratingView.show();
        updateRoute();
    }

    /**
     * 更新当前fragment的路线
     */
    public void updateRoute() {
        if (mAdapter.getAllFragments().size() > 0) {
            BaseRouteFragment fragment = (BaseRouteFragment) mAdapter.getFragment(mPager.getCurrentItem());
            if (fragment.isAdded() && fragment.isLoaded() && !fragment.isCurrentPlan(getCurrentStart(), getCurrentEnd())) {
                fragment.updateRoute();
                // 用于切换fragment避免重新加载逻辑的标志
                fragment.setCurrentPlan(getCurrentStart(), getCurrentEnd());
            }
        }
    }

    public String getCurrentStart() {
        if (isRoute()) {
            return currentRoute.getStart();
        } else {
            return currentPlan.getStart();
        }
    }

    public String getCurrentEnd() {
        if (isRoute()) {
            return currentRoute.getEnd();
        } else {
            return currentPlan.getEnd();
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

    public Plan getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(Plan currentPlan) {
        this.currentPlan = currentPlan;
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }
}
