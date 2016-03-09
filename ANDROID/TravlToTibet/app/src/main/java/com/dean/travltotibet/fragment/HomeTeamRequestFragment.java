package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.TeamCreateRequestActivity;
import com.dean.travltotibet.activity.TeamPersonalRequestActivity;
import com.dean.travltotibet.adapter.TeamRequestListAdapter;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.dialog.TeamRequestFilterDialog;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.util.LoginUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class HomeTeamRequestFragment extends RefreshFragment {

    private static final int CREATE_REQUEST = 0;

    private View root;
    private TeamRequestListAdapter mAdapter;
    private ArrayList<TeamRequest> teamRequests;
    private HomeActivity mActivity;
    private ListView mRecyclerView;

    private boolean tryToOpenMyTeamRequest = false;

    private String filterText;

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
        EventBus.getDefault().register(this);
        setUpList();
        initBottomView();
    }

    private void initBottomView() {
        View filterView = root.findViewById(R.id.filter_team_request);
        View addView = root.findViewById(R.id.add_view);
        View myView = root.findViewById(R.id.my_team_request);

        filterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamRequestFilterDialog dialogFragment = new TeamRequestFilterDialog();
                dialogFragment.setFilterCallback(new TeamRequestFilterDialog.FilterCallback() {
                    @Override
                    public void filterChanged(String filter) {
                        filterText = filter;
                        refresh();
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamRequestFilterDialog.class.getName());
            }
        });

        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeamCreateRequestActivity.class);
                startActivityForResult(intent, CREATE_REQUEST);
                getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
            }
        });

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TTTApplication.hasLoggedIn()) {
                    Intent intent = new Intent(getActivity(), TeamPersonalRequestActivity.class);
                    startActivity(intent);
                } else {
                    tryToOpenMyTeamRequest = true;
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }
            }
        });
    }

    private void setUpList() {
        mRecyclerView = (ListView) root.findViewById(R.id.team_request_fragment_list_rv);

        // 解决listview，mSwipeRefreshLayout冲突
        mRecyclerView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (mRecyclerView == null || mRecyclerView.getChildCount() == 0) ? 0 : mRecyclerView.getChildAt(0).getTop();
                mActivity.getSwipeRefreshLayout().setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });


        mAdapter = new TeamRequestListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        refresh();
    }

    private void getTeamRequests() {
        teamRequests = new ArrayList<>();

        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("isPass", true);
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

            // queries
            List<BmobQuery<TeamRequest>> queries = new ArrayList<BmobQuery<TeamRequest>>();
            queries.add(destination);
            queries.add(type);
            queries.add(content);

            // 添加or查询
            query.or(queries);
        }
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
        mActivity.finishUpdate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                refresh();
            }
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void refresh() {
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        if (mActivity != null && mAdapter != null) {
            mActivity.startUpdate();
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

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        if (tryToOpenMyTeamRequest) {
            Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), TeamPersonalRequestActivity.class);
            startActivity(intent);
            tryToOpenMyTeamRequest = false;
        }
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }
}
