package com.dean.travltotibet.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;

import com.dean.travltotibet.R;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.PointManager;


/**
 * Created by DeanGuo on 11/3/15.
 */
public class ChartSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_setting_layout);

        if (getIntent() != null) {
            int orient = getIntent().getIntExtra(Constants.INTENT_ROUTE_ORIENTATION, 0);
            if (orient == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }
}
