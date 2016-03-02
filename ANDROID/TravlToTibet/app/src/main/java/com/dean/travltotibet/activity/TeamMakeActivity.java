package com.dean.travltotibet.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.BaseHomeFragment;
import com.dean.travltotibet.fragment.LoginDialog;
import com.dean.travltotibet.fragment.TeamMakeFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

public class TeamMakeActivity extends BaseActivity implements LoginDialog.LoginListener{

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_make_layout);

        initToolBar();
        initPager();
        initFab();
    }

    private void initPager() {
        mPager = (ViewPager) this.findViewById(R.id.view_pager);

        if (mAdapter == null) {
            mAdapter = new ViewPageFragmentAdapter(getFragmentManager());
        }
        mAdapter.add(TeamMakeFragment.class, null, "最新结伴");
        mAdapter.add(TeamMakeFragment.class, null, "与我相关");
        mPager.setAdapter(mAdapter);

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);


        // 设置下拉刷新
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.half_dark_gray));
        //mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mAdapter.getAllFragments().size() > 0) {
                    BaseHomeFragment fragment = (BaseHomeFragment) mAdapter.getFragment(mPager.getCurrentItem());
                    fragment.refresh();
                }
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(getString(R.string.team_make_title));
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));
    }

    private void initFab() {
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        floatingActionButton.setImageResource(R.drawable.fab_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTeamRequest();
//                if (TTTApplication.hasLoggedIn()) {
//                    createTeamRequest();
//                } else {
//                    loginAction();
//                }
            }
        });
    }

    private void loginAction() {
        LoginDialog dialogFragment = new LoginDialog();
        dialogFragment.setLoginListener(this);
        dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
    }

    private void createTeamRequest() {
        Intent intent = new Intent(this, TeamMakeRequestActivity.class);
        startActivity(intent);
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public void loginSuccess() {
        createTeamRequest();
        Toast.makeText(getApplicationContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    public void startUpdate() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void finishUpdate() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
