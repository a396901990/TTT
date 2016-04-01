package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.TeamRequestListAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.loadmore.LoadMoreListView;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 3/4/16.
 * 子类需要重写getTeamRequests,prepareLoadingWork
 */
public abstract class TeamShowRequestBaseFragment extends BaseRefreshFragment implements LoadMoreListView.OnLoadMoreListener {

    protected View root;
    protected TeamRequestListAdapter mAdapter;
    protected ArrayList<TeamRequest> teamRequests;
    protected LoadMoreListView loadMoreListView;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadingBackgroundManager loadingBackgroundManager;

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

        initLoadingBackground();
        setUpList();
        initRefresh();
        onRefresh();
    }

    private void initLoadingBackground() {
        ViewGroup contentView = (ViewGroup) root.findViewById(R.id.content_view);
        loadingBackgroundManager = new LoadingBackgroundManager(getActivity(), contentView);
    }

    private void initRefresh() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        setSwipeRefreshLayout(mSwipeRefreshLayout);

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
    }

    private void setUpList() {
        loadMoreListView = (LoadMoreListView) root.findViewById(R.id.team_request_fragment_list_rv);
        mAdapter = new TeamRequestListAdapter(getActivity());
        mAdapter.setIsPersonal(isPersonal());
        loadMoreListView.setAdapter(mAdapter);
        loadMoreListView.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        super.prepareLoading();
        loadingBackgroundManager.resetLoadingView();

        if (getActivity() != null && mAdapter != null) {
            startRefresh();
            mAdapter.clearData();
        }

        prepareLoadingWork();
    }

    @Override
    public void onLoading() {
        super.onLoading();
        getTeamRequests(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();

        // 无数据
        if (teamRequests == null || teamRequests.size() == 0) {
            loadingBackgroundManager.loadingFaild(getString(R.string.no_result), null);
        }

        if (mAdapter != null) {
            mAdapter.setData(teamRequests);
        }
        finishRefresh();
    }

    @Override
    public void LoadingError() {
        super.LoadingError();
        loadingBackgroundManager.loadingFaild(getString(R.string.network_no_result), new LoadingBackgroundManager.LoadingRetryCallBack() {
            @Override
            public void retry() {
                onRefresh();
            }
        });
        finishRefresh();
    }

    @Override
    public void onLoadingMore() {
        super.onLoadingMore();
        getTeamRequests(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        super.LoadingMoreSuccess();
        if (mAdapter != null) {
            mAdapter.addData(teamRequests);
        }
        if (loadMoreListView != null) {
            loadMoreListView.onLoadMoreComplete();
        }
    }

    @Override
    public void LoadingMoreError() {
        super.LoadingMoreError();
        if (loadMoreListView != null) {
            loadMoreListView.onLoadMoreComplete();
        }
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        toDo(ON_LOADING_MORE, 800);
    }

    abstract public boolean isPersonal();
}
