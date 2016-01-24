package com.dean.travltotibet.activity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.AroundBaseFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by DeanGuo on 1/14/16.
 */
public abstract class AroundBaseActivity extends BaseActivity{

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private FloatingActionButton mFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_tab_view);

        initToolbar();
    }

    public abstract String getAroundType();

    public abstract String[] getHeaderURL();

    public abstract Object getAroundObj();

    public abstract boolean getDir();

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).actionBar().color(Color.WHITE));
    }

    public void setPageTitle(String title) {
        // set title
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
    }

    public void addPageTab(Class<? extends Fragment> clazz, Bundle arguments, String title) {
        if (mAdapter == null) {
            mAdapter = new ViewPageFragmentAdapter(getFragmentManager());
        }
        mAdapter.add(clazz, arguments, title);
    }

    public FloatingActionButton getFloatingBtn() {
        if (mFab == null) {
            mFab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        }
        return mFab;
    }

    public void setUpTabViewPager() {
        mPager = (ViewPager) findViewById(R.id.view_pager);

        // 设置adapter
        if (mAdapter != null) {
            mPager.setAdapter(mAdapter);
        }

        mPager.setOffscreenPageLimit(0);
        // 设置页面变化监听
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (mAdapter.getAllFragments().size() > 0) {
                    AroundBaseFragment fragment = (AroundBaseFragment) mAdapter.getFragment(mPager.getCurrentItem());
                    if (fragment.isAdded()) {
                        fragment.onTabChanged();
                    }
                }
            }
        });

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
    }
}
