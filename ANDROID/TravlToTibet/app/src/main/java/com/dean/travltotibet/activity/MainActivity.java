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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity
    extends Activity
{
    ChartFragment chartFragment;

    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
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
    }


}
