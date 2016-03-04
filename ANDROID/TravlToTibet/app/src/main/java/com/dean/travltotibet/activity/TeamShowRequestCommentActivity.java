package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.BaseCommentDialog;
import com.dean.travltotibet.fragment.TeamRequestCommentDialog;
import com.dean.travltotibet.fragment.TeamRequestCommentFragment;
import com.dean.travltotibet.fragment.TeamShowRequestFragment;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import cn.bmob.v3.BmobObject;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamShowRequestCommentActivity extends BaseCommentActivity {

    private TeamRequest teamRequest;

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.team_show_request_view);

        if (getIntent() != null) {
            teamRequest = (TeamRequest) getIntent().getSerializableExtra(IntentExtra.INTENT_TEAM_REQUEST);
        }
        if (teamRequest == null) {
            finish();
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(teamRequest.getDestination());
        setSubTitle(teamRequest.getDate());
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        updateWatch();
        initBtn();
        initViewPage();
    }

    private void initViewPage() {
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new ViewPageFragmentAdapter(getFragmentManager());

        // 为adapter添加数据
        mAdapter.add(TeamShowRequestFragment.class, null, "");
        mAdapter.add(TeamRequestCommentFragment.class, null, "");
        mPager.setAdapter(mAdapter);

        mPager.setOffscreenPageLimit(0);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
//                    goArticlePage();
                } else {
//                    goCommentPage();
                }
            }
        });
    }

    private void updateWatch() {
        try {
            teamRequest.increment("watch");
            if (this != null) {
                teamRequest.update(this, null);
            }
        } catch (Exception e) {
            // finish();
        }
    }

    private void initBtn() {
        // 评论
        View commentBtn = findViewById(R.id.send_comment_btn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentAction();
            }
        });

        // 切换按钮
        View switchBtn = findViewById(R.id.comment_switch_icon);
        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() == 0) {
                    goCommentPage();
                } else if (mPager.getCurrentItem() == 1) {
                    goArticlePage();
                }
            }
        });
    }

    private void goCommentPage() {
        TextView switchBtn = (TextView) findViewById(R.id.comment_switch_icon);
        switchBtn.setText("原文");
        mPager.setCurrentItem(1, true);
    }

    private void goArticlePage() {
        TextView switchBtn = (TextView) findViewById(R.id.comment_switch_icon);
        switchBtn.setText("评论");
        mPager.setCurrentItem(0, true);
    }

    private void commentAction() {
        if (ScreenUtil.isFastClick()) {
            return;
        }
        BaseCommentDialog dialogFragment = new TeamRequestCommentDialog();
        dialogFragment.setCommentCallBack(this);
        dialogFragment.show(getFragmentManager(), TeamRequestCommentDialog.class.getName());
    }

    @Override
    public void onCommentSuccess() {
        goCommentPage();
        TeamRequestCommentFragment articleCommentFragment = (TeamRequestCommentFragment) mAdapter.getFragment(1);
        if (articleCommentFragment != null) {
            articleCommentFragment.updateComment();
        }
    }

    @Override
    public void onCommentFailed() {

    }

    @Override
    public BmobObject getObj() {
        return teamRequest;
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    public TeamRequest getTeamRequest() {
        return teamRequest;
    }
}
