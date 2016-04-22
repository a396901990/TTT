package com.dean.travltotibet.fragment;

import android.content.Intent;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.model.TeamRequest;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/4/16.
 */
public class TeamRequestMyselfFragment extends TeamShowRequestBaseFragment {

    public void getTeamRequests(final int actionType) {

        teamRequests = new ArrayList<>();

        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.order("-createdAt");
        String userId = TTTApplication.getUserInfo() == null ? "no_result" : TTTApplication.getUserInfo().getUserId();
        query.addWhereEqualTo("userId", userId);
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

    @Override
    public void onRefresh() {
        if (TTTApplication.getUserInfo() == null) {
            finishRefresh();
            return;
        }
        super.onRefresh();
    }

    @Override
    protected void prepareLoadingWork() {
        toDo(ON_LOADING, 800);
    }

    @Override
    public boolean isPersonal() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BaseActivity.UPDATE_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                onRefresh();
            }
        }
    }

}
