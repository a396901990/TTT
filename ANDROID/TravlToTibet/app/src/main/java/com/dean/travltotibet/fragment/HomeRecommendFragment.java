package com.dean.travltotibet.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.activity.WhereGoActivity;
import com.dean.travltotibet.adapter.HomeGridAdapter;
import com.dean.travltotibet.adapter.RecommendAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.ui.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 10/15/15.
 */
public class HomeRecommendFragment extends BaseHomeFragment {

    private View root;
    private RecommendAdapter mAdapter;
    private ArrayList<Route> routes;
    private HomeActivity mActivity;
    private RecyclerView mRecyclerView;

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

        getRouteData();
        initList();
        //initWhereGo();
        initFabBtn();
    }

    private void initList() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recommend_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mAdapter = new RecommendAdapter(getActivity());
        mAdapter.setData(routes);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initFabBtn() {
        final FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.hide(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.show(true);
                fab.setShowAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.show_from_bottom));
                fab.setHideAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.hide_to_bottom));
            }
        }, 300);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabEvent();
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

    private void initWhereGo() {
        View whereGoBtn = root.findViewById(R.id.where_go);
        whereGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WhereGoActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取route数据
     */
    private void getRouteData() {
        routes = (ArrayList<Route>) TTTApplication.getDbHelper().getRoutsList();
    }

    private class refreshTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mActivity.startUpdate();
            mAdapter.clearData();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            getRouteData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mAdapter.setData(routes);
            mActivity.finishUpdate();

            super.onPostExecute(result);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void refresh() {
        new refreshTask().execute();
    }

    public void fabEvent() {
        Intent intent = new Intent(getActivity(), WhereGoActivity.class);
        startActivity(intent);
    }
}
