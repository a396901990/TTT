package com.dean.travltotibet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserFavorites;
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
public class TeamRequestFavoriteFragment extends TeamShowRequestBaseFragment {

    public void getTeamRequests(final int actionType) {
//        moveToFavorites();
        teamRequests = new ArrayList<>();

        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.addWhereRelatedTo("TeamFavorite", new BmobPointer(TTTApplication.getUserInfo()));
        query.addWhereEqualTo("status", TeamRequest.PASS_STATUS);
        query.include("imageFile");
        query.order("-createdAt");

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
     * 手工将旧逻辑数据添加到user中
     */
    private void moveToFavorites() {
        BmobQuery<UserFavorites> query = new BmobQuery<UserFavorites>();
        query.addWhereEqualTo("type", UserFavorites.TEAM_REQUEST);
        query.findObjects(getActivity(), new FindListener<UserFavorites>() {
            @Override
            public void onSuccess(List<UserFavorites> list) {
                for (UserFavorites f : list) {
                    String userId = f.getUserId();
                    final String id = f.getTypeObjectId();
                    BmobUser.loginByAccount(TTTApplication.getContext(), userId, LoginUtil.DEFAULT_PASSWORD, new LogInListener<UserInfo>() {

                        @Override
                        public void done(UserInfo user, BmobException e) {
                            if (user != null) {
                                addToFavorite(id, user);
                            }
                        }
                    });
                }
                toDo(ON_LOADING, 800);
            }

            @Override
            public void onError(int i, String s) {
                toDo(LOADING_ERROR, 800);
            }
        });
    }

    public void addToFavorite(String id, final UserInfo user) {

            TeamRequest teamRequest = new TeamRequest();
            teamRequest.setObjectId(id);
            BmobRelation relation = new BmobRelation();
            relation.add(teamRequest);
            user.setTeamFavorite(relation);
            user.update(getActivity(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    Log.e("userId", user.getUserName());
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
        return false;
    }

}
