package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.TeamRequestActivity;
import com.dean.travltotibet.adapter.TeamRequestAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.util.LoginUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 3/4/16.
 */
public class TeamShowPersonalRequestFragment extends BaseHomeFragment {

    private View root;
    private TeamRequestAdapter mAdapter;
    private ArrayList<TeamRequest> teamRequests;
    private TeamRequestActivity mActivity;
    private RecyclerView mRecyclerView;

    private View loginView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.team_show_personal_request_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);

        mActivity = (TeamRequestActivity) getActivity();
        loginView = root.findViewById(R.id.login_view);

        setUpList();
        settingViewStatus();
    }

    private void initLoginView() {
        View loginContent = root.findViewById(R.id.login_content);
        loginContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new LoginDialog();
                dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
            }
        });
    }

    private void settingViewStatus() {
        if (TTTApplication.hasLoggedIn()) {
            loginView.setVisibility(View.GONE);
            getTeamRequests();
        } else {
            loginView.setVisibility(View.VISIBLE);
            initLoginView();
        }
    }

    private void setUpList() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.team_request_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mAdapter = new TeamRequestAdapter(getActivity());
        mAdapter.setIsPersonal(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getTeamRequests() {
        teamRequests = new ArrayList<>();
        // 如果已经登录
        if (TTTApplication.hasLoggedIn()) {

            if (mActivity != null && mAdapter != null) {
                mActivity.startUpdate();
                mAdapter.clearData();
            }

            BmobQuery<TeamRequest> query = new BmobQuery<>();
            query.order("-createdAt");
            query.addWhereEqualTo("userId", TTTApplication.getUserInfo().getUserId());
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
        } else {

        }
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

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        settingViewStatus();
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }
}
