package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.AroundHotelDetailFragment;
import com.dean.travltotibet.fragment.AroundScenicDetailFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundScenicActivity extends AroundBaseActivity {

    private Scenic mScenic;

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private boolean isForward = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_scenic_view);
        if (getIntent() != null) {
            mScenic = (Scenic) getIntent().getSerializableExtra(IntentExtra.INTENT_SCENIC);
            isForward = getIntent().getBooleanExtra(IntentExtra.INTENT_ROUTE_DIR, true);
        }
        initToolbar();
        initViewPagerAndTab();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).actionBar().color(Color.WHITE));

        // set title
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mScenic.getScenic_name());
    }

    private void initViewPagerAndTab() {
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new ViewPageFragmentAdapter(getFragmentManager());

        // 为adapter添加数据
        mAdapter.add(AroundScenicDetailFragment.class, null, "简介");
        mAdapter.add(AroundScenicDetailFragment.class, null, "评论");
        mPager.setAdapter(mAdapter);

        mPager.setOffscreenPageLimit(1);
        // 设置页面变化监听
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
            }
        });

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
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

    @Override
    public boolean getDir() {
        return isForward;
    }
}
