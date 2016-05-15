package com.dean.travltotibet.fragment;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.QACreateActivity;
import com.dean.travltotibet.activity.TeamCreateRequestActivity;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.dialog.QASearchDialog;
import com.dean.travltotibet.dialog.SearchDialog;
import com.dean.travltotibet.dialog.TeamSearchDialog;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.ui.fab.FloatingActionButton;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.ui.tagview.OnTagClickListener;
import com.dean.travltotibet.ui.tagview.OnTagDeleteListener;
import com.dean.travltotibet.ui.tagview.Tag;
import com.dean.travltotibet.ui.tagview.TagView;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.ScreenUtil;
import com.dean.travltotibet.util.SearchFilterManger;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 4/30/16.
 */
public class HomeCommunityFragment extends Fragment {

    private static final int CREATE_REQUEST = 0;

    private static final int TEAM_REQUEST_VIEW = 0;

    private static final int QA_REQUEST_VIEW = 1;

    private View root;

    private HomeActivity mActivity;

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private View searchBar;

    private TagView tagView;

    private boolean tryToCreateTeamRequest = false;

    private boolean tryToCreateQA = false;

    private FloatingActionMenu mFloatingActionMenu;

    public static HomeCommunityFragment newInstance() {
        HomeCommunityFragment fragment = new HomeCommunityFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_community_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);

        mActivity = (HomeActivity) getActivity();

        initPager();
        initCreateBtn();
        initSearchView();
    }

    private void initSearchView() {
        searchBar = root.findViewById(R.id.search_view_container);
        tagView = (TagView) root.findViewById(R.id.tags_content);

        // 空白处点击
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchDialog();
            }
        });

        // tag view 点击删除
        tagView.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(TagView view, Tag tag, int position) {
                if (mPager.getCurrentItem() == TEAM_REQUEST_VIEW) {
                    SearchFilterManger.removeTagForTeamFilter(tag);
                }
                else if (mPager.getCurrentItem() == QA_REQUEST_VIEW) {
                    SearchFilterManger.removeTagForQAFilter(tag);
                }
                startSearch();
            }
        });

        // tag view 点击
        tagView.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position) {
                openSearchDialog();
            }
        });

        // 默认显示第一个页面搜索栏
        updateSearchView(0);
    }

    public void openSearchDialog() {

        SearchDialog searchDialog = null;
        if (mPager.getCurrentItem() == TEAM_REQUEST_VIEW) {
            searchDialog = new TeamSearchDialog();
        }
        else if (mPager.getCurrentItem() == QA_REQUEST_VIEW) {
            searchDialog = new QASearchDialog();
        }

        searchDialog.setSearchCallBack(new SearchDialog.SearchCallBack() {
            @Override
            public void onFinished() {
                startSearch();
            }
        });

        searchDialog.show(getFragmentManager(), SearchDialog.class.getName());
    }

    public void startSearch() {

        // 刷新search view
        updateSearchView(mPager.getCurrentItem());

        // 刷新当前页面
        BaseRefreshFragment fragment = (BaseRefreshFragment) mAdapter.getFragment(mPager.getCurrentItem());
        if (fragment != null) {
            fragment.onRefresh();
        }
    }

    private void setSearchHint(String hint) {
        TextView view = (TextView) root.findViewById(R.id.search_box_collapsed_hint);
        view.setHint(hint);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initPager() {
        mPager = (ViewPager) root.findViewById(R.id.view_pager);

        if (mAdapter == null) {
            mAdapter = new ViewPageFragmentAdapter(getChildFragmentManager());
        }

        mAdapter.add(HomeTeamRequestFragment.class, null, "约伴");
        mAdapter.add(HomeQAFragment.class, null, "问答");
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(1);
        mPager.setCurrentItem(0, true);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 跟新搜索栏
                updateSearchView(position);
            }
        });

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
    }

    private void updateSearchView(int position) {
        // 约伴界面
        if (position == TEAM_REQUEST_VIEW) {

            // 设置标签
            tagView.addTags(SearchFilterManger.getTeamFilterTags());

            // 设置搜索栏提示文字
            if (SearchFilterManger.getTeamFilterTags().size() == 0) {
                setSearchHint(getString(R.string.team_make_filter_hint));
            } else {
                setSearchHint("");
            }
        }
        // 问答界面
        else if (position == QA_REQUEST_VIEW) {
            // 设置标签
            tagView.addTags(SearchFilterManger.getQAFilterTags());

            // 设置搜索栏提示文字
            if (SearchFilterManger.getQAFilterTags().size() == 0) {
                setSearchHint(getString(R.string.q_a_filter_hint));
            } else {
                setSearchHint("");
            }
        }

    }

    private void initCreateBtn() {
        // 添加结伴
        mFloatingActionMenu = (FloatingActionMenu) root.findViewById(R.id.add_btn);
        mFloatingActionMenu.setClosedOnTouchOutside(true);
        FloatingActionButton teamFab = (FloatingActionButton) root.findViewById(R.id.team_create_fab);
        FloatingActionButton askFab = (FloatingActionButton) root.findViewById(R.id.ask_create_fab);
        mFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingActionMenu.toggle(true);
            }
        });
        mFloatingActionMenu.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.show_from_bottom));
        mFloatingActionMenu.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.hide_to_bottom));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFloatingActionMenu.showMenu(false);
            }
        }, 300);

        // 发起组队
        teamFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatingActionMenu.isOpened()) {
                    mFloatingActionMenu.toggle(true);
                }

                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (TTTApplication.hasLoggedIn()) {
                    gotoTeamCreate();
                } else {
                    tryToCreateTeamRequest = true;
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }

            }
        });

        // 提问题
        askFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatingActionMenu.isOpened()) {
                    mFloatingActionMenu.toggle(true);
                }

                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (TTTApplication.hasLoggedIn()) {
                    gotoQACreate();
                } else {
                    tryToCreateQA = true;
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }
            }
        });
    }

    public void gotoTeamCreate() {
        if (mPager != null) {
            mPager.setCurrentItem(0);
        }
        Intent intent = new Intent(getActivity(), TeamCreateRequestActivity.class);
        startActivityForResult(intent, CREATE_REQUEST);
        getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
    }

    public void gotoQACreate() {
        if (mPager != null) {
            mPager.setCurrentItem(1);
        }
        Intent intent = new Intent(getActivity(), QACreateActivity.class);
        startActivityForResult(intent, CREATE_REQUEST);
        getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
    }

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        if (tryToCreateTeamRequest) {
            gotoTeamCreate();
        }
        else if (tryToCreateQA) {
            gotoQACreate();
        }
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    public void refresh() {
//        Log.e("home community refresh", "refresh");
        if (mAdapter.getAllFragments().size() > 0) {
            BaseRefreshFragment fragment = (BaseRefreshFragment) mAdapter.getFragment(mPager.getCurrentItem());
            if (fragment.isAdded()) {
                fragment.onRefresh();
            }
        }
    }

    public void hiddenFloatingActionMenu() {
        mFloatingActionMenu.hideMenu(false);
    }

    public void showFloatingActionMenu() {
        mFloatingActionMenu.showMenu(false);
    }
}
