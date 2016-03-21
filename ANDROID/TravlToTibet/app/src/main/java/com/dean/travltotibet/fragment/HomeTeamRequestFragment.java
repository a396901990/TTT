package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.TeamCreateRequestActivity;
import com.dean.travltotibet.activity.TeamRequestPersonalActivity;
import com.dean.travltotibet.adapter.TeamRequestListAdapter;
import com.dean.travltotibet.dialog.LoginDialog;
import com.dean.travltotibet.dialog.ShowHtmlDialogFragment;
import com.dean.travltotibet.dialog.TeamRequestFilterDialog;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.LoadMoreListView;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class HomeTeamRequestFragment extends RefreshFragment implements LoadMoreListView.OnLoadMoreListener {

    private static final int CREATE_REQUEST = 0;

    private View root;

    private TeamRequestListAdapter mAdapter;
    private ArrayList<TeamRequest> teamRequests;
    private HomeActivity mActivity;
    private LoadMoreListView loadMoreListView;
    private View articleHeader;

    private boolean tryToOpenMyTeamRequest = false;

    private boolean tryToCreateTeamRequest = false;

    private int limit = 6;        // 每页的数据是6条

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
        setUpHeader();
        initBottomView();
    }

    private void initBottomView() {

        View filterView = root.findViewById(R.id.filter_team_request);
        View myView = root.findViewById(R.id.my_team_request);

        // 搜索结伴
        filterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                TeamRequestFilterDialog dialogFragment = new TeamRequestFilterDialog();
                dialogFragment.show(getFragmentManager(), TeamRequestFilterDialog.class.getName());
            }
        });

        // 我的结伴
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (TTTApplication.hasLoggedIn()) {
                    Intent intent = new Intent(getActivity(), TeamRequestPersonalActivity.class);
                    startActivity(intent);
                } else {
                    tryToOpenMyTeamRequest = true;
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }
            }
        });

        // 添加结伴
        FloatingActionMenu mFloatingActionMenu = (FloatingActionMenu) root.findViewById(R.id.add_btn);
        mFloatingActionMenu.setIconAnimated(false);
        mFloatingActionMenu.setMenuButtonColorNormal(TTTApplication.getMyColor(R.color.colorPrimary));
        mFloatingActionMenu.setMenuButtonColorPressed(TTTApplication.getMyColor(R.color.colorPrimaryDark));
        mFloatingActionMenu.getMenuIconView().setImageResource(R.drawable.fab_add);
        mFloatingActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                if (TTTApplication.hasLoggedIn()) {
                    Intent intent = new Intent(getActivity(), TeamCreateRequestActivity.class);
                    startActivityForResult(intent, CREATE_REQUEST);
                    getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
                } else {
                    tryToCreateTeamRequest = true;
                    DialogFragment dialogFragment = new LoginDialog();
                    dialogFragment.show(getFragmentManager(), LoginDialog.class.getName());
                }
            }
        });
    }

    private void setUpList() {
        loadMoreListView = (LoadMoreListView) root.findViewById(R.id.team_request_fragment_list_rv);

        // 解决listview，mSwipeRefreshLayout冲突
        loadMoreListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (loadMoreListView == null || loadMoreListView.getChildCount() == 0) ? 0 : loadMoreListView.getChildAt(0).getTop();
                mActivity.getSwipeRefreshLayout().setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });

        mAdapter = new TeamRequestListAdapter(getActivity());
        loadMoreListView.setAdapter(mAdapter);
        loadMoreListView.setOnLoadMoreListener(this);
        refresh();
    }

    private void setUpHeader() {

        final String headerHtmlURL = "http://7xrvj0.com1.z0.glb.clouddn.com/rule.html";
        String headerImageURL = "http://7xr1ra.com1.z0.glb.clouddn.com/ruheyueban.png";

        articleHeader = LayoutInflater.from(getActivity()).inflate(R.layout.team_request_header_view, null);
        ImageView backgroundImage = (ImageView) articleHeader.findViewById(R.id.background_image);
        if (!TextUtils.isEmpty(headerImageURL)) {
            Picasso.with(getActivity()).load(headerImageURL).error(R.color.light_gray).into(backgroundImage);
        }
        MaterialRippleLayout contentView = (MaterialRippleLayout) articleHeader.findViewById(R.id.ripple_view);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                ShowHtmlDialogFragment dialogFragment = new ShowHtmlDialogFragment();
                dialogFragment.setUrl(headerHtmlURL);
                dialogFragment.show(getFragmentManager(), ShowHtmlDialogFragment.class.getName());
            }
        });
    }

    private void getTeamRequests(final int actionType) {
        teamRequests = new ArrayList<>();

        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.order("-comments,-createdAt");
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

    /**
     * 更新recentRoutes数据
     */
    public void updateData() {
        if (mAdapter == null || mActivity == null) {
            return;
        }

        View noResultView = root.findViewById(R.id.no_result_content);

        // 无数据
        if (teamRequests == null || teamRequests.size() == 0) {
            noResultView.setVisibility(View.VISIBLE);
            TextView noResultText = (TextView) root.findViewById(R.id.no_result_text);
            if (noResultText != null) {
                noResultText.setText(getString(R.string.no_result));
            }
        }
        // 有数据
        else {
            noResultView.setVisibility(View.GONE);
        }
        mAdapter.setData(teamRequests);
        mActivity.finishUpdate();
    }


    public void updateError() {
        if (mAdapter == null || mActivity == null) {
            return;
        }
        View noResultView = root.findViewById(R.id.no_result_content);

        // 无数据
        if (teamRequests == null || teamRequests.size() == 0) {
            noResultView.setVisibility(View.VISIBLE);
            TextView noResultText = (TextView) root.findViewById(R.id.no_result_text);
            if (noResultText != null) {
                noResultText.setText(getString(R.string.no_network_result));
            }
        }
        // 有数据
        else {
            noResultView.setVisibility(View.GONE);
        }
        mAdapter.setData(teamRequests);
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
        View noResultView = root.findViewById(R.id.no_result_content);
        noResultView.setVisibility(View.GONE);

        if (mActivity != null && mAdapter != null) {
            mActivity.startUpdate();
            mAdapter.clearData();
            toDo(ON_LOADING, 800);
        }

        if (loadMoreListView.getHeaderViewsCount() > 0) {
            loadMoreListView.removeHeaderView(articleHeader);
        }
    }

    @Override
    public void onLoading() {
        getTeamRequests(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {

        // 加载header
        if (loadMoreListView.getHeaderViewsCount() == 0) {
            loadMoreListView.addHeaderView(articleHeader);
        }

        // 更新数据
        updateData();
    }

    @Override
    public void LoadingError() {
        updateError();
    }

    @Override
    public void onLoadingMore() {
        getTeamRequests(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        if (mAdapter != null) {
            mAdapter.addData(teamRequests);
        }
        if (loadMoreListView != null) {
            loadMoreListView.onLoadMoreComplete();
        }
    }

    @Override
    public void LoadingMoreError() {
        if (loadMoreListView != null) {
            loadMoreListView.onLoadMoreComplete();
        }
    }

    @Override
    public void onLoadMore() {
        toDo(ON_LOADING_MORE, 800);
    }

    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        if (tryToOpenMyTeamRequest) {
            Intent intent = new Intent(getActivity(), TeamRequestPersonalActivity.class);
            startActivity(intent);
            tryToOpenMyTeamRequest = false;
        }
        else if (tryToCreateTeamRequest) {
            Intent intent = new Intent(getActivity(), TeamCreateRequestActivity.class);
            startActivityForResult(intent, CREATE_REQUEST);
            getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
            tryToCreateTeamRequest = false;
        }
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }


}
