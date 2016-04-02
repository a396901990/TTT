package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.GalleryAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.model.GalleryInfo;
import com.dean.travltotibet.ui.loadmore.LoadMoreRecyclerView;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 3/18/16.
 */
public abstract class GalleryInfoFragment extends BaseRefreshFragment implements LoadMoreRecyclerView.LoadMoreListener {

    protected int ITEM_LIMIT = 6;

    protected View root;

    protected GalleryAdapter mAdapter;

    protected ArrayList<GalleryInfo> galleryInfos;

    protected LoadMoreRecyclerView loadMoreRecyclerView;

    protected String routeName;

    protected String aroundBelong;

    protected boolean isForward;

    private LoadingBackgroundManager loadingBackgroundManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.gallery_info_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            routeName = getArguments().getString(IntentExtra.INTENT_ROUTE);
            aroundBelong = getArguments().getString(IntentExtra.INTENT_AROUND_BELONG);
            isForward = getArguments().getBoolean(IntentExtra.INTENT_ROUTE_DIR);
        }
        initLoadingBackground();
        initView();
        onRefresh();
    }

    private void initLoadingBackground() {
        ViewGroup contentView = (ViewGroup) root.findViewById(R.id.content_view);
        loadingBackgroundManager = new LoadingBackgroundManager(getActivity(), contentView);
    }

    public abstract void getResult(int actionType);

    public abstract String getType();

    private void initView() {

        mAdapter = new GalleryAdapter(getActivity());
        mAdapter.setType(getType());
        loadMoreRecyclerView = (LoadMoreRecyclerView) root.findViewById(R.id.scenic_recycler);
        // 设置横向layout manager
        loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        loadMoreRecyclerView.setHasFixedSize(true);
        loadMoreRecyclerView.setAdapter(mAdapter);
        loadMoreRecyclerView.setLoadMoreListener(this);
    }

    @Override
    public void onLoadMore() {
        toDo(ON_LOADING_MORE, 800);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        super.prepareLoading();
        loadingBackgroundManager.showLoadingView();

        if (getActivity() != null && mAdapter != null) {
            mAdapter.clearData();
            toDo(ON_LOADING, 800);
        }
    }

    @Override
    public void onLoading() {
        super.onLoading();
        getResult(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();
        // 无数据
        if (galleryInfos == null || galleryInfos.size() == 0) {
            loadingBackgroundManager.loadingFaild(getString(R.string.no_result), null);
        }
        // 有数据
        else {
            loadingBackgroundManager.loadingSuccess();
            mAdapter.setData(galleryInfos);
            loadMoreRecyclerView.notifyMoreFinish(galleryInfos.size() >= ITEM_LIMIT);
        }
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
    }

    @Override
    public void onLoadingMore() {
        super.onLoadingMore();
        getResult(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        super.LoadingMoreSuccess();
        mAdapter.addData(galleryInfos);
        loadMoreRecyclerView.notifyMoreFinish(true);
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getAroundBelong() {
        return aroundBelong;
    }

    public boolean isForward() {
        return isForward;
    }

    public void setIsForward(boolean isForward) {
        this.isForward = isForward;
    }
}
