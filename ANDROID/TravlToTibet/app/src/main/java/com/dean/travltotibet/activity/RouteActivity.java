package com.dean.travltotibet.activity;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ChartPagerAdapter;
import com.dean.travltotibet.fragment.ChartFragment;
import com.dean.travltotibet.fragment.GuideRouteFragment;
import com.dean.travltotibet.fragment.MapFragment;
import com.dean.travltotibet.fragment.PlanFragment;
import com.dean.travltotibet.fragment.GuideFragment;
import com.dean.travltotibet.model.Place;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.StringUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class RouteActivity
        extends SlidingFragmentActivity {

    private ChartFragment chartFragment;

    private PlanFragment planFragment;

    private GuideFragment guideFragment;

    private MapFragment mapFragment;

    private GuideRouteFragment guideRouteFragment;

    private SlidingMenu slidingMenu;

    private ViewPager mPager;

    private ArrayList<Fragment> fragmentsList;

    private View headerView;

    private TextView heightTab;
    private TextView mapTab;
    private TextView guideTab;

    // 当前计划
    private String planDate;
    private String planStart;
    private String planEnd;
    private String planDistance;

    // 当前路线
    private Route currentRoute;

    // 当前线路名称
    private String routeName;

    // 当前线路类型
    private String routeType;

    // 当前路线计划的id
    private int routePlanId;

    // 当前是否向前，也就是正向反向 f/r
    private boolean isForwrad;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Intent intent = getIntent();
        if (intent != null) {
            routeName = intent.getStringExtra(Constants.INTENT_ROUTE_NAME);
            routeType = intent.getStringExtra(Constants.INTENT_ROUTE_TYPE);
            routePlanId = (int)intent.getLongExtra(Constants.INTENT_ROUTE_PLAN_ID, 0);
            isForwrad = intent.getBooleanExtra(Constants.INTENT_ROUTE_DIR, true);
        }

        initMenu();
        initHeader();
        initViewPager();

        // 设置路线信息
        currentRoute = TTTApplication.getDbHelper().getRouteInfo(routeName, routeType, isForwrad());
        // 跟新信息
        updateHeader(currentRoute.getStart(), currentRoute.getEnd(), currentRoute.getName(), currentRoute.getDistance());
    }

    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);

        fragmentsList = new ArrayList<Fragment>();

        chartFragment = ChartFragment.newInstance();
        mapFragment = MapFragment.newInstance();
        guideFragment = GuideFragment.newInstance();
        guideRouteFragment = GuideRouteFragment.newInstance();

        fragmentsList.add(chartFragment);
        fragmentsList.add(mapFragment);
        fragmentsList.add(guideRouteFragment);

        mPager.setAdapter(new ChartPagerAdapter(getFragmentManager(), fragmentsList));

        // 设置默认点击btn
        btnSelected(heightTab, 0);
        mPager.setCurrentItem(0);
    }

    private void initHeader() {

        headerView = findViewById(R.id.chart_header);

        // 初始化header menu两侧按钮
        initHeaderButton();

        heightTab = (TextView) findViewById(R.id.bt1);
        mapTab = (TextView) findViewById(R.id.bt2);
        guideTab = (TextView) findViewById(R.id.bt3);

        heightTab.setOnClickListener(new TabBtnOnClickListener(0));
        mapTab.setOnClickListener(new TabBtnOnClickListener(1));
        guideTab.setOnClickListener(new TabBtnOnClickListener(2));
    }

    public class TabBtnOnClickListener implements View.OnClickListener {
        private int index = 0;

        public TabBtnOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {

            // 每次点击不同按钮式重置所有按钮颜色和背景
            resetColorAndBackground(index);

            // 设置新颜色
            btnSelected(v, index);

            // 变换fragment
            mPager.setCurrentItem(index);
        }
    }

    /**
     * 选中btn变换颜色和背景
     */
    public void btnSelected(View v, int index) {

        v.setBackgroundResource(R.drawable.btn_write_background);

        switch (index) {
            case 0:
                ((TextView) v).setTextColor(getResources().getColor(R.color.light_blue));
                break;
            case 1:
                ((TextView) v).setTextColor(getResources().getColor(R.color.light_green));
                break;
            case 2:
                ((TextView) v).setTextColor(getResources().getColor(R.color.light_orange));
                break;
            default:
                break;
        }
    }

    /**
     * 每次点击不同按钮式重置所有按钮颜色和背景
     */
    private void resetColorAndBackground(int index) {
        switch (index) {
            case 0:
                headerView.setBackgroundResource(R.color.light_blue);

                heightTab.setBackgroundResource(R.drawable.btn_blue_background);
                mapTab.setBackgroundResource(R.drawable.btn_blue_background);
                guideTab.setBackgroundResource(R.drawable.btn_blue_background);

                break;
            case 1:
                headerView.setBackgroundResource(R.color.light_green);

                heightTab.setBackgroundResource(R.drawable.btn_green_background);
                mapTab.setBackgroundResource(R.drawable.btn_green_background);
                guideTab.setBackgroundResource(R.drawable.btn_green_background);

                break;
            case 2:
                headerView.setBackgroundResource(R.color.light_orange);

                heightTab.setBackgroundResource(R.drawable.btn_purple_background);
                mapTab.setBackgroundResource(R.drawable.btn_purple_background);
                guideTab.setBackgroundResource(R.drawable.btn_purple_background);

                break;
            default:
                break;
        }

        heightTab.setTextColor(getResources().getColor(R.color.white_background));
        mapTab.setTextColor(getResources().getColor(R.color.white_background));
        guideTab.setTextColor(getResources().getColor(R.color.white_background));
    }

    /**
     * 初始化侧滑菜单
     */
    private void initMenu() {

        // route fragment
        Fragment rightFragment = getFragmentManager().findFragmentById(R.id.routeFragment);
        if (rightFragment == null) {
            planFragment = new PlanFragment();
            getFragmentManager().beginTransaction().replace(R.id.routeFragment, planFragment).commit();
        } else {
            planFragment = (PlanFragment) rightFragment;
        }

        // 设置主菜单
        setBehindContentView(R.layout.route_fragment_layout);
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
        //slidingMenu.setMenu(R.layout.menu_layout);

//        // slidingMenu.setBehindScrollScale(1.0f);
//        slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow);
//        //设置右边（二级）侧滑菜单
//        slidingMenu.setSecondaryMenu(R.layout.route_fragment_layout);

        // 设置滑动时actionbar是否跟着移动，SLIDING_WINDOW=跟着移动;SLIDING_CONTENT=不跟着移动
        //menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
    }

    /**
     * 初始化header左右两侧按钮
     */
    private void initHeaderButton() {
        TextView menuBtn = (TextView) this.findViewById(R.id.menu_btn);
        TextView routeBtn = (TextView) this.findViewById(R.id.header_menu_date);

        menuBtn.setOnClickListener(new View.OnClickListener() {
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
    }

    /**
     * 更新标题栏文字
     *
     * @param start
     * @param end
     * @param date
     */
    public void updateHeader(String start, String end, String date, String distance) {
        planDate = date;
        planStart = start;
        planEnd = end;
        planDistance = distance;

        TextView header_date = (TextView) this.findViewById(R.id.header_menu_date);
        TextView menuBtn = (TextView) this.findViewById(R.id.menu_btn);
        TextView header_distance = (TextView) this.findViewById(R.id.header_distance);

        header_date.setText(date);
        menuBtn.setText(String.format(Constants.HEADER_START_END, start, end));
        header_distance.setText(String.format(Constants.HEADER_DISTANCE, distance));

        updateToAll();
    }

    /**
     * 更新标题栏文字
     *
     * @param currentRoute
     */
    public void updateHeader(Route currentRoute) {
        planDate = currentRoute.getName();
        if (isForwrad) {
            planStart = currentRoute.getStart();
            planEnd = currentRoute.getEnd();
        } else {
            planStart = currentRoute.getEnd();
            planEnd = currentRoute.getStart();
        }
        planDistance = currentRoute.getDistance();

        TextView header_date = (TextView) this.findViewById(R.id.header_menu_date);
        TextView menuBtn = (TextView) this.findViewById(R.id.menu_btn);

        header_date.setText(planDate);
        menuBtn.setText(planStart + "-" + planEnd);

        updateToAll();
    }

    public void updateToAll() {
        // update chartFragment
        if (chartFragment.isAdded()) {
            chartFragment.updateRoute();
        }

        // update chartFragment
        if (mapFragment.isAdded()) {
            mapFragment.updateRoute();
        }

        // update chartFragment
        if (guideRouteFragment.isAdded()) {
            guideRouteFragment.updateRoute();
        }
    }

    public String getPlanDate() {
        return planDate;
    }

    public String getPlanStart() {
        return planStart;
    }

    public String getPlanEnd() {
        return planEnd;
    }

    public String getPlanDistance() {
        return planDistance;
    }

    public ChartFragment getChartFragment() {
        return chartFragment;
    }

    public PlanFragment getPlanFragment() {
        return planFragment;
    }

    public GuideFragment getGuideFragment() {
        return guideFragment;
    }

    public MapFragment getMapFragment() {
        return mapFragment;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }

    @Override
    public SlidingMenu getSlidingMenu() {
        return slidingMenu;
    }

    public void showMenu() {
        slidingMenu.showMenu();
    }

    public boolean isForwrad() {
        return isForwrad;
    }

    public void setIsForwrad(boolean isForwrad) {
        this.isForwrad = isForwrad;
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
}
