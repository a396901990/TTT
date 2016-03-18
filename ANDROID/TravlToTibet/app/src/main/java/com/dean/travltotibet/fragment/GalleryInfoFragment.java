package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.GalleryAdapter;
import com.dean.travltotibet.model.GalleryInfo;
import com.dean.travltotibet.ui.loadmore.LoadMoreRecyclerView;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 3/18/16.
 * <p/>
 * 用来显示route风景视图
 */
public abstract class GalleryInfoFragment extends RefreshFragment implements LoadMoreRecyclerView.LoadMoreListener {

    protected int ITEM_LIMIT = 6;

    protected View root;

    protected GalleryAdapter mAdapter;

    protected ArrayList<GalleryInfo> galleryInfos;

    protected LoadMoreRecyclerView loadMoreRecyclerView;

    protected View loadingView;

    protected View loadingProgressView;

    protected View loadingNoResultView;

    protected String routeName;

    protected String aroundBelong;

    protected boolean isForward;

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
        initView();
        refresh();
    }

    public abstract void getResult(int actionType);

    public abstract String getType();

    private void initView() {

        loadingView = root.findViewById(R.id.loading_view);
        loadingProgressView = root.findViewById(R.id.loading_progress_view);
        loadingNoResultView = root.findViewById(R.id.no_result_view);
        loadingNoResultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

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
    public void update() {

    }

    @Override
    public void refresh() {
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        loadingView.setVisibility(View.VISIBLE);
        loadingProgressView.setVisibility(View.VISIBLE);
        loadingNoResultView.setVisibility(View.GONE);
        if (getActivity() != null && mAdapter != null) {
            mAdapter.clearData();
            toDo(ON_LOADING, 800);
        }
    }

    @Override
    public void onLoading() {
        getResult(STATE_REFRESH);
    }

    @Override
    public void LoadingSuccess() {
        loadingView.setVisibility(View.GONE);
        mAdapter.setData(galleryInfos);
        loadMoreRecyclerView.notifyMoreFinish(galleryInfos.size() >= ITEM_LIMIT);
    }

    @Override
    public void LoadingError() {
        loadingView.setVisibility(View.VISIBLE);
        loadingProgressView.setVisibility(View.GONE);
        loadingNoResultView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingMore() {
        getResult(STATE_MORE);
    }

    @Override
    public void LoadingMoreSuccess() {
        mAdapter.addData(galleryInfos);
        loadMoreRecyclerView.notifyMoreFinish(true);
    }

    @Override
    public void LoadingMoreError() {
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
