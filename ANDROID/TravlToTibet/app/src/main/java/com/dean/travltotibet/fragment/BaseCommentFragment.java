package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.BaseCommentActivity;
import com.dean.travltotibet.adapter.CommonCommentListAdapter;
import com.dean.travltotibet.model.Comment;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by DeanGuo on 3/3/16.
 * 调用此fragment，父activity必须继承自CommentBaseActivity
 * 子类必须实现getCommentData，getCommentType
 */
public abstract class BaseCommentFragment extends Fragment {

    private View root;

    public BaseCommentActivity mActivity;

    private CommonCommentListAdapter commentListAdapter;

    private ArrayList<Comment> mComments;

    private final static int HOT_COMMENT = 0;

    private final static int NEW_COMMENT = 1;

    private int currentTab = 0;

    private View noResultView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.article_comment_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (BaseCommentActivity) this.getActivity();
        noResultView = root.findViewById(R.id.no_result_content);
        noResultView.setVisibility(View.GONE);
        listView = (ListView) root.findViewById(R.id.comment_list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);

        initCommentView();
        initBtn();
        initRefresh();
    }

    private void initRefresh() {

        // 解决listview，mSwipeRefreshLayout冲突
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ? 0 : listView.getChildAt(0).getTop();
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
        Collections.sort(mComments, Comment.likeComparator);
        setComments();
    }

    public void showNewComment() {

        currentTab = NEW_COMMENT;

        final View hotLine = root.findViewById(R.id.hot_line);
        final View newLine = root.findViewById(R.id.new_line);
        newLine.setVisibility(View.VISIBLE);
        hotLine.setVisibility(View.GONE);
        Collections.sort(mComments, Comment.timeComparator);
        setComments();
    }

    private void initCommentView() {
        commentListAdapter = new CommonCommentListAdapter(getActivity());
        commentListAdapter.setCommentType(getCommentType());
        listView.setAdapter(commentListAdapter);
        refresh();
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

    public void getDataSuccess(ArrayList<Comment> comments) {
        mSwipeRefreshLayout.setRefreshing(false);
        mComments = comments;
        if (mComments == null) {
            return;
        }
        setCommentTab();
    }

    public void getDataFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
        if (mComments == null) {
            return;
        }
        setCommentTab();
    }

    public void updateComment() {
        // 更新后显示最新评论
        currentTab = NEW_COMMENT;
        refresh();
    }

    public void setCommentTab() {
        switch (currentTab) {
            case HOT_COMMENT:
                showHotComment();
                break;
            case NEW_COMMENT:
                showNewComment();
                break;
            default:
                break;
        }
    }

    public void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getCommentData();
    }

    // 获取评论类型
    public abstract String getCommentType();

    // 获取评论数据
    public abstract void getCommentData();
}
