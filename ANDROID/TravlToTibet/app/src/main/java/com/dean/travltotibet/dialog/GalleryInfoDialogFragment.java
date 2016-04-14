package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.GalleryAdapter;
import com.dean.travltotibet.base.BaseRefreshDialogFragment;
import com.dean.travltotibet.base.LoadingBackgroundManager;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.model.GalleryInfo;
import com.dean.travltotibet.ui.loadmore.LoadMoreRecyclerView;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 3/18/16.
 * same code with GalleryInfoFragment bad design
 */
public abstract class GalleryInfoDialogFragment extends BaseRefreshDialogFragment implements LoadMoreRecyclerView.LoadMoreListener {

    public final static int ITEM_LIMIT = 6;

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

        initLoadingBackground();
        initView();
        onRefresh();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            routeName = getArguments().getString(IntentExtra.INTENT_ROUTE);
            aroundBelong = getArguments().getString(IntentExtra.INTENT_AROUND_BELONG);
            isForward = getArguments().getBoolean(IntentExtra.INTENT_ROUTE_DIR);
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PopupDialog);
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

        // title
        TextView title = (TextView) root.findViewById(R.id.title_text);
        if (AroundType.HOTEL.equals(getType())) {
            title.setText("住宿");
        }
        else if (AroundType.SCENIC.equals(getType())) {
            title.setText("风景");
        }
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
        if (loadingBackgroundManager == null) {
            return;
        }
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

        if (mAdapter != null) {
            mAdapter.addData(galleryInfos);
        }
        if (loadMoreRecyclerView != null) {
            loadMoreRecyclerView.notifyMoreFinish(galleryInfos.size() >= ITEM_LIMIT);
        }
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
