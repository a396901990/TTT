package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.adapter.GalleryAdapter;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.GalleryInfo;
import com.dean.travltotibet.model.ScenicInfo;
import com.dean.travltotibet.ui.loadmore.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DeanGuo on 12/17/15.
 * <p/>
 * 用来显示route风景视图
 */
public class InfoScenicFragment extends RefreshFragment implements LoadMoreRecyclerView.LoadMoreListener {

    private InfoActivity infoActivity;

    private View root;

    private GalleryAdapter mAdapter;

    private ArrayList<GalleryInfo> galleryInfos;

    private LoadMoreRecyclerView loadMoreRecyclerView;

    private View loadingView;

    private View loadingProgressView;

    private View loadingNoResultView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.info_scenic_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        infoActivity = (InfoActivity) getActivity();

        initView();
        refresh();

    }

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
        loadMoreRecyclerView = (LoadMoreRecyclerView) root.findViewById(R.id.scenic_recycler);
        // 设置横向layout manager
        loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        loadMoreRecyclerView.setHasFixedSize(true);
        loadMoreRecyclerView.setAdapter(mAdapter);
        loadMoreRecyclerView.setLoadMoreListener(this);
        loadMoreRecyclerView.setAutoLoadMoreEnable(true);
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

    private void getResult(final int actionType) {

        BmobQuery<ScenicInfo> query = new BmobQuery<>();
        query.order("-comment,-createdAt");
        query.addWhereContains("route", infoActivity.getRoute());
        query.addQueryKeys("objectId,scenicName,scenic_Pic");

        // 加载更多
        if (actionType == STATE_MORE) {
            // 跳过已经加载的元素
            query.setSkip(mAdapter.getItemCount());
        }

        // 设置每页数据个数
        query.setLimit(6);

        query.findObjects(getActivity(), new FindListener<ScenicInfo>() {
            @Override
            public void onSuccess(List<ScenicInfo> list) {
                galleryInfos = new ArrayList<GalleryInfo>();
                for (ScenicInfo scenicInfo : list) {
                    GalleryInfo galleryInfo = new GalleryInfo();
                    galleryInfo.setName(scenicInfo.getScenicName());
                    galleryInfo.setUrl(scenicInfo.getScenic_Pic());
                    galleryInfo.setObjectId(scenicInfo.getObjectId());
                    galleryInfos.add(galleryInfo);
                }

                if (list.size() == 0 && actionType == STATE_MORE) {
                    loadMoreRecyclerView.notifyMoreFinish(false);
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
        loadMoreRecyclerView.notifyMoreFinish(true);
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
}
