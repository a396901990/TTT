package com.dean.travltotibet.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.BaseHomeFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;

import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class HomeActivity extends AppCompatActivity {

    private String route;

    private String routeName;

    private HomePageAdapter mAdapter;
    private ViewPager mPager;
    private PagerSlidingTabStrip mTabs;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;


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

        setUpToolBar();
        setUpViewPager();
        setUpHomeTab();
        setUpNavigationDrawer();
        checkForUpdate();
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpViewPager() {
        mAdapter = new HomePageAdapter(getFragmentManager());
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(3);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (mAdapter.getAllFragments().size() > 0) {
                    BaseHomeFragment fragment = (BaseHomeFragment) mAdapter.getFragment(mPager.getCurrentItem());
                    fragment.update();
                }
            }
        });
    }

    private void setUpHomeTab() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.home_tabs, null);
        assert v != null;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //v.setLayoutParams(params);
        mTabs = (PagerSlidingTabStrip) v.findViewById(R.id.home_tabs);
        mTabs.setViewPager(mPager);

        // 字体颜色大小
        mTabs.setTextColor(TTTApplication.getMyColor(R.color.dark_blue));
        // 指示器颜色，大小
        mTabs.setIndicatorColor(TTTApplication.getMyColor(R.color.dark_blue));
        mTabs.setUnderlineColor(TTTApplication.getMyColor(android.R.color.transparent));
        mTabs.setIndicatorHeight(10);
        mTabs.setUnderlineHeight(10);
        // 是否拉伸
        mTabs.setShouldExpand(false);
        // tab间的分割线 -- 透明表示没有
        mTabs.setDividerColor(android.R.color.transparent);

        // 设置actionbar
        //getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setCustomView(v);
        toolbar.addView(v, params);

    }

    private void setUpNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
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
