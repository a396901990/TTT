package com.dean.travltotibet.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.ui.PagerSlidingTabStrip;
import com.dean.travltotibet.ui.fab.FloatingActionButton;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class HomeCommunityFragment extends BaseRefreshFragment {

    private static final int CREATE_REQUEST = 0;

    private View root;

    private HomeActivity mActivity;

    private ViewPager mPager;

    private ViewPageFragmentAdapter mAdapter;

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
        mActivity = (HomeActivity) getActivity();

        initPager();
        initCreateBtn();
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
                if (mFloatingActionMenu.isOpened()) {
                }

                mFloatingActionMenu.toggle(true);
            }
        });
    }

}
