package com.dean.travltotibet.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.ReplyCommentListAdapter;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.model.BaseCommentBmobObject;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.customScrollView.InsideScrollLoadMorePressListView;
import com.dean.travltotibet.util.LoginUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 3/3/16.
 * 调用此fragment，父activity必须继承自CommentBaseActivity
 * 子类必须实现getCommentTypeObjectId，getCommentType
 */
public abstract class NewBaseInsideCommentFragment extends BaseRefreshFragment  implements InsideScrollLoadMorePressListView.OnLoadMoreListener  {

    private View root;

    private ReplyCommentListAdapter commentListAdapter;

    private ArrayList<Comment> mComments;

    public final static int COMMENT_LIMIT = 12;        // 每页的数据是8条

    private InsideScrollLoadMorePressListView loadMoreListView;

    private LoadingBackgroundManager loadingBackgroundManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.base_inside_comment_fragment_view, null);
        return root;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadMoreListView = (InsideScrollLoadMorePressListView) root.findViewById(R.id.comment_list_view);

        initLoadingBackground();
        initCommentView();
        onRefresh();
    }

    private void initLoadingBackground() {
        ViewGroup contentView = (ViewGroup) root.findViewById(R.id.content_view);
        loadingBackgroundManager = new LoadingBackgroundManager(getActivity(), contentView);
    }

    private void initCommentView() {
        commentListAdapter = new ReplyCommentListAdapter(getActivity());
        commentListAdapter.setCommentType(getCommentType());
        loadMoreListView.setAdapter(commentListAdapter);
        loadMoreListView.setOnLoadMoreListener(this);
    }

    public ReplyCommentListAdapter getCommentListAdapter() {
        return commentListAdapter;
    }

    public InsideScrollLoadMorePressListView getLoadMorePressListView() {
        return loadMoreListView;
    }

    public void setComments(ArrayList<Comment> mComments) {
        this.mComments = mComments;
    }

    // 获取评论类型
    public abstract String getCommentType();

    public abstract BaseCommentBmobObject getCommentObject();

    // 获取评论数据
    public void getCommentData(final int actionType) {

        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereRelatedTo("replyComments", new BmobPointer(getCommentObject()));
        query.include("commentQuote,user");
        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(getCommentListAdapter().getCount());
        }

        // 设置每页数据个数
        query.setLimit(COMMENT_LIMIT);

        query.order("-like,-createdAt");

        query.findObjects(getActivity(), new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                if (getActivity() == null) {
                    return;
                }
                setComments((ArrayList<Comment>) list);

                if (list.size() < COMMENT_LIMIT) {
                    getLoadMorePressListView().onNoMoreDate();
                } else {
                    getLoadMorePressListView().hasMoreDate();
                }

                if (actionType == STATE_REFRESH) {
                    toDo(LOADING_SUCCESS, 0);
                } else {
                    toDo(LOADING_MORE_SUCCESS, 0);
                }
            }

            @Override
            public void onError(int i, String s) {
                if (getActivity() == null) {
                    return;
                }
                if (TextUtils.isEmpty(s)) {
                    toDo(LOADING_SUCCESS, 0);
                } else {
                    toDo(LOADING_ERROR, 0);
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

        loadingBackgroundManager.showLoadingView();

        if (commentListAdapter != null) {
            commentListAdapter.clearData();
            toDo(ON_LOADING, 800);
        }
    }

    @Override
    public void onLoading() {
        super.onLoading();
        getCommentData(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();
        // 无数据
        Log.e(" mComments.size()",  mComments.size()+"");
        if (mComments == null || mComments.size() == 0) {
            loadingBackgroundManager.loadingNoResultView(getString(R.string.no_comment_result));
//            loadingBackgroundManager.loadingSuccess();
        }
        // 有数据
        else {
            loadingBackgroundManager.loadingSuccess();
            commentListAdapter.setData(mComments);
        }
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
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        toDo(ON_LOADING_MORE, 800);
    }

    @Override
    public void onLoadingMore() {
        super.onLoadingMore();
        getCommentData(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        super.LoadingMoreSuccess();
        if (commentListAdapter != null) {
            commentListAdapter.addData(mComments);
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
}
