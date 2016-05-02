package com.dean.travltotibet.fragment;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.TeamCreateRequestActivity;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.dialog.SearchDialog;
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
 * Created by DeanGuo on 3/3/16.
 */
public class HomeCommunityFragment extends BaseRefreshFragment {

    private static final int CREATE_REQUEST = 0;

    private View root;

    private HomeActivity mActivity;

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

    private View searchBar;

    private TagView tagView;

    SearchDialog searchDialog;

    private boolean tryToCreateTeamRequest = false;

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

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchDialog != null) {
                    searchDialog.show(getFragmentManager(), SearchDialog.class.getName());
                }
            }
        });

        // 删除
        tagView.setOnTagDeleteListener(new OnTagDeleteListener() {
            @Override
            public void onTagDeleted(TagView view, Tag tag, int position) {
                SearchFilterManger.removeTagForTeamFilter(tag);
                startSearch();
            }
        });

        // 点击
        tagView.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position) {
                if (searchDialog != null) {
                    searchDialog.show(getFragmentManager(), SearchDialog.class.getName());
                }
            }
        });

        setSearchHint(getString(R.string.team_make_filter_hint));

        searchDialog = new SearchDialog();
        searchDialog.setSearchCallBack(new SearchDialog.SearchCallBack() {
            @Override
            public void onFilter(ArrayList<Tag> tags) {
                startSearch();
            }
        });
    }

    public void startSearch() {
        tagView.addTags(SearchFilterManger.getTeamFilterTags());

        if (SearchFilterManger.getTeamFilterTags().size() == 0) {
            setSearchHint(getString(R.string.team_make_filter_hint));
        } else {
            setSearchHint("");
        }

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
        mAdapter.add(HomeTopicFragment.class, null, "问答");
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(1);
        mPager.setCurrentItem(0, true);

        PagerSlidingTabStrip mTabs = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
    }

    private void initCreateBtn() {
        // 添加结伴
        final FloatingActionMenu mFloatingActionMenu = (FloatingActionMenu) root.findViewById(R.id.add_btn);
        mFloatingActionMenu.setClosedOnTouchOutside(true);
        FloatingActionButton teamFab = (FloatingActionButton) root.findViewById(R.id.team_create_fab);
        FloatingActionButton askFab = (FloatingActionButton) root.findViewById(R.id.ask_create_fab);
        mFloatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingActionMenu.toggle(true);
            }
        });

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
                    Intent intent = new Intent(getActivity(), TeamCreateRequestActivity.class);
                    startActivityForResult(intent, CREATE_REQUEST);
                    getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
                } else {
                    tryToCreateTeamRequest = true;
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }

            }
        });
    }

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        if (tryToCreateTeamRequest) {
            Intent intent = new Intent(getActivity(), TeamCreateRequestActivity.class);
            startActivityForResult(intent, CREATE_REQUEST);
            getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
            tryToCreateTeamRequest = false;
        }
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }


}
