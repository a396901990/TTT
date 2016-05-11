package com.dean.travltotibet.fragment;

import android.util.Log;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
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
 * Created by DeanGuo on 3/4/16.
 */
public class TeamRequestPublishFragment extends TeamShowRequestBaseFragment {

    public void getTeamRequests(final int actionType) {
//        moveToUser();
        teamRequests = new ArrayList<>();

        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.addWhereRelatedTo("TeamRequest", new BmobPointer(TTTApplication.getUserInfo()));
        query.order("-createdAt");
        query.include("imageFile");

        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(mAdapter.getCount());
        }

        // 设置每页数据个数
        query.setLimit(limit);

        query.findObjects(getActivity(), new FindListener<TeamRequest>() {
            @Override
            public void onSuccess(List<TeamRequest> list) {
                teamRequests = (ArrayList<TeamRequest>) list;

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

    /**
     * 配合老逻辑手工添加
     */
    private void moveToUser() {
        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.include("user");
//        query.setSkip(100);
        query.findObjects(getActivity(), new FindListener<TeamRequest>() {
            @Override
            public void onSuccess(List<TeamRequest> list) {
                for (final TeamRequest t : list) {
                    if (t.getUserId() != null) {
                        BmobUser.loginByAccount(TTTApplication.getContext(), t.getUserId(), LoginUtil.DEFAULT_PASSWORD, new LogInListener<UserInfo>() {

                            @Override
                            public void done(UserInfo user, BmobException e) {
                                if (user != null) {
                                    addToUserTR(user, t.getObjectId());
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void addToUserTR(final UserInfo userInfo, String id) {

        final BmobRelation relation = new BmobRelation();
        TeamRequest teamRequest = new TeamRequest();
        teamRequest.setObjectId(id);
        relation.add(teamRequest);
        userInfo.setTeamRequest(relation);
        userInfo.update(getActivity(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.e("userId", userInfo.getUserName());
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    @Override
    public void onRefresh() {
        if (TTTApplication.getUserInfo() == null) {
            finishRefresh();
            return;
        }
        super.onRefresh();
    }

    @Override
    public boolean isPersonal() {
        return true;
    }

}
