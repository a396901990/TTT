package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.adapter.ArticleListAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.ui.loadmore.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 2/16/16.
 */
public class HomeTopicFragment extends BaseRefreshFragment {

    private View root;
    private ArticleListAdapter mAdapter;
    private ArrayList<Article> articles;
    private HomeActivity mActivity;
    private LoadMoreListView loadMoreListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private LoadingBackgroundManager loadingBackgroundManager;

    private int limit = 6;        // 每页的数据是6条

    public HomeTopicFragment() {
    }

    public static HomeTopicFragment newInstance() {
        HomeTopicFragment fragment = new HomeTopicFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_topic_layout, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) getActivity();

        initLoadingBackground();
        initRefreshView();
        setUpList();
        onRefresh();
    }

    private void initLoadingBackground() {
        ViewGroup contentView = (ViewGroup) root.findViewById(R.id.content_view);
        loadingBackgroundManager = new LoadingBackgroundManager(getActivity(), contentView);
    }

    private void initRefreshView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        setSwipeRefreshLayout(mSwipeRefreshLayout);
    }

    private void setUpList() {
        mAdapter = new ArticleListAdapter(getActivity());

        loadMoreListView = (LoadMoreListView) root.findViewById(R.id.article_fragment_list_rv);


        // 解决listview，mSwipeRefreshLayout冲突
        loadMoreListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (loadMoreListView == null || loadMoreListView.getChildCount() == 0) ? 0 : loadMoreListView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });

        loadMoreListView.setAdapter(mAdapter);
        setLoadMoreListView(loadMoreListView);
    }

    private void getArticles(final int actionType) {

        articles = new ArrayList<>();

        BmobQuery<Article> query = new BmobQuery<>();
        query.order("id");
        query.addWhereNotEqualTo("isDisplay", false);
        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(mAdapter.getCount());
        }

        // 设置每页数据个数
        query.setLimit(limit);

        query.findObjects(getActivity(), new FindListener<Article>() {
            @Override
            public void onSuccess(List<Article> list) {
                articles = (ArrayList<Article>) list;

                if (list.size() == 0 && actionType == STATE_MORE) {
                    loadMoreListView.onNoMoreDate();
                } else {
                    if (actionType == STATE_REFRESH) {
                        toDo(LOADING_SUCCESS, 0);
                    } else {
                        toDo(LOADING_MORE_SUCCESS, 0);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

                if (actionType == STATE_REFRESH) {
                    toDo(LOADING_ERROR, 0);
                } else {
                    toDo(LOADING_MORE_ERROR, 0);
                }
            }
        });
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

        if (mActivity != null && mAdapter != null) {
            startRefresh();
            mAdapter.clearData();
            toDo(ON_LOADING, 800);
        }
    }

    @Override
    public void onLoading() {
        super.onLoading();
        getArticles(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();

        // 无数据
        if (articles == null || articles.size() == 0) {
            loadingBackgroundManager.loadingFaild(getString(R.string.no_result), null);
        }
        if (mAdapter != null) {
            mAdapter.setData(articles);
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
        finishRefresh();
        getArticles(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        super.LoadingMoreSuccess();
        if (mAdapter != null) {
            mAdapter.addData(articles);
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
}
