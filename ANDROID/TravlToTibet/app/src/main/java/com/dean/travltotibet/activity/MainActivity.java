package com.dean.travltotibet.activity;
import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.ChartFragment;
import com.dean.travltotibet.fragment.RouteFragment;
import com.dean.travltotibet.ui.SlidingLayout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity
    extends Activity
{
    ChartFragment chartFragment;

    RouteFragment routeFragment;

    SlidingLayout slidingLayout;

    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        slidingLayout = (SlidingLayout) findViewById(R.id.sliding_layout);
        FrameLayout chartContent = (FrameLayout) findViewById(R.id.chartFragment);
        slidingLayout.setScrollEvent(chartContent);

        // chart fragment
        Fragment cFragment = getFragmentManager().findFragmentById(R.id.chartFragment);
        if (cFragment == null)
        {
            chartFragment = new ChartFragment();
            getFragmentManager().beginTransaction().replace(R.id.chartFragment, chartFragment).commit();
        }
        else
        {
            chartFragment = (ChartFragment) cFragment;
        }
        chartFragment.setSlidingMenuListener(slidingMenuListener);

        // route fragment
        Fragment rFragment = getFragmentManager().findFragmentById(R.id.routeFragment);
        if (rFragment == null)
        {
            routeFragment = new RouteFragment();
            getFragmentManager().beginTransaction().replace(R.id.routeFragment, routeFragment).commit();
        }
        else
        {
            routeFragment = (RouteFragment) rFragment;
        }
    }

    SlidingLayout.SlidingMenuListener slidingMenuListener = new SlidingLayout.SlidingMenuListener() {
        @Override
        public void onLeftMenuBtnClicked() {
            if (slidingLayout.isLeftLayoutVisible()) {
                slidingLayout.scrollToContentFromLeftMenu();
            } else {
                slidingLayout.initShowLeftState();
                slidingLayout.scrollToLeftMenu();
            }
        }

        @Override
        public void onRightMenuBtnClicked() {
            if (slidingLayout.isRightLayoutVisible()) {
                slidingLayout.scrollToContentFromRightMenu();
            } else {
                slidingLayout.initShowRightState();
                slidingLayout.scrollToRightMenu();
            }
        }
    };
}
