package com.dean.travltotibet.fragment;

import com.dean.travltotibet.model.ArticleComment;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.ui.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 2/18/16.
 */
public class ArticleCommentFragment extends BaseCommentFragment {

    @Override
    public String getCommentType() {
        return Comment.ARTICLE_COMMENT;
    }

    @Override
    public void getCommentData(final int actionType) {

        BmobQuery<ArticleComment> query = new BmobQuery<>();
        query.addWhereEqualTo("article_id", mActivity.getObj().getObjectId());

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

        query.findObjects(getActivity(), new FindListener<ArticleComment>() {
            @Override
            public void onSuccess(List<ArticleComment> list) {

                ArrayList<Comment> mComments = new ArrayList<Comment>();
                for (ArticleComment articleComment : list) {
                    Comment comment = articleComment;
                    mComments.add(comment);
                }
                setComments(mComments);

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
                getDataFailed();
            }
        });
    }
}
