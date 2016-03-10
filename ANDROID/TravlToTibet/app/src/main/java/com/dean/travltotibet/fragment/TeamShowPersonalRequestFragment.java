package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.TeamPersonalRequestActivity;
import com.dean.travltotibet.adapter.TeamRequestListAdapter;
import com.dean.travltotibet.model.TeamRequest;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/4/16.
 */
public class TeamShowPersonalRequestFragment extends RefreshFragment {

    private View root;
    private TeamRequestListAdapter mAdapter;
    private ArrayList<TeamRequest> teamRequests;
    private TeamPersonalRequestActivity mActivity;
    private ListView listView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.team_show_personal_request_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (TeamPersonalRequestActivity) getActivity();
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        listView = (ListView) root.findViewById(R.id.team_request_fragment_list_rv);

        setUpList();
        initRefresh();
        refresh();
    }

    private void initRefresh() {

        // 解决listview，mSwipeRefreshLayout冲突
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ? 0 : listView.getChildAt(0).getTop();
                if(mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                }
            }
        });

        // 设置下拉刷新
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.half_dark_gray));
        //mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void setUpList() {
        mAdapter = new TeamRequestListAdapter(getActivity());
        mAdapter.setIsPersonal(true);
        listView.setAdapter(mAdapter);
    }

    private void getTeamRequests() {
        teamRequests = new ArrayList<>();

            BmobQuery<TeamRequest> query = new BmobQuery<>();
            query.order("-createdAt");
            query.addWhereEqualTo("userId", TTTApplication.getUserInfo().getUserId());
            query.findObjects(getActivity(), new FindListener<TeamRequest>() {
                @Override
                public void onSuccess(List<TeamRequest> list) {
                    teamRequests = (ArrayList<TeamRequest>) list;
                    toDo(LOADING_SUCCESS, 0);
                }

                @Override
                public void onError(int i, String s) {
                    toDo(LOADING_ERROR, 0);
                }
            });
    }

    /**
     * 更新recentRoutes数据
     */
    public void updateData() {
        View noResultView = root.findViewById(R.id.no_result_content);

        // 无数据
        if (teamRequests == null || teamRequests.size() == 0) {
            noResultView.setVisibility(View.VISIBLE);
        }
        // 有数据
        else {
            noResultView.setVisibility(View.GONE);
        }
        mAdapter.setData(teamRequests);
        mAdapter.notifyDataSetChanged();
        finishUpdate();
    }

    @Override
    public void update() {
//        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void refresh() {
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        if (mActivity != null && mAdapter != null) {
            startUpdate();
            mAdapter.clearData();
            toDo(ON_LOADING, 800);
        }
    }

    @Override
    public void onLoading() {
        getTeamRequests();
    }

    @Override
    public void LoadingSuccess() {
        updateData();
    }

    @Override
    public void LoadingError() {
        updateData();
    }

    @Override
    public void onLoadingMore() {

    }

    @Override
    public void LoadingMoreSuccess() {

    }

    @Override
    public void LoadingMoreError() {

    }

    public void startUpdate() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void finishUpdate() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
