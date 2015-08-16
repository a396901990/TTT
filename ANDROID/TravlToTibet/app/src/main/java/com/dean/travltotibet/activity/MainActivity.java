package com.dean.travltotibet.activity;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.PlanSpinnerAdapter;
import com.dean.travltotibet.database.DBHelper;
import com.dean.travltotibet.database.ParseUtil;
import com.dean.travltotibet.fragment.ChartFragment;
import com.dean.travltotibet.model.AbstractPoint;
import com.dean.travltotibet.model.AbstractSeries;
import com.dean.travltotibet.model.AbstractSeries.PointListener;
import com.dean.travltotibet.model.GeocodesJson;
import com.dean.travltotibet.model.IndicatorSeries;
import com.dean.travltotibet.model.MountainSeries;
import com.dean.travltotibet.model.Place;
import com.dean.travltotibet.ui.IndicatorChartView;
import com.dean.travltotibet.ui.RouteChartView;
import com.dean.travltotibet.ui.SlidingLayout;
import com.dean.travltotibet.util.ChartCrosshairUtil.OnCrosshairPainted;
import com.dean.travltotibet.adapter.PlanSpinnerAdapter.PlanNavItem;
import com.dean.travltotibet.util.Constants;
import com.google.gson.Gson;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity
    extends Activity
{
    ChartFragment chartFragment;

    SlidingLayout slidingLayout;

    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        slidingLayout = (SlidingLayout) findViewById(R.id.sliding_layout);
        FrameLayout chartContent = (FrameLayout) findViewById(R.id.chartFragment);
        slidingLayout.setScrollEvent(chartContent);

        Button showLeftButton = (Button) findViewById(R.id.left_btn);
        showLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingLayout.isLeftLayoutVisible()) {
                    slidingLayout.scrollToContentFromLeftMenu();
                } else {
                    slidingLayout.initShowLeftState();
                    slidingLayout.scrollToLeftMenu();
                }
            }
        });

        // chart fragment
        Fragment fragment = getFragmentManager().findFragmentById(R.id.chartFragment);
        if (fragment == null)
        {
            chartFragment = new ChartFragment();
            getFragmentManager().beginTransaction().replace(R.id.chartFragment, chartFragment).commit();
        }
        else
        {
            chartFragment = (ChartFragment) fragment;
        }
        chartFragment.setSlidingMenuListener(slidingMenuListener);
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
