package com.dean.travltotibet.activity;
import java.util.ArrayList;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.PlanSpinnerAdapter;
import com.dean.travltotibet.model.AbstractPoint;
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
        initDatabase(this);
        getActionBar().setIcon(R.drawable.ic_ab_back_icon);
        initDropdownNavigation();
        getActionBar().setTitle("新藏线");
        setContentView(R.layout.activity_main);
        mHeaderView = findViewById(R.id.chart_header_contents);
        
        mChartView = (RouteChartView) findViewById(R.id.chart);
        mChartView.setAxisRange(-5, 0, 105, 6000);
        // Create the data points
        series = new MountainSeries();
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
        indicatorSeries = new IndicatorSeries();
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(0, 220));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(10, 3001));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(35, 5001));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(25, 31));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(55, 4533));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(65, 1800));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(85, 5200));
        indicatorSeries.addPoint(new IndicatorSeries.IndicatorPoint(100, 3000));
        mIndicatorView.addSeries(indicatorSeries);
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

    private void initDatabase(Context mContext) {
//        DaoSession daoSession = TTTApplication.getDaoSession(mContext);
//        LocationDao mLocationDao = daoSession.getLocationDao();
//
//
//        Location l = new Location();
//        l.setName("lasa");
//        l.setHeight("2051");
//        l.setMileage("3311");
//
//        mLocationDao.insert(l);
//
//        QueryBuilder<Location> mquery = mLocationDao.queryBuilder();
//        List list = mquery.list();
//        System.out.print(list);

        ArrayList<Geocode> geocodes = new ArrayList<Geocode>();
        Geocode geocode = new Geocode();
        geocode.setName("lasa");
        geocode.setElevation(1124);
        geocode.setMileage(4123);
        geocode.setAddress("aaasdf");
        geocode.setLatitude(333);
        geocode.setLongitude(444);
        geocode.setTypes("city");

        Geocode geocode1 = new Geocode();
        geocode1.setName("lasa");
        geocode1.setElevation(1124);
        geocode1.setMileage(4123);
        geocode1.setAddress("aaasdf");
        geocode1.setLatitude(333);
        geocode1.setLongitude(444);
        geocode1.setTypes("city");

        Geocode geocode2 = new Geocode();
        geocode2.setName("lasa");
        geocode2.setElevation(1124);
        geocode2.setMileage(4123);
        geocode2.setAddress("aaasdf");
        geocode2.setLatitude(333);
        geocode2.setLongitude(444);
        geocode2.setTypes("city");

        Geocode geocode3 = new Geocode();
        geocode3.setName("lasa");
        geocode3.setElevation(1124);
        geocode3.setMileage(4123);
        geocode3.setAddress("aaasdf");
        geocode3.setLatitude(333);
        geocode3.setLongitude(444);
        geocode3.setTypes("city");

        geocodes.add(geocode);
        geocodes.add(geocode1);
        geocodes.add(geocode2);
        geocodes.add(geocode3);

        GeocodesJson gj = new GeocodesJson();
        gj.setGeocodes(geocodes);
        Gson gson = new Gson();
        String s = gson.toJson(gj);
        Log.e("gsdfg", s);
    }

}
