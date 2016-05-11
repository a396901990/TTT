package com.dean.travltotibet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.fragment.QARequestPublishFragment;
import com.dean.travltotibet.fragment.TeamRequestPublishFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.util.ScreenUtil;

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
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 退出刷新
            setResult(RESULT_OK);
            finish();
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // 退出刷新
            setResult(RESULT_OK);
            finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 只在UPDATE_REQUEST操作后刷新
        if (requestCode == BaseActivity.UPDATE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                updateAll();
            }
        }
    }

}
