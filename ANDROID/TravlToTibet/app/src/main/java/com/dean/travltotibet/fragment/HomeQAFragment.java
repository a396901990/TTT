package com.dean.travltotibet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.adapter.QAListAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.dialog.ShowHtmlDialogFragment;
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.ui.loadmore.LoadMoreListView;
import com.dean.travltotibet.util.ScreenUtil;
import com.dean.travltotibet.util.SearchFilterManger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 5/3/16.
 */
public class HomeQAFragment extends BaseRefreshFragment {

    private static final int CREATE_REQUEST = 0;

    private static final String HEADER_HTML_URL = "http://7xrvj0.com1.z0.glb.clouddn.com/rule.html";
    private static final String HEADER_IMAGE_URL = "http://7xr1ra.com1.z0.glb.clouddn.com/ruheyueban.png";

    private View root;

    private QAListAdapter mAdapter;
    private ArrayList<QARequest> qaRequests;
    private HomeActivity mActivity;
    private LoadMoreListView loadMoreListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadingBackgroundManager loadingBackgroundManager;
    private View articleHeader;

    private int limit = 8;        // 每页的数据是8条

    public static HomeQAFragment newInstance() {
        HomeQAFragment fragment = new HomeQAFragment();
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

        initLoadingBackground();
        initRefreshView();
        setUpList();
//        setUpHeader();
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
        loadMoreListView = (LoadMoreListView) root.findViewById(R.id.team_request_fragment_list_rv);

        // 解决listview，mSwipeRefreshLayout冲突
        loadMoreListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (loadMoreListView == null || loadMoreListView.getChildCount() == 0) ? 0 : loadMoreListView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });

        mAdapter = new QAListAdapter(getActivity());
        loadMoreListView.setAdapter(mAdapter);
        setLoadMoreListView(loadMoreListView);
    }

    private void setUpHeader() {

        articleHeader = LayoutInflater.from(getActivity()).inflate(R.layout.team_request_header_view, null);
        ImageView backgroundImage = (ImageView) articleHeader.findViewById(R.id.background_image);
        if (!TextUtils.isEmpty(HEADER_IMAGE_URL)) {
            Picasso.with(getActivity()).load(HEADER_IMAGE_URL).error(R.color.light_gray).into(backgroundImage);
        }
        MaterialRippleLayout contentView = (MaterialRippleLayout) articleHeader.findViewById(R.id.ripple_view);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                ShowHtmlDialogFragment dialogFragment = new ShowHtmlDialogFragment();
                dialogFragment.setUrl(HEADER_HTML_URL);
                dialogFragment.show(getFragmentManager(), ShowHtmlDialogFragment.class.getName());
            }
        });
    }

    private void getQARequests(final int actionType) {
        qaRequests = new ArrayList<>();

        BmobQuery<QARequest> query = new BmobQuery<>();
        query.order("-comments,-createdAt");
        query.addWhereEqualTo("status", TeamRequest.PASS_STATUS);   // 只显示P状态

        // 搜索条件
        if (SearchFilterManger.getQAFilterTags().size() > 0) {
            query.addWhereContains("content", SearchFilterManger.getQATextWithType(SearchFilterManger.SEARCH_QA));
        }

        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(mAdapter.getCount());
        }

        // 设置每页数据个数
        query.setLimit(limit);

        query.findObjects(getActivity(), new FindListener<QARequest>() {
            @Override
            public void onSuccess(List<QARequest> list) {
                qaRequests = (ArrayList<QARequest>) list;

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

        if (requestCode == CREATE_REQUEST) {
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
        getQARequests(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();
        // 加载header
        if (loadMoreListView.getHeaderViewsCount() == 0) {
            // loadMoreListView.addHeaderView(articleHeader);
        }

        // 无数据
        if (qaRequests == null || qaRequests.size() == 0) {
            loadingBackgroundManager.loadingFaild(getString(R.string.no_result), null);
        }

        if (mAdapter != null) {
            mAdapter.setData(qaRequests);
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
        getQARequests(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        super.LoadingMoreSuccess();
        if (mAdapter != null) {
            mAdapter.addData(qaRequests);
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
