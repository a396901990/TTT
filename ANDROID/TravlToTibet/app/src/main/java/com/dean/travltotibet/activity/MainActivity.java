package com.dean.travltotibet.activity;
import java.util.ArrayList;
import java.util.List;

import com.dean.greendao.DaoSession;
import com.dean.greendao.Geocode;
import com.dean.greendao.GeocodeDao;
import com.dean.greendao.Routes;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.PlanSpinnerAdapter;
import com.dean.travltotibet.database.DBHelper;
import com.dean.travltotibet.database.ParseUtil;
import com.dean.travltotibet.model.AbstractPoint;
import com.dean.travltotibet.model.AbstractSeries;
import com.dean.travltotibet.model.AbstractSeries.PointListener;
import com.dean.travltotibet.model.GeocodesJson;
import com.dean.travltotibet.model.IndicatorSeries;
import com.dean.travltotibet.model.MountainSeries;
import com.dean.travltotibet.model.Place;
import com.dean.travltotibet.ui.IndicatorChartView;
import com.dean.travltotibet.ui.RouteChartView;
import com.dean.travltotibet.util.ChartCrosshairUtil.OnCrosshairPainted;
import com.dean.travltotibet.adapter.PlanSpinnerAdapter.PlanNavItem;
import com.dean.travltotibet.util.Constants;
import com.google.gson.Gson;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity
    extends Activity
{
    private RouteChartView mChartView;

    private IndicatorChartView mIndicatorView;

    private View mHeaderView;

    private MountainSeries series;

    private IndicatorSeries indicatorSeries;

    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(R.drawable.ic_ab_back_icon);
        initDropdownNavigation();
        getActionBar().setTitle("新藏线");
        setContentView(R.layout.activity_main);
        mHeaderView = findViewById(R.id.chart_header_contents);
        
        mChartView = (RouteChartView) findViewById(R.id.chart);
        mChartView.setAxisRange(-30, 0, 2680, 6500);

        // Create the data points
        series = new MountainSeries();
        indicatorSeries = new IndicatorSeries();

        List<Geocode> geocodes = TTTApplication.getDbHelper().getGeocodeList();
        for (Geocode geocode : geocodes) {
            series.addPoint(new MountainSeries.MountainPoint((int)geocode.getMileage(), (int)geocode.getElevation(), geocode.getName(), AbstractSeries.getType(geocode.getTypes())));
            indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint((int)geocode.getMileage(), (int)geocode.getElevation()));
        }

        // Add chart view data
        mChartView.addSeries(series);
        mChartView.initCrosshair();
        mChartView.addCrosshairPaintedListener(new OnCrosshairPainted()
            {

                @Override
                public void onCrosshairPainted( AbstractPoint point )
                {
                    updateHeader(point);
                }
            });
        mChartView.setPointListener(new PointListener()
            {

                @Override
                public void pointOnTouched( AbstractPoint point )
                {
                    updateHeader(point);
                }
            });

        mIndicatorView = (IndicatorChartView) findViewById(R.id.indicator);
        mIndicatorView.setChartView(mChartView);
        mIndicatorView.addSeries(indicatorSeries);
    }

    protected void updateHeader( AbstractPoint point )
    {
        TextView posName = (TextView) mHeaderView.findViewById(R.id.header_position_name);
        TextView posHeight = (TextView) mHeaderView.findViewById(R.id.header_position_height);
        TextView posMileage = (TextView) mHeaderView.findViewById(R.id.header_position_mileage);

        Place place = series.getPlace(point);

        if (place != null) {
            posHeight.setText(place.getHeight());
            posMileage.setText(place.getMileage());
            posName.setText(place.getName());
        }

    }

    /**
     * 初始化下拉菜单
     */
    private void initDropdownNavigation()
    {
        ArrayList<PlanNavItem> mPlans = new ArrayList<PlanNavItem>();

        // 获取数据库路线
        List<Routes> routes = TTTApplication.getDbHelper().getRoutsList();

        // 为下拉菜单赋值
        // 第一个初始默认，每次加载会调用第一个的onItemSelected方法
        mPlans.add(new PlanNavItem("新藏线", "叶城县", "拉萨"));
        // 其他路线
        for (Routes r : routes) {
            mPlans.add(new PlanNavItem("DAY"+r.getId(), r.getStart() ,r.getEnd()));
        }

        PlanSpinnerAdapter adapter = new PlanSpinnerAdapter(this);
        adapter.setData(mPlans);
        
        LayoutInflater mInflater = LayoutInflater.from(this);
        final View spinnerView = mInflater.inflate(R.layout.plan_spinner, null);
        Spinner spinner = (Spinner) spinnerView.findViewById(R.id.plan_spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PlanNavItem planNavItem = (PlanNavItem) parent.getItemAtPosition(position);
                String date = planNavItem.getPlanDate();
                String start = planNavItem.getPlanDetailStart();
                String end = planNavItem.getPlanDetailEnd();

                List<Geocode> geocodes = TTTApplication.getDbHelper().getGeocodeListWithName(start, end);

                series = new MountainSeries();
                indicatorSeries = new IndicatorSeries();

                float firstPointLength = (float)geocodes.get(0).getMileage();
                float lastPointLength = (float)geocodes.get(geocodes.size()-1).getMileage();
                float pointLength = lastPointLength - firstPointLength;

                for (Geocode geocode : geocodes) {
                    series.addPoint(new MountainSeries.MountainPoint((int)geocode.getMileage()-firstPointLength, (int)geocode.getElevation(), geocode.getName(), AbstractSeries.getType(geocode.getTypes())));
                    indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint((int)geocode.getMileage()-firstPointLength, (int)geocode.getElevation()));
                }

                // reset chart view data
                mChartView.setAxisRange(-30, 0, pointLength + 30, 6500);
                mChartView.addSeries(series);
                mIndicatorView.addSeries(indicatorSeries);
                mIndicatorView.setChartView(mChartView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.RIGHT;
        layoutParams.rightMargin = 5;
        getActionBar().setCustomView(spinnerView, layoutParams); 
        getActionBar().setDisplayShowCustomEnabled(true);
    }

}
