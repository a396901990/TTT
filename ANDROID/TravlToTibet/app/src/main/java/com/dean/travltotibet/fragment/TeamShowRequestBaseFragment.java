package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.TeamShowRequestSearchActivity;
import com.dean.travltotibet.adapter.TeamRequestListAdapter;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/4/16.
 * 子类需要重写getTeamRequests,prepareLoadingWork
 */
public abstract class TeamShowRequestBaseFragment extends RefreshFragment implements LoadMoreListView.OnLoadMoreListener {

    protected View root;
    protected TeamRequestListAdapter mAdapter;
    protected ArrayList<TeamRequest> teamRequests;
    protected LoadMoreListView loadMoreListView;

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected int limit = 8;        // 每页的数据是6条

    // 获取数据
    protected abstract void getTeamRequests(final int actionType);
    // 获取准备数据
    protected abstract void prepareLoadingWork();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.team_show_request_type_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        loadMoreListView = (LoadMoreListView) root.findViewById(R.id.team_request_fragment_list_rv);

        setUpList();
        initRefresh();
        refresh();
    }

    private void initRefresh() {

        // 解决listview，mSwipeRefreshLayout冲突
        loadMoreListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (loadMoreListView == null || loadMoreListView.getChildCount() == 0) ? 0 : loadMoreListView.getChildAt(0).getTop();
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                }
            }
        });

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
        mAdapter = new TeamRequestListAdapter(getActivity());
        mAdapter.setIsPersonal(isPersonal());
        loadMoreListView.setAdapter(mAdapter);
        loadMoreListView.setOnLoadMoreListener(this);
    }

    /**
     * 更新recentRoutes数据
     */
    public void updateData() {
        View noResultView = root.findViewById(R.id.no_result_content);

        // 无数据
        if (teamRequests == null || teamRequests.size() == 0) {
            noResultView.setVisibility(View.VISIBLE);
        }
        // 有数据
        else {
            noResultView.setVisibility(View.GONE);
        }
        mAdapter.setData(teamRequests);
        finishUpdate();
    }

    @Override
    public void update() {
    }

    @Override
    public void refresh() {
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {

        View noResultView = root.findViewById(R.id.no_result_content);
        noResultView.setVisibility(View.GONE);

        if (getActivity() != null && mAdapter != null) {
            startUpdate();
            mAdapter.clearData();
        }

        prepareLoadingWork();
    }

    @Override
    public void onLoading() {
        getTeamRequests(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        updateData();
    }

    @Override
    public void LoadingError() {
        updateData();
    }

    @Override
    public void onLoadingMore() {
        getTeamRequests(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        if (mAdapter != null) {
            mAdapter.addData(teamRequests);
        }
        if (loadMoreListView != null) {
            loadMoreListView.onLoadMoreComplete();
        }
    }

    @Override
    public void LoadingMoreError() {
        if (loadMoreListView != null) {
            loadMoreListView.onLoadMoreComplete();
        }
    }

    public void startUpdate() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void finishUpdate() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoadMore() {
        toDo(ON_LOADING_MORE, 800);
    }

    abstract public boolean isPersonal();
}
