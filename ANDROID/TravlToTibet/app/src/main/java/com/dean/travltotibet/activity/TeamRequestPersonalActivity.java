package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.RefreshFragment;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.fragment.TeamRequestFavoriteFragment;
import com.dean.travltotibet.fragment.TeamRequestMyselfFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 3/16/16.
 * 个人记录（包括个人发布，个人收藏，个人回复），先登录后查看
 */
public class TeamRequestPersonalActivity extends BaseActivity implements LoginDialog.LoginListener {

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private View loginView;

    private LoginDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_request_personal_view);

        initToolBar();
        initLoginView();
        initPager();
        checkLogin();
    }

    private void initLoginView() {
        loginDialog = new LoginDialog();
        loginDialog.setLoginListener(this);

        loginView = findViewById(R.id.login_view);
        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.show(getFragmentManager(), LoginDialog.class.getName());
            }
        });
    }

    private void checkLogin() {
        if (!TTTApplication.hasLoggedIn()) {
            loginView.setVisibility(View.VISIBLE);
        } else {
            loginView.setVisibility(View.GONE);

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    updateAll();
                }
            });
        }
    }

    private void initPager() {
        mPager = (ViewPager) this.findViewById(R.id.view_pager);

        if (mAdapter == null) {
            mAdapter = new ViewPageFragmentAdapter(getFragmentManager());
        }
        mAdapter.add(TeamRequestMyselfFragment.class, null, "我的发布");
        mAdapter.add(TeamRequestFavoriteFragment.class, null, "我的收藏");
        mAdapter.add(TeamRequestMyselfFragment.class, null, "我的回复");
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(3);
        mPager.setCurrentItem(0, true);

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle("我的结伴");
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public void loginSuccess() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                loginView.setVisibility(View.GONE);
                updateAll();
            }
        });
        Toast.makeText(getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    private void updateAll() {
        if (mAdapter != null && mAdapter.getAllFragments().size() > 0) {
            RefreshFragment fragment = (RefreshFragment) mAdapter.getFragment(mPager.getCurrentItem());
            fragment.refresh();
        }
    }
}
