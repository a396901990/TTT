package com.dean.travltotibet.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.loadmore.LoadMoreListView;

/**
 * Created by DeanGuo on 8/31/15.
 */
public class BaseRefreshFragment extends BaseLoadingFragment implements LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static final int STATE_REFRESH = 999;// 下拉刷新

    public static final int STATE_MORE = 998;// 加载更多

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private LoadMoreListView mLoadMoreListView;

    private LoadingBackgroundManager loadingBackgroundManager;

    /**
     * 必须包含content_view
     */
    public void initLoadingBackground(View root) {
        ViewGroup contentView = (ViewGroup) root.findViewById(R.id.content_view);
        loadingBackgroundManager = new LoadingBackgroundManager(getActivity(), contentView);
    }

    @Override
    public void onResume() {
        if (getActivity() != null && getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setOnRefreshListener(this);
        }

        if (getActivity() != null && getLoadMoreListView() != null) {
            getLoadMoreListView().setOnLoadMoreListener(this);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (getActivity() != null && getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(false);
            getSwipeRefreshLayout().setOnRefreshListener(null);
        }

        if (getActivity() != null && getLoadMoreListView() != null) {
            getLoadMoreListView().onLoadMoreComplete();
            getLoadMoreListView().setOnLoadMoreListener(null);
        }
        super.onDestroy();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        this.mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.half_dark_gray));
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public LoadMoreListView getLoadMoreListView() {
        return mLoadMoreListView;
    }

    public void setLoadMoreListView(LoadMoreListView mLoadMoreListView) {
        this.mLoadMoreListView = mLoadMoreListView;
        this.mLoadMoreListView.setOnLoadMoreListener(this);
    }

    public void startRefresh() {
        if (getActivity() != null && getSwipeRefreshLayout() != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    public void finishRefresh() {
        if (getActivity() != null && getSwipeRefreshLayout() != null && getSwipeRefreshLayout().isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public LoadingBackgroundManager getLoadingBackgroundManager() {
        return loadingBackgroundManager;
    }

    @Override
    public void onLoadMore() {
        if (getActivity() == null || getLoadMoreListView() == null){
            return;
        }
    }

    public void onUpdate() {
        if (getActivity() == null){
            return;
        }
    }

    @Override
    public void onRefresh() {
        if (getActivity() == null){
            return;
        }
    }

}
