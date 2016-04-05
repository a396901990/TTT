package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import com.dean.travltotibet.model.TeamRequest;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/4/16.
 */
public class TeamRequestSearchFragment extends TeamShowRequestBaseFragment {

    private String searchFilter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onRefresh();
    }

    public void getTeamRequests(final int actionType) {
        teamRequests = new ArrayList<>();

        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("status", TeamRequest.PASS_STATUS);
        query.include("imageFile");
        String filterText = getSearchFilter();
        if (!TextUtils.isEmpty(filterText)) {
            // destination
            BmobQuery<TeamRequest> destination = new BmobQuery<TeamRequest>();
            destination.addWhereContains("destination", filterText);
            // type
            BmobQuery<TeamRequest> type = new BmobQuery<TeamRequest>();
            type.addWhereContains("type", filterText);
            // content
            BmobQuery<TeamRequest> content = new BmobQuery<TeamRequest>();
            content.addWhereContains("content", filterText);
            // date
            BmobQuery<TeamRequest> date = new BmobQuery<TeamRequest>();
            date.addWhereContains("date", filterText);

            // queries
            List<BmobQuery<TeamRequest>> queries = new ArrayList<BmobQuery<TeamRequest>>();
            queries.add(destination);
            queries.add(type);
            queries.add(content);
            queries.add(date);

            // 添加or查询
            query.or(queries);
        }

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
        toDo(ON_LOADING, 800);
    }

    @Override
    public boolean isPersonal() {
        return false;
    }

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }
}
