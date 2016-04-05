package com.dean.travltotibet.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.PrepareDetailPageAdapter;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.util.CountUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by DeanGuo on 11/8/15.
 */
public class PrepareDetailActivity extends BaseActivity {

    private String mRoute;

    private String mType;

    private InfoType mInfoType;

    private ViewPager mPager;

    private PrepareDetailPageAdapter mAdapter;

    private PagerSlidingTabStrip mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.prepare_detail_view);

        Intent intent = getIntent();
        if (intent != null) {
            mRoute = intent.getStringExtra(IntentExtra.INTENT_ROUTE);
            mType = intent.getStringExtra(IntentExtra.INTENT_ROUTE_TYPE);
            mInfoType = (InfoType) intent.getSerializableExtra(IntentExtra.INTENT_PREPARE_TYPE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator();
        // 设置标题
        setTitle("准备信息");
        initViewPagerAndTab();

        CountUtil.countPrepareInfo(this, mRoute, mInfoType.name(), mType);
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    private void initViewPagerAndTab() {
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new PrepareDetailPageAdapter(getFragmentManager());
        mAdapter.setData(InfoType.INFO_TYPES, mRoute, mType);
        mPager.setAdapter(mAdapter);

        mPager.setOffscreenPageLimit(1);

        mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.home_tabs);
        mTabs.setViewPager(mPager);

        mPager.setCurrentItem(InfoType.INFO_TYPES.indexOf(mInfoType), true);
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
