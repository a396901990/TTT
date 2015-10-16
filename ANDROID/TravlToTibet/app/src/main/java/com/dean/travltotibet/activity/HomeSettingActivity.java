package com.dean.travltotibet.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 10/16/15.
 */
public class HomeSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_setting);
        initHeader();
    }

    private void initHeader() {
        TextView headerTitle = (TextView) this.findViewById(R.id.header_title);
        headerTitle.setText(getText(R.string.title_activity_home_setting));

        View headerReturn = this.findViewById(R.id.header_return_btn);
        headerReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
