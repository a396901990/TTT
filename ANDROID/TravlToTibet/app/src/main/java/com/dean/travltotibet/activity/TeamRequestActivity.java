package com.dean.travltotibet.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.RefreshFragment;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.fragment.TeamShowAllRequestFragment;
import com.dean.travltotibet.fragment.TeamShowRequestTypeFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamRequestActivity extends BaseActivity implements LoginDialog.LoginListener {

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_request_view);

        initToolBar();
        initPager();
        initFab();
    }

    private void initPager() {
        mPager = (ViewPager) this.findViewById(R.id.view_pager);

        if (mAdapter == null) {
            mAdapter = new ViewPageFragmentAdapter(getFragmentManager());
        }
        mAdapter.add(TeamShowAllRequestFragment.class, null, "最新结伴");
        mAdapter.add(TeamShowRequestTypeFragment.class, null, "我的结伴");
        mPager.setAdapter(mAdapter);

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);

        // 解决Viewpager和SwipeRefreshLayout滑动冲突
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mSwipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });

        // 设置下拉刷新
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.half_dark_gray));
        //mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateAll();
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

    private void createTeamRequest() {
        Intent intent = new Intent(this, TeamCreateRequestActivity.class);
        startActivity(intent);
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAll();
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

    private void updateAll() {
        if (mAdapter != null && mAdapter.getAllFragments().size() > 0) {
            RefreshFragment fragment = (RefreshFragment) mAdapter.getFragment(mPager.getCurrentItem());
            fragment.refresh();
        }
    }

    public void startUpdate() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void finishUpdate() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
