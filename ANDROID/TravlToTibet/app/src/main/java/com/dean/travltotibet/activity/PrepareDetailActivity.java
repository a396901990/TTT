package com.dean.travltotibet.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.PrepareDetailPageAdapter;
import com.dean.travltotibet.dialog.InfoTravelTypeDialog;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.util.CountUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
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

    private ImageView mMenuType;

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
        initMenu();
        initViewPagerAndTab();

        if (mInfoType != null) {
            CountUtil.countPrepareInfo(this, mRoute, mInfoType.name(), mType);
        }
    }

    private void initMenu() {
        // type btn(menu right)
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.menu_type, null);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.RIGHT;
        setCustomView(v, layoutParams);

        mMenuType = (ImageView) v.findViewById(R.id.header_menu_type);
        mMenuType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTypeDialog();
            }
        });
        updateMenu();
    }

    private void openTypeDialog() {
        if (ScreenUtil.isFastClick()) {
            return;
        }
        InfoTravelTypeDialog dialogFragment = new InfoTravelTypeDialog();
        dialogFragment.setListener(new InfoTravelTypeDialog.TravelTypeListener() {
            @Override
            public void updateTravelType(String type) {
                mType = type;
                updateMenu();
                initViewPagerAndTab();
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString(IntentExtra.INTENT_ROUTE, mRoute);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), InfoTravelTypeDialog.class.getName());
    }

    public void updateMenu() {
        mMenuType.setImageDrawable(TravelType.getActionBarImageSrc(mType));
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
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mInfoType = InfoType.INFO_TYPES.get(position);
            }
        });
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
