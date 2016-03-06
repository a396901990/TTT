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
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.TeamRequestActivity;
import com.dean.travltotibet.adapter.TeamRequestAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class HomeTeamRequestFragment extends BaseHomeFragment {

    private View root;
    private TeamRequestAdapter mAdapter;
    private ArrayList<TeamRequest> teamRequests;
    private HomeActivity mActivity;
    private RecyclerView mRecyclerView;

    public static HomeTeamRequestFragment newInstance() {
        HomeTeamRequestFragment fragment = new HomeTeamRequestFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_team_request_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) getActivity();

        setUpList();
    }

    private void setUpList() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.team_request_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));
        mAdapter = new TeamRequestAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        refresh();
    }

    private void getTeamRequests() {
        teamRequests = new ArrayList<>();

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
        new refreshTask().execute();
    }

    private class refreshTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            if (mActivity != null && mAdapter != null) {
                mActivity.startUpdate();
                mAdapter.clearData();
            }
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            getTeamRequests();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

}
