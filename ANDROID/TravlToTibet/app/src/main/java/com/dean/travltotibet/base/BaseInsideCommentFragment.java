package com.dean.travltotibet.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.ReplyCommentListAdapter;
import com.dean.travltotibet.fragment.RefreshFragment;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.ui.loadmore.LoadMoreListView;
import com.dean.travltotibet.ui.customScrollView.InsideScrollLoadMorePressListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/3/16.
 * 调用此fragment，父activity必须继承自CommentBaseActivity
 * 子类必须实现getCommentTypeObjectId，getCommentType
 */
public abstract class BaseInsideCommentFragment extends BaseRefreshFragment  implements InsideScrollLoadMorePressListView.OnLoadMoreListener  {

    private View root;

    private ReplyCommentListAdapter commentListAdapter;

    private ArrayList<Comment> mComments;

    public final static int COMMENT_LIMIT = 8;        // 每页的数据是8条

    private InsideScrollLoadMorePressListView loadMoreListView;

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
        initCommentView();
        onRefresh();
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
            noResultText.setText(getString(R.string.no_comment_result));
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

    public InsideScrollLoadMorePressListView getLoadMorePressListView() {
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
        super.onLoading();
        getCommentData(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();
        getDataSuccess();
    }

    @Override
    public void LoadingError() {
        super.LoadingError();
        getDataFailed();
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
