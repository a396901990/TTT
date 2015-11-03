package com.dean.travltotibet.activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.HomeSettingFragment;
import com.dean.travltotibet.fragment.HomeRecentFragment;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.PointManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class HomeActivity extends SlidingFragmentActivity {

    private String route;

    private String routeName;

    private SlidingMenu slidingMenu;

    private HomeRecentFragment homeRecentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

//        ActionBar actionBar = getActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setIcon(getResources().getDrawable(R.drawable.car_disable));
        initMenu();
        persistConfigurationData();
    }

    private void persistConfigurationData() {
        SharedPreferences sp = TTTApplication.getSharedPreferences();
        String[] default_points = getResources().getStringArray(R.array.default_points);
        StringBuffer sb = new StringBuffer();
        for (String point : default_points) {
            sb.append(point);
            sb.append(Constants.POINT_DIVIDE_MARK);
        }

        sp.edit().putString(Constants.CURRENT_POINTS, sb.toString()).commit();
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
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
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
