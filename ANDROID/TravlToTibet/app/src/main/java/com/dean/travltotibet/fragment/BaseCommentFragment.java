package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.adapter.CommonCommentListAdapter;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.ui.LoadMoreListView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by DeanGuo on 3/3/16.
 * 调用此fragment，父activity必须继承自CommentBaseActivity
 * 子类必须实现getCommentData，getCommentType
 */
public abstract class BaseCommentFragment extends RefreshFragment  implements LoadMoreListView.OnLoadMoreListener  {

    private View root;

    public BaseCommentActivity mActivity;

    private CommonCommentListAdapter commentListAdapter;

    private ArrayList<Comment> mComments;

    public final static int HOT_COMMENT = 0;

    public final static int NEW_COMMENT = 1;

    public final static int COMMENT_LIMIT = 6;        // 每页的数据是6条

    private int currentTab = 0;

    private View noResultView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private LoadMoreListView loadMoreListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.base_comment_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (BaseCommentActivity) this.getActivity();
        noResultView = root.findViewById(R.id.no_result_content);
        noResultView.setVisibility(View.GONE);
        loadMoreListView = (LoadMoreListView) root.findViewById(R.id.comment_list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);

        initCommentView();
        initBtn();
        initRefresh();
        showHotComment();
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
                mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
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

    private void initBtn() {
        View hotComment = root.findViewById(R.id.hot_comment);
        View newComment = root.findViewById(R.id.new_comment);

        hotComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHotComment();
            }
        });

        newComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewComment();
            }
        });
    }

    public void showHotComment() {

        currentTab = HOT_COMMENT;

        final View hotLine = root.findViewById(R.id.hot_line);
        final View newLine = root.findViewById(R.id.new_line);
        hotLine.setVisibility(View.VISIBLE);
        newLine.setVisibility(View.GONE);
        refresh();
//        Collections.sort(mComments, Comment.likeComparator);
//        setComments();
    }

    public void showNewComment() {

        currentTab = NEW_COMMENT;

        final View hotLine = root.findViewById(R.id.hot_line);
        final View newLine = root.findViewById(R.id.new_line);
        newLine.setVisibility(View.VISIBLE);
        hotLine.setVisibility(View.GONE);
        refresh();
//        Collections.sort(mComments, Comment.timeComparator);
//        setComments();
    }

    private void initCommentView() {
        commentListAdapter = new CommonCommentListAdapter(getActivity());
        commentListAdapter.setCommentType(getCommentType());
        loadMoreListView.setAdapter(commentListAdapter);
        loadMoreListView.setOnLoadMoreListener(this);
    }

    public void setComments() {

        // 无数据
        if (mComments == null || mComments.size() == 0) {
            noResultView.setVisibility(View.VISIBLE);
        }
        // 有数据
        else {
            noResultView.setVisibility(View.GONE);
        }

        commentListAdapter.setData(mComments);
    }

    public void getDataSuccess() {
        setComments();
        finishUpdate();
    }

    public void getDataFailed() {
        setComments();
        finishUpdate();
    }

    public CommonCommentListAdapter getCommentListAdapter() {
        return commentListAdapter;
    }

    public void startUpdate() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void finishUpdate() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public LoadMoreListView getLoadMoreListView() {
        return loadMoreListView;
    }

    public void setComments(ArrayList<Comment> mComments) {
        this.mComments = mComments;
    }

    // 获取评论类型
    public abstract String getCommentType();

    // 获取评论数据
    public abstract void getCommentData(final int actionType);


    @Override
    public void update() {

    }

    @Override
    public void refresh() {
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        if (commentListAdapter != null) {
            startUpdate();
            commentListAdapter.clearData();
            toDo(ON_LOADING, 800);
        }
    }

    @Override
    public void onLoading() {
        getCommentData(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        getDataSuccess();
    }

    @Override
    public void LoadingError() {
        getDataFailed();
    }

    @Override
    public void onLoadMore() {
        toDo(ON_LOADING_MORE, 800);
    }

    @Override
    public void onLoadingMore() {
        getCommentData(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        if (commentListAdapter != null) {
            commentListAdapter.addData(mComments);
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

    public int getCurrentTab() {
        return currentTab;
    }

}
