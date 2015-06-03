package com.dean.travltotibet.activity;
import java.util.ArrayList;
import java.util.List;

import com.dean.greendao.DaoSession;
import com.dean.greendao.Geocode;
import com.dean.greendao.GeocodeDao;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.PlanSpinnerAdapter;
import com.dean.travltotibet.database.DBHelper;
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
        mChartView.setAxisRange(-5, 0, 505, 6000);



        // Create the data points
        series = new MountainSeries();
        indicatorSeries = new IndicatorSeries();
//        series.addPoint(new MountainSeries.MountainPoint(0, 220, "成都", Constants.CITY));
//        series.addPoint(new MountainSeries.MountainPoint(10, 3001, "雅安", Constants.CITY));
//        series.addPoint(new MountainSeries.MountainPoint(35, 5001, "二郎山", Constants.MOUNTAIN));
//        series.addPoint(new MountainSeries.MountainPoint(25, 31, "新都桥", Constants.TOWN));
//        series.addPoint(new MountainSeries.MountainPoint(55, 4533, "高尔山", Constants.MOUNTAIN));
//        series.addPoint(new MountainSeries.MountainPoint(65, 1800, "通麦", Constants.TOWN));
//        series.addPoint(new MountainSeries.MountainPoint(85, 5200, "米拉山", Constants.MOUNTAIN));
//        series.addPoint(new MountainSeries.MountainPoint(100, 3000, "拉萨", Constants.CITY));

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

//        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(0, 220));
//        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(10, 3001));
//        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(35, 5001));
//        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(25, 31));
//        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(55, 4533));
//        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(65, 1800));
//        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(85, 5200));
//        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(100, 3000));
        mIndicatorView.addSeries(indicatorSeries);
    }

    void initData() {
        List<Geocode> geocodes = TTTApplication.getDbHelper().getGeocodeList();
    }
    protected void updateHeader( AbstractPoint point )
    {
        TextView posName = (TextView) mHeaderView.findViewById(R.id.header_position_name);
        TextView posHeight = (TextView) mHeaderView.findViewById(R.id.header_position_height);
        TextView posMileage = (TextView) mHeaderView.findViewById(R.id.header_position_mileage);

        Place place = series.getPlace(point);
        posHeight.setText(place.getHeight());
        posMileage.setText(place.getMileage());
        posName.setText(place.getName());
    }

    private void initDropdownNavigation()
    {
        ArrayList<PlanNavItem> mPlans = new ArrayList<PlanNavItem>();
        mPlans.add(new PlanNavItem("川藏线", "成都－拉萨"));
        mPlans.add(new PlanNavItem("DAY1", "成都－拉萨"));
        mPlans.add(new PlanNavItem("DAY2", "成都－拉萨"));
        mPlans.add(new PlanNavItem("DAY3", "成都－拉萨"));

        PlanSpinnerAdapter adapter = new PlanSpinnerAdapter(this);
        adapter.setData(mPlans);
        
        LayoutInflater mInflater = LayoutInflater.from(this);
        final View spinnerView = mInflater.inflate(R.layout.plan_spinner, null);
        Spinner spinner = (Spinner) spinnerView.findViewById(R.id.plan_spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
