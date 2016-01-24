package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.AroundCommentFragment;
import com.dean.travltotibet.fragment.AroundHotelCommentFragment;
import com.dean.travltotibet.fragment.AroundHotelDetailFragment;
import com.dean.travltotibet.fragment.AroundScenicCommentFragment;
import com.dean.travltotibet.fragment.AroundScenicDetailFragment;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.util.Constants;
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

        if (getIntent() != null) {
            mHotel = (Hotel) getIntent().getSerializableExtra(IntentExtra.INTENT_HOTEL);
        }

        setPageTitle(mHotel.getHotel_name());

        addPageTab(AroundHotelDetailFragment.class, null, getString(R.string.around_overview));
        addPageTab(AroundHotelCommentFragment.class, null, getString(R.string.around_comment));
        setUpTabViewPager();
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public String getAroundType() {
        return AroundType.HOTEL;
    }

    @Override
    public String[] getHeaderURL() {
        return null;
    }

    @Override
    public Object getAroundObj() {
        return mHotel;
    }

    @Override
    public boolean getDir() {
        return false;
    }
}
