package com.dean.travltotibet.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.TeamRequestActivity;
import com.dean.travltotibet.adapter.TeamRequestAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.model.TeamRequest;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamShowAllRequestFragment extends BaseHomeFragment {

    private View root;
    private TeamRequestAdapter mAdapter;
    private ArrayList<TeamRequest> teamRequests;
    private TeamRequestActivity mActivity;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.team_show_all_request_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (TeamRequestActivity) getActivity();

        setUpList();
    }

    private void setUpList() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.team_request_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mAdapter = new TeamRequestAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        getTeamRequests();
    }

    private void getTeamRequests() {
        teamRequests = new ArrayList<>();

        if (mActivity != null && mAdapter != null) {
            mActivity.startUpdate();
            mAdapter.clearData();
        }

        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("isPass", true);
        query.findObjects(getActivity(), new FindListener<TeamRequest>() {
            @Override
            public void onSuccess(List<TeamRequest> list) {
                teamRequests = (ArrayList<TeamRequest>) list;
                updateData();
            }

            @Override
            public void onError(int i, String s) {
                updateData();
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
        mActivity.finishUpdate();
    }

    @Override
    public void update() {
//        new refreshTask().execute();
    }

    @Override
    public void refresh() {
        getTeamRequests();
    }

    @Override
    public void prepareLoading() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void LoadingSuccess() {

    }

    @Override
    public void LoadingError() {

    }

}
