package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.adapter.RecommendAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.ui.VerticalSpaceItemDecoration;
import com.dean.travltotibet.ui.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 10/15/15.
 */
public class HomeRecommendFragment extends RefreshFragment {

    private View root;
    private RecommendAdapter mAdapter;
    private ArrayList<Route> routes;
    private HomeActivity mActivity;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;

    public HomeRecommendFragment() {
    }

    public static HomeRecommendFragment newInstance() {
        HomeRecommendFragment fragment = new HomeRecommendFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_recommend_layout, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) getActivity();

        initList();
        refresh();
        initFabBtn();
    }

    private void initList() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recommend_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(1));

        mAdapter = new RecommendAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initFabBtn() {
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setColorNormalResId(R.color.colorPrimary);
        fab.setColorPressedResId(R.color.colorPrimaryDark);
        fab.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.show(true);
                fab.setShowAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.show_from_bottom));
                fab.setHideAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.hide_to_bottom));
            }
        }, 300);

        // 点击打开slidingMenu
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity.getSlidingMenu().isMenuShowing()) {
                    mActivity.getSlidingMenu().showMenu(true);
                } else {
                    mActivity.getSlidingMenu().showMenu(false);
                }
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    fab.hide(true);
                } else {
                    fab.show(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 获取route数据
     */
    private void getRouteData() {
        routes = (ArrayList<Route>) TTTApplication.getDbHelper().getRoutsList();
    }

    @Override
    public void update() {
    }

    @Override
    public void refresh() {
        toDo(PREPARE_LOADING, 200);
    }

    @Override
    public void prepareLoading() {

        if (mActivity != null && mAdapter != null) {
            mActivity.startUpdate();
            mAdapter.clearData();
            toDo(ON_LOADING, 600);
        }
    }

    @Override
    public void onLoading() {
        getRouteData();
        toDo(LOADING_SUCCESS, 0);
    }

    @Override
    public void LoadingSuccess() {
        mAdapter.setData(routes);
        mActivity.finishUpdate();
    }

    @Override
    public void LoadingError() {

    }

    @Override
    public void onLoadingMore() {

    }

    @Override
    public void LoadingMoreSuccess() {

    }

    @Override
    public void LoadingMoreError() {

    }
}
