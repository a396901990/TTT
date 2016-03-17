package com.dean.travltotibet.fragment;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserFavorites;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/4/16.
 */
public class TeamRequestFavoriteFragment extends TeamShowRequestBaseFragment {

    List<String> favorites;

    public void getTeamRequests(final int actionType) {

        teamRequests = new ArrayList<>();

        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereContainedIn("objectId", favorites);
        query.addWhereEqualTo("status", TeamRequest.PASS_STATUS);

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

    @Override
    protected void prepareLoadingWork() {
        getFavorites();
    }

    private void getFavorites() {
        BmobQuery<UserFavorites> query = new BmobQuery<UserFavorites>();
        query.addWhereEqualTo("userId", TTTApplication.getUserInfo().getUserId());
        query.addWhereEqualTo("type", UserFavorites.TEAM_REQUEST);
        query.findObjects(getActivity(), new FindListener<UserFavorites>() {
            @Override
            public void onSuccess(List<UserFavorites> list) {
                favorites = new ArrayList<String>();
                for (UserFavorites f : list) {
                    favorites.add(f.getTypeObjectId());
                }
                toDo(ON_LOADING, 800);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void refresh() {
        if (TTTApplication.getUserInfo() == null) {
            finishUpdate();
            return;
        }
        super.refresh();
    }

    @Override
    public boolean isPersonal() {
        return false;
    }

}
