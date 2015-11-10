package com.dean.travltotibet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;

import com.dean.travltotibet.R;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 11/8/15.
 */
public class PrepareDetailActivity extends Activity {

    private String route;

    private InfoType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prepare_detail_layout);

        Intent intent = getIntent();
        if (intent != null) {
            route = intent.getStringExtra(Constants.INTENT_ROUTE);
            type = (InfoType) intent.getSerializableExtra(Constants.INTENT_PREPARE_TYPE);
        }

        // 设置标题
        getActionBar().setTitle(InfoType.INFO_TEXT.get(type));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 结束
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public String getRoute() {
        return route;
    }

    public InfoType getType() {
        return type;
    }
}
