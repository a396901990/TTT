package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.QAShowRequestDetailActivity;
import com.dean.travltotibet.adapter.AnswerListAdapter;
import com.dean.travltotibet.adapter.ReplyCommentListAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.model.AnswerInfo;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.customScrollView.InsideScrollLoadMorePressListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 5/3/16.
 */
public class QAnswerFragment extends BaseRefreshFragment  implements InsideScrollLoadMorePressListView.OnLoadMoreListener  {

    private View root;

    private AnswerListAdapter commentListAdapter;

    private ArrayList<AnswerInfo> mComments;

    public final static int COMMENT_LIMIT = 8;        // 每页的数据是8条

    private InsideScrollLoadMorePressListView loadMoreListView;

    private LoadingBackgroundManager loadingBackgroundManager;

    private QARequest qaRequest;

    private QAShowRequestDetailActivity qaShowRequestDetailActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.answer_fragment_view, null);
        return root;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadMoreListView = (InsideScrollLoadMorePressListView) root.findViewById(R.id.comment_list_view);

        qaShowRequestDetailActivity = (QAShowRequestDetailActivity) getActivity();
        qaRequest = qaShowRequestDetailActivity.getQaRequest();

        initLoadingBackground();
        initCommentView();
        onRefresh();
    }

    private void initLoadingBackground() {
        ViewGroup contentView = (ViewGroup) root.findViewById(R.id.content_view);
        loadingBackgroundManager = new LoadingBackgroundManager(getActivity(), contentView);
    }

    private void initCommentView() {
        commentListAdapter = new AnswerListAdapter(getActivity());
        loadMoreListView.setAdapter(commentListAdapter);
        loadMoreListView.setOnLoadMoreListener(this);
    }

    public AnswerListAdapter getCommentListAdapter() {
        return commentListAdapter;
    }

    public InsideScrollLoadMorePressListView getLoadMorePressListView() {
        return loadMoreListView;
    }

    public void setComments(ArrayList<AnswerInfo> mComments) {
        this.mComments = mComments;
    }

    // 获取评论数据
    public void getCommentData(final int actionType) {

        BmobQuery<AnswerInfo> query = new BmobQuery<>();
        query.addWhereRelatedTo("answers", new BmobPointer(qaRequest));
        query.include("user");
        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(getCommentListAdapter().getCount());
        }

        // 设置每页数据个数
        query.setLimit(COMMENT_LIMIT);

        query.order("-like,-createdAt");

        query.findObjects(getActivity(), new FindListener<AnswerInfo>() {
            @Override
            public void onSuccess(List<AnswerInfo> list) {
                if (getActivity() == null) {
                    return;
                }
                setComments((ArrayList<AnswerInfo>) list);

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
        if (mComments == null || mComments.size() == 0) {
//            loadingBackgroundManager.loadingFaild(getString(R.string.no_comment_result), null);
            loadingBackgroundManager.loadingSuccess();
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
