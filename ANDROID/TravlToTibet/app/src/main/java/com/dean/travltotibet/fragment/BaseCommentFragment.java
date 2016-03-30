package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.ReplyCommentListAdapter;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.ui.loadmore.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/3/16.
 * 调用此fragment，父activity必须继承自CommentBaseActivity
 * 子类必须实现getCommentTypeObjectId，getCommentType
 */
public abstract class BaseCommentFragment extends RefreshFragment  implements LoadMoreListView.OnLoadMoreListener  {

    private View root;

    private ReplyCommentListAdapter commentListAdapter;

    private ArrayList<Comment> mComments;

    public final static int HOT_COMMENT = 0;

    public final static int NEW_COMMENT = 1;

    public final static int COMMENT_LIMIT = 8;        // 每页的数据是6条

    private int currentTab = 0;

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
        commentListAdapter = new ReplyCommentListAdapter(getActivity());
        commentListAdapter.setCommentType(getCommentType());
        loadMoreListView.setAdapter(commentListAdapter);
        loadMoreListView.setOnLoadMoreListener(this);
    }

    public void setComments() {
        View noResultView = root.findViewById(R.id.no_result_content);
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
        if (getActivity() == null) {
            return;
        }
        setComments();
        finishRefresh();
        TextView noResultText = (TextView) root.findViewById(R.id.no_result_text);
        if (noResultText != null) {
            noResultText.setText(getString(R.string.no_result));
        }
    }

    public void getDataFailed() {
        if (getActivity() == null) {
            return;
        }
        setComments();
        finishRefresh();
        TextView noResultText = (TextView) root.findViewById(R.id.no_result_text);
        if (noResultText != null) {
            noResultText.setText(getString(R.string.no_network_result));
        }
    }

    public ReplyCommentListAdapter getCommentListAdapter() {
        return commentListAdapter;
    }

    public void startRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void finishRefresh() {
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

    public abstract String getCommentTypeObjectId();

    // 获取评论数据
    public void getCommentData(final int actionType) {

        BmobQuery<Comment> query = new BmobQuery<>();
        if (!TextUtils.isEmpty(getCommentType())) {
            query.addWhereEqualTo("type", getCommentType());
        }
        if (!TextUtils.isEmpty(getCommentTypeObjectId())) {
            query.addWhereEqualTo("type_object_id", getCommentTypeObjectId());
        }
        query.include("commentQuote,user");
        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(getCommentListAdapter().getCount());
        }

        // 设置每页数据个数
        query.setLimit(COMMENT_LIMIT);

        if (BaseCommentFragment.HOT_COMMENT == getCurrentTab()) {
            query.order("-like");
        } else if (BaseCommentFragment.NEW_COMMENT == getCurrentTab()) {
            query.order("-createdAt");
        }

        query.findObjects(getActivity(), new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                if (getActivity() == null) {
                    return;
                }
                setComments((ArrayList<Comment>) list);

                if (list.size() == 0 && actionType == STATE_MORE) {
                    getLoadMoreListView().onNoMoreDate();
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
                if (getActivity() == null) {
                    return;
                }
                getDataFailed();
            }
        });
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

        if (commentListAdapter != null) {
            startRefresh();
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
