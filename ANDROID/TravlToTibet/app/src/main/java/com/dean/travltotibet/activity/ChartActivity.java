package com.dean.travltotibet.activity;
import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.ChartFragment;
import com.dean.travltotibet.fragment.MenuFragment;
import com.dean.travltotibet.fragment.RouteFragment;
import com.dean.travltotibet.model.AbstractPoint;
import com.dean.travltotibet.model.Place;
import com.dean.travltotibet.ui.SlidingLayout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChartActivity
    extends SlidingFragmentActivity
{
    MenuFragment menuFragment;

    ChartFragment chartFragment;

    RouteFragment routeFragment;

    SlidingMenu slidingMenu;

    private View mHeaderView;

    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.main_layout);

        initMenu();
        initHeader();
        initFragment();

    }

    private void initHeader() {
        mHeaderView = this.findViewById(R.id.chart_header);

        // 初始化header menu两侧按钮
        initHeaderButton();
    }

    private void initFragment() {
        // menu fragment
        Fragment leftFragment = getFragmentManager().findFragmentById(R.id.menuFragment);
        if (leftFragment == null)
        {
            menuFragment = new MenuFragment();
            getFragmentManager().beginTransaction().replace(R.id.menuFragment, menuFragment).commit();
        }
        else
        {
            menuFragment = (MenuFragment) leftFragment;
        }

        // chart fragment
        Fragment contentFragment = getFragmentManager().findFragmentById(R.id.chartFragment);
        if (contentFragment == null)
        {
            chartFragment = new ChartFragment();
            getFragmentManager().beginTransaction().replace(R.id.chartFragment, chartFragment).commit();
        }
        else
        {
            chartFragment = (ChartFragment) contentFragment;
        }

        // route fragment
        Fragment rightFragment = getFragmentManager().findFragmentById(R.id.routeFragment);
        if (rightFragment == null)
        {
            routeFragment = new RouteFragment();
            getFragmentManager().beginTransaction().replace(R.id.routeFragment, routeFragment).commit();
        }
        else
        {
            routeFragment = (RouteFragment) rightFragment;
        }
    }

    private void initMenu()
    {
        // 设置主菜单
        setBehindContentView(R.layout.menu_layout);
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
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //slidingMenu.setMenu(R.layout.menu_layout);

        // slidingMenu.setBehindScrollScale(1.0f);
        slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow);
        //设置右边（二级）侧滑菜单
        slidingMenu.setSecondaryMenu(R.layout.route_layout);


        // 设置滑动时actionbar是否跟着移动，SLIDING_WINDOW=跟着移动;SLIDING_CONTENT=不跟着移动
        //menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);


    }

    /**
     * 初始化header左右两侧按钮
     */
    private void initHeaderButton() {
        Button menuBtn = (Button) mHeaderView.findViewById(R.id.menu_btn);
        LinearLayout routeBtn = (LinearLayout) mHeaderView.findViewById(R.id.route_btn);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLeftMenu();
            }
        });

        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRightMenu();
            }
        });
    }

    /**
     * 更新标题栏文字
     * @param start
     * @param end
     * @param date
     */
    public void updateHeader(String start, String end, String date) {
        TextView header_date = (TextView) this.findViewById(R.id.header_menu_date);
        TextView header_detail = (TextView) this.findViewById(R.id.header_menu_detail);

        header_date.setText(date);
        header_detail.setText(start + "-" + end);
    }

    /**
     * 更新标题头
     */
    public void updateHeader(Place place) {
        TextView posName = (TextView) mHeaderView.findViewById(R.id.header_position_name);
        TextView posHeight = (TextView) mHeaderView.findViewById(R.id.header_position_height);
        TextView posMileage = (TextView) mHeaderView.findViewById(R.id.header_position_mileage);

        if (place != null) {
            posHeight.setText(place.getHeight());
            posMileage.setText(place.getMileage());
            posName.setText(place.getName());
        }
    }


    public MenuFragment getMenuFragment() {
        return menuFragment;
    }

    public ChartFragment getChartFragment() {
        return chartFragment;
    }

    public RouteFragment getRouteFragment() {
        return routeFragment;
    }

    @Override
    public SlidingMenu getSlidingMenu() {
        return slidingMenu;
    }

    public void showLeftMenu()
    {
        slidingMenu.showMenu();
    }

    public void showRightMenu()
    {
        slidingMenu.showSecondaryMenu();
    }
}
