package com.dean.travltotibet.fragment;

import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.TeamRequestComment;
import com.dean.travltotibet.ui.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamShowRequestCommentFragment extends BaseCommentFragment {

    @Override
    public String getCommentType() {
        return Comment.TEAM_REQUEST_COMMENT;
    }

    @Override
    public void getCommentData(final int actionType) {

        BmobQuery<TeamRequestComment> query = new BmobQuery<>();
        query.addWhereEqualTo("team_request_id", mActivity.getObj().getObjectId());

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

        query.findObjects(getActivity(), new FindListener<TeamRequestComment>() {
            @Override
            public void onSuccess(List<TeamRequestComment> list) {

                ArrayList<Comment> mComments = new ArrayList<Comment>();
                for (TeamRequestComment teamRequestComment : list) {
                    Comment comment = teamRequestComment;
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
