package com.dean.travltotibet.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.InfoConfirmDialog;
import com.dean.travltotibet.fragment.RecentFragment;
import com.dean.travltotibet.fragment.RoutePlanFragment;
import com.dean.travltotibet.fragment.TravelTypeDialog;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class MainActivity extends SlidingFragmentActivity {

    private String route = "XINZANG";

    private String routeName = "新藏线";

    private SlidingMenu slidingMenu;

    private RecentFragment recentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(getResources().getDrawable(R.drawable.car_disable));
        initMenu();

        Button b = (Button) findViewById(R.id.btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TravelTypeDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_ROUTE, route);
                bundle.putString(Constants.INTENT_ROUTE_NAME, routeName);
                bundle.putString(Constants.INTENT_FROM_WHERE, TravelTypeDialog.FROM_FIRST);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), TravelTypeDialog.class.getName());
            }
        });

    }

    private void initMenu() {

        // recent fragment
        Fragment fragment = getFragmentManager().findFragmentById(R.id.recentFragment);
        if (fragment == null) {
            recentFragment = RecentFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.recentFragment, recentFragment).commit();
        } else {
            recentFragment = (RecentFragment) fragment;
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
        //slidingMenu.setMenu(R.layout.menu_layout);

//        // slidingMenu.setBehindScrollScale(1.0f);
//        slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow);
//        //设置右边（二级）侧滑菜单
//        slidingMenu.setSecondaryMenu(R.layout.route_fragment_layout);

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

        if (id == R.id.action_recent) {
            recentFragment.updateRecentData();
            slidingMenu.showMenu();
        }
        else if (id == android.R.id.home) {
            TTTApplication.getDbHelper().cleanRecentRoutes();
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
