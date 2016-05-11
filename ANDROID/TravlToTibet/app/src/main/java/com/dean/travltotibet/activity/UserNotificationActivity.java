package com.dean.travltotibet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.fragment.QARequestFavoriteFragment;
import com.dean.travltotibet.fragment.TeamRequestFavoriteFragment;
import com.dean.travltotibet.fragment.UserMessageBaseFragment;
import com.dean.travltotibet.fragment.UserNotificationFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.util.ScreenUtil;

/**
 * Created by DeanGuo on 5/11/16.
 * 个人通知
 */
public class UserNotificationActivity extends BaseActivity {

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
        mAdapter.add(UserNotificationFragment.class, null, "通知");
//        mAdapter.add(QARequestFavoriteFragment.class, null, "私信");
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(1);
        mPager.setCurrentItem(0, true);

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setUpToolBar(toolbar);
        setTitle("我的消息");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_del) {
            actionDel();
            return false;
        }
        if (id == R.id.action_verified) {
            actionVerified();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionVerified() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.verified_message_action_title))
                .positiveText(getString(R.string.ok_btn))
                .negativeText(getString(R.string.cancel_btn))
                .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        verifiedMessage();
                        dialog.dismiss();
                        updateAll();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    private void actionDel() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.delete_message_action_title))
                .positiveText(getString(R.string.ok_btn))
                .negativeText(getString(R.string.cancel_btn))
                .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        deleteMessage();
                        dialog.dismiss();
                        updateAll();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    private void deleteMessage() {
        if (mAdapter != null && mAdapter.getAllFragments().size() > 0) {
            UserMessageBaseFragment fragment = (UserMessageBaseFragment) mAdapter.getFragment(mPager.getCurrentItem());
            if (fragment != null && fragment.isAdded()) {
                fragment.deleteMessage();
            }
        }
    }

    private void verifiedMessage() {
        if (mAdapter != null && mAdapter.getAllFragments().size() > 0) {
            UserMessageBaseFragment fragment = (UserMessageBaseFragment) mAdapter.getFragment(mPager.getCurrentItem());
            if (fragment != null && fragment.isAdded()) {
                fragment.verifiedMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_notification, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateAll();
    }
}
