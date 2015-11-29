package com.dean.travltotibet.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.HomeRecentFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class HomeActivity extends SlidingFragmentActivity {

    private String route;

    private String routeName;

    private SlidingMenu slidingMenu;

    private HomeRecentFragment homeRecentFragment;

    private HomePageAdapter mAdapter;
    private ViewPager mPager;
    private PagerSlidingTabStrip mTabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.dark_blue);
//        }
        setContentView(R.layout.home_view);

        initHomeTab();
        checkForUpdate();
        initMenu();
    }

    private void initHomeTab() {
        mAdapter = new HomePageAdapter(getFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pagerContent);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(3);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.home_tabs, null);
        assert v != null;
        v.setLayoutParams(new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        mTabs = (PagerSlidingTabStrip) v.findViewById(R.id.home_tabs);
        mTabs.setViewPager(mPager);

        // 字体颜色大小
        mTabs.setTextSize(45);
        mTabs.setTextColor(TTTApplication.getColor(R.color.white));
        // 指示器颜色，大小
        mTabs.setIndicatorColor(TTTApplication.getColor(R.color.white));
        mTabs.setUnderlineColor(TTTApplication.getColor(android.R.color.transparent));
        mTabs.setIndicatorHeight(10);
        mTabs.setUnderlineHeight(10);
        // 是否拉伸
        mTabs.setShouldExpand(false);
        // tab间的分割线 -- 透明表示没有
        mTabs.setDividerColor(android.R.color.transparent);

        // 设置actionbar
        final ActionBar mActionBar = getActionBar();
        mActionBar.setTitle("");
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(v);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void checkForUpdate() {
        // only wifi
        BmobUpdateAgent.update(this);
    }

    private void initMenu() {

        Fragment recentFragment = getFragmentManager().findFragmentById(R.id.recentFragment);
        if (recentFragment == null) {
            homeRecentFragment = HomeRecentFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.recentFragment, homeRecentFragment).commit();
        } else {
            homeRecentFragment = (HomeRecentFragment) recentFragment;
        }

        // 设置主菜单
        setBehindContentView(R.layout.recent_fragment_layout);
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
//        slidingMenu.setSecondaryMenu(R.layout.recent_fragment_layout);

        // 设置滑动时actionbar是否跟着移动，SLIDING_WINDOW=跟着移动;SLIDING_CONTENT=不跟着移动
        // menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_history) {
            homeRecentFragment.updateRecentData();
            slidingMenu.showMenu();
        } else if (id == R.id.action_setting) {
            Intent intent = new Intent(this, HomeSettingActivity.class);
            startActivity(intent);
        } else if (id == android.R.id.home) {
            slidingMenu.showMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

}
