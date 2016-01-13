package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.R;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundHotelActivity extends AroundBaseActivity {

    private Hotel mHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_hotel_view);

        if (getIntent() != null) {
            mHotel = (Hotel) getIntent().getSerializableExtra(IntentExtra.INTENT_HOTEL);
        }
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).actionBar().color(Color.WHITE));

        // set title
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mHotel.getHotel_name());
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public String[] getHeaderURL() {
        return null;
    }

    @Override
    public Object getAroundObj() {
        return mHotel;
    }
}
