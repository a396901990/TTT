package com.dean.travltotibet.fragment;

import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.TeamRequestComment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamRequestCommentFragment extends BaseCommentFragment {

    @Override
    public String getCommentType() {
        return Comment.TEAM_REQUEST_COMMENT;
    }

    @Override
    public void getCommentData() {

        BmobQuery<TeamRequestComment> query = new BmobQuery<>();
        query.addWhereEqualTo("team_request_id", mActivity.getObj().getObjectId());
        query.findObjects(getActivity(), new FindListener<TeamRequestComment>() {
            @Override
            public void onSuccess(List<TeamRequestComment> list) {

                ArrayList<Comment> mComments = new ArrayList<Comment>();
                for (TeamRequestComment teamRequestComment : list) {
                    Comment comment = teamRequestComment;
                    mComments.add(comment);
                }
                getDataSuccess(mComments);
            }

            @Override
            public void onError(int i, String s) {
                getDataFailed();
            }
        });
    }
}
