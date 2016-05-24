package com.dean.travltotibet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.MomentCreateActivity;
import com.dean.travltotibet.adapter.MomentAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.model.Moment;
import com.dean.travltotibet.ui.fab.FloatingActionButton;
import com.dean.travltotibet.ui.loadmore.LoadMoreListView;
import com.dean.travltotibet.util.LoginUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 5/23/16.
 */
public class HomeMomentFragment extends BaseRefreshFragment {

    private View root;
    private MomentAdapter mAdapter;
    private ArrayList<Moment> moments;
    private HomeActivity mActivity;
    private LoadMoreListView loadMoreListView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadingBackgroundManager loadingBackgroundManager;

    private int limit = 8;        // 每页的数据是8条

    private boolean tryToCreateMoment = false;

    public static HomeMomentFragment newInstance() {
        HomeMomentFragment fragment = new HomeMomentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_moment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) getActivity();
        initLoadingBackground();
        initRefreshView();
        initFabBtn();
        setUpList();

        onRefresh();
    }

    private void initLoadingBackground() {
        ViewGroup contentView = (ViewGroup) root.findViewById(R.id.content_view);
        loadingBackgroundManager = new LoadingBackgroundManager(getActivity(), contentView);
    }

    private void initRefreshView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        setSwipeRefreshLayout(mSwipeRefreshLayout);
    }

    private void setUpList() {
        loadMoreListView = (LoadMoreListView) root.findViewById(R.id.moment_fragment_list_rv);

        loadMoreListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 拖动时隐藏
                if (scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    fab.hide(true);
                } else {
                    fab.show(true);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 解决listview，mSwipeRefreshLayout冲突
                int topRowVerticalPosition = (loadMoreListView == null || loadMoreListView.getChildCount() == 0) ? 0 : loadMoreListView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });

        mAdapter = new MomentAdapter(getActivity());
        loadMoreListView.setAdapter(mAdapter);
        setLoadMoreListView(loadMoreListView);
    }

    private void initFabBtn() {
        if (getActivity() == null) {
            return;
        }
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setColorNormalResId(R.color.colorPrimary);
        fab.setColorPressedResId(R.color.colorPrimaryDark);
//        fab.setColorNormal(0xFFDA4336);
//        fab.setColorPressed(0xFFE75043);
        fab.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.show(true);
                fab.setShowAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.show_from_bottom));
                fab.setHideAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.hide_to_bottom));
            }
        }, 300);

        // 点击打开slidingMenu
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMomentCreate();
            }
        });
    }

    private void gotoMomentCreate() {
        Intent intent = new Intent(getActivity(), MomentCreateActivity.class);
        startActivityForResult(intent, BaseActivity.CREATE_REQUEST);
        getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_down_out);
    }


    /**
     * 登陆成功回调
     */
    public void onEventMainThread(LoginUtil.LoginEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        if (tryToCreateMoment) {
            gotoMomentCreate();
        }
    }

    /**
     * 登陆失败回调
     */
    public void onEventMainThread(LoginUtil.LoginFailedEvent event) {
        Toast.makeText(getActivity(), getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    private void getMoments(final int actionType) {
        moments = new ArrayList<>();

        BmobQuery<Moment> query = new BmobQuery<>();
        query.order("-comments,-createdAt");
        query.include("imageFile,user[userId]");
        query.addWhereEqualTo("status", Moment.PASS_STATUS);   // 只显示P状态

        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(mAdapter.getCount());
        }

        // 设置每页数据个数
        query.setLimit(limit);

        query.findObjects(getActivity(), new FindListener<Moment>() {
            @Override
            public void onSuccess(List<Moment> list) {
                moments = (ArrayList<Moment>) list;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BaseActivity.CREATE_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                onRefresh();
            }
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        super.prepareLoading();

        loadingBackgroundManager.resetLoadingView();

        if (mActivity != null && mAdapter != null) {
            startRefresh();
            mAdapter.clearData();
            toDo(ON_LOADING, 800);
        }

        if (loadMoreListView.getHeaderViewsCount() > 0) {
            // loadMoreListView.removeHeaderView(articleHeader);
        }
    }

    @Override
    public void onLoading() {
        super.onLoading();
        getMoments(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();
        // 加载header
        if (loadMoreListView.getHeaderViewsCount() == 0) {
            // loadMoreListView.addHeaderView(articleHeader);
        }

        // 无数据
        if (moments == null || moments.size() == 0) {
            loadingBackgroundManager.loadingFaild(getString(R.string.no_result), null);
        }

        if (mAdapter != null) {
            mAdapter.setData(moments);
        }
        finishRefresh();
    }

    @Override
    public void LoadingError() {
        super.LoadingError();
        loadingBackgroundManager.loadingFaild(getString(R.string.network_no_result), new LoadingBackgroundManager.LoadingRetryCallBack() {
            @Override
            public void retry() {
                onRefresh();
            }
        });
        finishRefresh();
    }

    @Override
    public void onLoadingMore() {
        super.onLoadingMore();
        getMoments(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        super.LoadingMoreSuccess();
        if (mAdapter != null) {
            mAdapter.addData(moments);
        }
        if (loadMoreListView != null) {
            loadMoreListView.onLoadMoreComplete();
        }
    }

    @Override
    public void LoadingMoreError() {
        super.LoadingMoreError();
        if (loadMoreListView != null) {
            loadMoreListView.onLoadMoreComplete();
        }
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        toDo(ON_LOADING_MORE, 800);
    }
}
