package com.dean.travltotibet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.fragment.QARequestPublishFragment;
import com.dean.travltotibet.fragment.TeamRequestPublishFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;

/**
 * Created by DeanGuo on 5/5/16.
 * 个人发布记录（包括组队，问答），先登录后查看
 */
public class UserPublishActivity extends BaseActivity {

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type_view);

        initToolBar();
        initPager();
    }

    private void initPager() {
        mPager = (ViewPager) this.findViewById(R.id.view_pager);

        if (mAdapter == null) {
            mAdapter = new ViewPageFragmentAdapter(getFragmentManager());
        }
        mAdapter.add(TeamRequestPublishFragment.class, null, "我的结伴");
        mAdapter.add(QARequestPublishFragment.class, null, "我的问答");
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(1);
        mPager.setCurrentItem(0, true);

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setTitle("我的发布");
        setHomeIndicator();
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    private void updateAll() {
        if (mAdapter != null && mAdapter.getAllFragments().size() > 0) {
            BaseRefreshFragment fragment = (BaseRefreshFragment) mAdapter.getFragment(mPager.getCurrentItem());
            fragment.onRefresh();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_REQUEST) {
            if (resultCode == RESULT_OK) {
                updateAll();
            }
        }
    }
}
