package com.dean.travltotibet.activity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.BaseRatingCommentFragment;
import com.dean.travltotibet.model.GalleryInfo;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by DeanGuo on 1/14/16.
 */
public abstract class AroundBaseActivity extends BaseActivity {

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private FloatingActionButton mFab;

    private GalleryInfo mGalleryInfo;

    private boolean isForward = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.around_tab_view);

        if (getIntent() != null) {
            mGalleryInfo = (GalleryInfo) getIntent().getSerializableExtra(IntentExtra.INTENT_GALLERY);
            isForward = getIntent().getBooleanExtra(IntentExtra.INTENT_ROUTE_DIR, true);
        }

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setHomeIndicator(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).actionBar().color(Color.WHITE));
        setPageTitle(getHeaderName());
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
            mFab.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_edit, TTTApplication.getMyColor(R.color.white)));
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
                    Fragment fragment = mAdapter.getFragment(mPager.getCurrentItem());
                    if (fragment.isAdded()) {
                        if (fragment instanceof BaseRatingCommentFragment) {
                            getFloatingBtn().setVisibility(View.VISIBLE);
                        } else {
                            getFloatingBtn().setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
    }

    public String[] getHeaderURL() {
        if (mGalleryInfo != null) {
            return mGalleryInfo.getUrl().split(Constants.URL_MARK);
        }
        return null;
    }

    public String getHeaderName() {
        if (mGalleryInfo != null) {
            return mGalleryInfo.getName();
        }
        return "";
    }

    public String getTypeObjectId() {
        if (mGalleryInfo != null) {
            return mGalleryInfo.getObjectId();
        }
        return null;
    }

    public boolean getDir() {
        return isForward;
    }

}
