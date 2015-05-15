/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dean.travltotibet;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

public class MainActivity
    extends Activity
{
    private RouteChartView mChartView;

    private IndicatorChartView mIndicatorView;

    private MountainSeries series;

    private IndicatorSeries indicatorSeries;

    private boolean isShowSelector = false;

    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChartView = (RouteChartView) findViewById(R.id.chart);
        mChartView.setAxisRange(-5, 0, 105, 6000);
        // Create the data points
        series = new MountainSeries();
        series.setLineWidth(2);
        series.setLineColor(0xFF0099CC);
        series.setMountainColor(0xFF0099CC);
        series.setMountainAlpha(0.6);

        series.addPoint(new MountainSeries.MountainPoint(0, 220, "成都", Constants.CITY));
        series.addPoint(new MountainSeries.MountainPoint(10, 3001, "雅安", Constants.CITY));
        series.addPoint(new MountainSeries.MountainPoint(35, 5001, "二郎山", Constants.MOUNTAIN));
        series.addPoint(new MountainSeries.MountainPoint(25, 31, "新都桥", Constants.TOWN));
        series.addPoint(new MountainSeries.MountainPoint(55, 4533, "高尔山", Constants.MOUNTAIN));
        series.addPoint(new MountainSeries.MountainPoint(65, 1800, "通麦", Constants.TOWN));
        series.addPoint(new MountainSeries.MountainPoint(85, 5200, "米拉山", Constants.MOUNTAIN));
        series.addPoint(new MountainSeries.MountainPoint(100, 3000, "拉萨", Constants.CITY));

        // Add chart view data
        mChartView.addSeries(series);
        mChartView.initCrosshair();
        mChartView.addCrosshairPaintedListener(new ChartCrosshairUtil.OnCrosshairPainted()
            {

                @Override
                public void onCrosshairPainted( int index )
                {
                    updateHeader(index);
                }
            });

        mIndicatorView = (IndicatorChartView) findViewById(R.id.indicator);
        mIndicatorView.setChartView(mChartView);
        indicatorSeries = new IndicatorSeries(this);
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(0, 2250));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(10, 3001));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(35, 5001));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(25, 3251));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(55, 4533));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(65, 1800));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(85, 5200));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(100, 3000));
        mIndicatorView.addSeries(indicatorSeries);
    }

    protected void updateHeader( int index )
    {

    }

    double oldLocation = 0;

    double newLocation = 0;

    @Override
    public boolean onTouchEvent( MotionEvent event )
    {
        double eachX = event.getX();
        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN)
        {
                oldLocation = eachX;
                toggleTimeSelector();
        }

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP)
        {
            newLocation = eachX;
            
            if (Math.abs(newLocation - oldLocation) < 10d)
            {
             //   toggleTimeSelector();
            }
        }
        return true;
    }

    private void toggleTimeSelector()
    {

        View view = findViewById(R.id.timeSelector);

        View topShadow = findViewById(R.id.topRule);
        View bottomShadow = findViewById(R.id.bottomRule);
        // Define a flag for time selector used for toggle time bar frame
        // animation
        TranslateAnimation animation;
        if (view.getVisibility() == View.GONE)
        {
            view.setVisibility(View.VISIBLE);
            animation = new TranslateAnimation(0, 0, view.getHeight(), 0);
            animation.setDuration(800);
            if (topShadow != null && bottomShadow != null)
            {
                topShadow.setVisibility(View.VISIBLE);
                bottomShadow.setVisibility(View.VISIBLE);
                topShadow.startAnimation(animation);
                bottomShadow.startAnimation(animation);
            }
            view.startAnimation(animation);
        }
        else
        {
            animation = new TranslateAnimation(0, 0, 0, view.getHeight());
            animation.setDuration(800);
            if (topShadow != null && bottomShadow != null)
            {
                topShadow.setVisibility(View.GONE);
                bottomShadow.setVisibility(View.GONE);
                topShadow.startAnimation(animation);
                bottomShadow.startAnimation(animation);
            }
            view.startAnimation(animation);
            view.setVisibility(View.GONE);
        }
    }

}
