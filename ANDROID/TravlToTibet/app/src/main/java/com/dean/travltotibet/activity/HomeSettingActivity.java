package com.dean.travltotibet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 10/16/15.
 */
public class HomeSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_setting);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 结束
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
