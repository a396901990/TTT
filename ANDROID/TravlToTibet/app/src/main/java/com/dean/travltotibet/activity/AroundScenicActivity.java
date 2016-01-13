package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundScenicActivity extends AroundBaseActivity {

    private Scenic mScenic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_scenic_view);
        if (getIntent() != null) {
            mScenic = (Scenic) getIntent().getSerializableExtra(IntentExtra.INTENT_SCENIC);
            initView();
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).actionBar().color(Color.WHITE));

        // set title
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mScenic.getScenic_name());
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public String[] getHeaderURL() {
        return mScenic.getScenic_pic().split(Constants.URL_MARK);
    }

    @Override
    public Object getAroundObj() {
        return mScenic;
    }
}
