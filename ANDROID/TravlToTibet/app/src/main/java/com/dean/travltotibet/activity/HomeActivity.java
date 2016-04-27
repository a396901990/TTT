package com.dean.travltotibet.activity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.HomePageAdapter;
import com.dean.travltotibet.fragment.BaseRatingCommentFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import cn.bmob.v3.update.BmobUpdateAgent;
/**
 * Created by DeanGuo on 9/30/15.
 */
public class HomeActivity extends BaseActivity {

    private String route;

    private String routeName;

    private HomePageAdapter mAdapter;
    private ViewPager mPager;
    private PagerSlidingTabStrip mTabs;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private View toolbarShadow;

    private SlidingMenu mSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
        setUpToolBar();
        setUpView();
        setUpHomeTab();
        setUpNavigationDrawer();
        checkForUpdate();
        initMenu();
    }

    private void initMenu() {

        mSlidingMenu = new SlidingMenu(this);
        // 设置滑动方向
        mSlidingMenu.setMode(SlidingMenu.RIGHT);
        // 设置触摸屏幕的模式 全屏：TOUCHMODE_FULLSCREEN ；边缘：TOUCHMODE_MARGIN ；不打开：TOUCHMODE_NONE
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置是否淡入淡出
        mSlidingMenu.setFadeEnabled(true);
        mSlidingMenu.setFadeDegree(0.35f);

        // 设置边缘阴影的宽度，通过dimens资源文件中的ID设置
         mSlidingMenu.setShadowDrawable(R.drawable.shadowright);

        // 设置偏移量。说明：设置menu全部打开后，主界面剩余部分与屏幕边界的距离，值写在dimens里面:60dp
        mSlidingMenu.setBehindOffsetRes(R.dimen.home_slidingmenu_offset);

        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setMenu(R.layout.sliding_recent_fragment_layout);
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpView() {
        toolbarShadow = findViewById(R.id.toolbar_shadow);
        // 设置viewpager
        mAdapter = new HomePageAdapter(getFragmentManager());
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(1);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // 第二页
                if (position == 1) {
                    mTabs.setIndicatorHeight(0);
                    mTabs.invalidate();
                    toolbarShadow.setVisibility(View.GONE);
                } else {
                    mTabs.setIndicatorHeight(6);
                    mTabs.invalidate();
                    toolbarShadow.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setUpHomeTab() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.home_tabs, null);

        mTabs = (PagerSlidingTabStrip) v.findViewById(R.id.home_tabs);
        mTabs.setViewPager(mPager);

        // 字体颜色大小
        mTabs.setBackgroundColor(TTTApplication.getMyColor(R.color.colorPrimary));
        mTabs.setActivateTextColor(TTTApplication.getMyColor(R.color.white));
        mTabs.setDeactivateTextColor(TTTApplication.getMyColor(R.color.half_white));
        // 指示器颜色，大小
        mTabs.setIndicatorColor(TTTApplication.getMyColor(R.color.white));
        mTabs.setUnderlineColor(TTTApplication.getMyColor(android.R.color.transparent));

        mTabs.setIndicatorHeight(6);
        mTabs.setUnderlineHeight(0);
        // 是否拉伸
        mTabs.setShouldExpand(false);
        // tab间的分割线 -- 透明表示没有
        mTabs.setDividerColor(android.R.color.transparent);

        // 设置actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.RIGHT;
        actionBar.setCustomView(v, layoutParams);

    }

    private void setUpNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    private void checkForUpdate() {
        // only wifi
        BmobUpdateAgent.forceUpdate(this);
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private long firstTime = 0;
    /**
     * 点击两次退出
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // 如果抽屉打开先关抽屉
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                mDrawerLayout.closeDrawers();
                return true;
            }

            // 如果打开侧滑历史
            if (mSlidingMenu.isMenuShowing()) {
                mSlidingMenu.toggle();
                return true;
            }

            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1200) {//如果两次按键时间间隔大于1200毫秒，则不退出
                Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
                System.exit(0);//否则退出程序
            }
        }
        return super.onKeyUp(keyCode, event);
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

    public SlidingMenu getSlidingMenu() {
        return mSlidingMenu;
    }

}
