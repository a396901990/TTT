package com.dean.travltotibet.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundSelectActivity extends Activity {

    private String routeName;

    private String aroundType;

    private String aroundBelong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_select_view);

        Intent intent = getIntent();
        if (intent != null) {
            routeName = intent.getStringExtra(IntentExtra.INTENT_ROUTE);
            aroundType = intent.getStringExtra(IntentExtra.INTENT_AROUND_TYPE);
            aroundBelong = intent.getStringExtra(IntentExtra.INTENT_AROUND_BELONG);
        }

        initView();
    }

    private void initView() {
    }

    public String getRouteName() {
        return routeName;
    }

    public String getAroundType() {
        return aroundType;
    }

    public String getAroundBelong() {
        return aroundBelong;
    }
}
