package com.dean.travltotibet.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 11/28/15.
 */
public class WhereGoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.where_go_activity_layout);
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
