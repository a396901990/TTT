package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.greendao.RecentRoute;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.RecentAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 10/10/15.
 */
public class HomeRecentFragment extends RefreshFragment {

    private View root;
    private RecentAdapter mAdapter;
    private ArrayList<RecentRoute> recentRoutes;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public HomeRecentFragment() {
    }

    public static HomeRecentFragment newInstance() {
        HomeRecentFragment fragment = new HomeRecentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_recent_layout, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recent_fragment_list_rv);

        initRefreshView();
        setUpList();
        refresh();
        // initFabBtn();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void initRefreshView() {
        // 设置下拉刷新
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.half_dark_gray));
        //mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void setUpList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mAdapter = new RecentAdapter(getActivity());
        mAdapter.setRecentCallBack(new RecentAdapter.RecentCallBack() {
            @Override
            public void update() {
                refresh();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 更新recentRoutes数据
     */
    public void updateRecentData() {
        if (mAdapter == null || getActivity() == null) {
            return;
        }
        View noResultView = root.findViewById(R.id.no_result_content);
        if (noResultView == null) {
            return;
        }

        // 无数据
        if (recentRoutes == null || recentRoutes.size() == 0) {
            noResultView.setVisibility(View.VISIBLE);
        }
        // 有数据
        else {
            noResultView.setVisibility(View.GONE);
        }
        mAdapter.setData(recentRoutes);
        finishUpdate();
    }

    /**
     * 获取最近显示数据
     */
    private void getRecentData() {
        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
        if (recentRoutes != null) {
            toDo(LOADING_SUCCESS, 0);
        } else {
            toDo(LOADING_ERROR, 0);
        }
    }

    @Override
    public void update() {
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void refresh() {
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {

        View noResultView = root.findViewById(R.id.no_result_content);
        noResultView.setVisibility(View.GONE);

        if (mAdapter != null) {
            startUpdate();
            mAdapter.clearData();
            toDo(ON_LOADING, 800);
        }
    }

    @Override
    public void onLoading() {
        getRecentData();
    }

    @Override
    public void LoadingSuccess() {
        updateRecentData();
    }

    @Override
    public void LoadingError() {
        updateRecentData();
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

    public void startUpdate() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void finishUpdate() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
