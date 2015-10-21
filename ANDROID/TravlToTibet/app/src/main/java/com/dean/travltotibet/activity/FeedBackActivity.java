package com.dean.travltotibet.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dean.travltotibet.R;

public class FeedbackActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back_layout);
        initHeader();
    }

    private void initHeader() {
        TextView headerTitle = (TextView) this.findViewById(R.id.header_title);
        headerTitle.setText(getText(R.string.feedback_title));

        View headerReturn = this.findViewById(R.id.header_return_btn);
        headerReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
