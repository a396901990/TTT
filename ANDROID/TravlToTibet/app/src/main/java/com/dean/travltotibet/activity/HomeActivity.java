package com.dean.travltotibet.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.HomePageAdapter;
import com.dean.travltotibet.fragment.BaseHomeFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
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

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
        setUpToolBar();
        setUpView();
        setUpHomeTab();
        setUpNavigationDrawer();
        checkForUpdate();
    }

    public void update() {
        if (mAdapter.getAllFragments().size() > 0) {

        }
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void setUpView() {
        // 设置viewpager
        mAdapter = new HomePageAdapter(getFragmentManager());
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(1);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (mAdapter.getAllFragments().size() > 0) {
                    BaseHomeFragment fragment = (BaseHomeFragment) mAdapter.getFragment(mPager.getCurrentItem());
                    fragment.update();
                }
            }
        });
        // 解决Viewpager和SwipeRefreshLayout滑动冲突
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mSwipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });

        // 设置下拉刷新
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.half_dark_gray));
        //mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mAdapter.getAllFragments().size() > 0) {
                    BaseHomeFragment fragment = (BaseHomeFragment) mAdapter.getFragment(mPager.getCurrentItem());
                    fragment.refresh();
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

        mTabs.setIndicatorHeight(10);
        mTabs.setUnderlineHeight(10);
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
        BmobUpdateAgent.update(this);
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

    public void startUpdate() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void finishUpdate() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
