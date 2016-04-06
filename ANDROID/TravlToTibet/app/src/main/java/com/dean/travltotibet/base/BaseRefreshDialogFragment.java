package com.dean.travltotibet.base;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;

import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.loadmore.LoadMoreListView;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseRefreshDialogFragment extends DialogFragment  implements LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public final static int PREPARE_LOADING = 0;

    public final static int ON_LOADING = 1;

    public final static int LOADING_SUCCESS = 2;

    public final static int LOADING_ERROR = 3;

    public final static int ON_LOADING_MORE = 4;

    public final static int LOADING_MORE_SUCCESS = 5;

    public final static int LOADING_MORE_ERROR = 6;

    public static final int STATE_REFRESH = 999;// 下拉刷新
    public static final int STATE_MORE = 998;// 加载更多

    private Handler mHandle;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private LoadMoreListView mLoadMoreListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case PREPARE_LOADING:
                        prepareLoading();
                        break;
                    case ON_LOADING:
                        onLoading();
                        break;
                    case LOADING_SUCCESS:
                        LoadingSuccess();
                        break;
                    case LOADING_ERROR:
                        LoadingError();
                        break;
                    case ON_LOADING_MORE:
                        onLoadingMore();
                        break;
                    case LOADING_MORE_SUCCESS:
                        LoadingMoreSuccess();
                        break;
                    case LOADING_MORE_ERROR:
                        LoadingMoreError();
                        break;
                }
            }
        };
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

    public void toDo(int message, long delayed) {
        if (getActivity() != null && mHandle != null) {
            mHandle.sendEmptyMessageDelayed(message, delayed);
        }
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

    public void prepareLoading() {
        if (getActivity() == null){
            return;
        }
    }

    public void onLoading() {
        if (getActivity() == null){
            return;
        }
    }

    public void LoadingSuccess() {
        if (getActivity() == null){
            return;
        }
    }

    public void LoadingError() {
        if (getActivity() == null){
            return;
        }
    }

    public void onLoadingMore() {
        if (getActivity() == null){
            return;
        }
    }

    public void LoadingMoreSuccess() {
        if (getActivity() == null){
            return;
        }
    }

    public void LoadingMoreError() {
        if (getActivity() == null){
            return;
        }
    }

}
