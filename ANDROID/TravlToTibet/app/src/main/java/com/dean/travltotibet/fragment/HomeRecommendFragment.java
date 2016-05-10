package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.adapter.RecommendAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.ui.VerticalSpaceItemDecoration;
import com.dean.travltotibet.ui.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 10/15/15.
 */
public class HomeRecommendFragment extends BaseRefreshFragment {

    private View root;
    private RecommendAdapter mAdapter;
    private ArrayList<Route> routes;
    private HomeActivity mActivity;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    HomeRecommendFragment homeRecommendFragment;

    public HomeRecommendFragment() {
    }

    public static HomeRecommendFragment newInstance() {
        HomeRecommendFragment fragment = new HomeRecommendFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_recommend_layout, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) getActivity();
        initRefreshView();
        initList();
        initFabBtn();

        onRefresh();
    }

    private void initRefreshView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        setSwipeRefreshLayout(mSwipeRefreshLayout);
    }

    private void initList() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recommend_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(3));

        mAdapter = new RecommendAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
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
                if (mActivity.getSlidingMenu().isMenuShowing()) {
                    mActivity.getSlidingMenu().showMenu(true);
                } else {
                    mActivity.getSlidingMenu().showMenu(false);
                }
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    fab.hide(true);
                } else {
                    fab.show(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 获取route数据
     */
    private void getRouteData() {
        routes = (ArrayList<Route>) TTTApplication.getDbHelper().getRoutsList();
        if (routes != null) {
            toDo(LOADING_SUCCESS, 0);
        } else {
            toDo(LOADING_ERROR, 0);
        }
    }

    @Override
    public void onRefresh() {
        super.onUpdate();
        toDo(PREPARE_LOADING, 200);
    }

    @Override
    public void prepareLoading() {
        super.prepareLoading();
        startRefresh();
        mAdapter.clearData();
        toDo(ON_LOADING, 600);
    }

    @Override
    public void onLoading() {
        super.onLoading();
        getRouteData();
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();
        mAdapter.setData(routes);
        finishRefresh();
    }

    @Override
    public void LoadingError() {
        super.LoadingError();
        finishRefresh();
    }
}
