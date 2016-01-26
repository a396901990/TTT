package com.dean.travltotibet.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.greendao.RecentRoute;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.adapter.RecentAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.ui.fab.FloatingActionButton;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 10/10/15.
 */
public class HomeRecentFragment extends BaseHomeFragment {

    private View root;
    private RecentAdapter mAdapter;
    private ArrayList<RecentRoute> recentRoutes;
    private HomeActivity mActivity;
    private RecyclerView mRecyclerView;

    public HomeRecentFragment() {
    }

    public static HomeRecentFragment newInstance() {
        HomeRecentFragment fragment = new HomeRecentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_recent_layout, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) getActivity();

        getRecentData();
        setUpList();
        // initFabBtn();
    }

    private void setUpList() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recent_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mAdapter = new RecentAdapter(getActivity());
        mAdapter.setRecentCallBack(new RecentAdapter.RecentCallBack() {
            @Override
            public void update() {
                updateRecentData();
            }
        });
        mAdapter.setData(recentRoutes);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 更新recentRoutes数据
     */
    public void updateRecentData() {
        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
        mAdapter.setData(recentRoutes);
        mAdapter.notifyDataSetChanged();
    }

    private void initFabBtn() {
        final FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setImageDrawable(new IconicsDrawable(getActivity(), GoogleMaterial.Icon.gmd_delete).color(Color.WHITE).actionBar());
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

    /**
     * 获取最近显示数据
     */
    private void getRecentData() {
        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
    }

    @Override
    public void update() {
        new refreshTask().execute();
//        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
//        mAdapter.setData(recentRoutes);
    }

    @Override
    public void refresh() {
        new refreshTask().execute();
    }

    public void fabEvent() {

        new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.delete_recent_title))
                .content(getString(R.string.delete_recent_msg))
                .positiveText(getString(R.string.cancel_btn))
                .negativeText(getString(R.string.ok_btn))
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        TTTApplication.getDbHelper().cleanRecentRoutes();
                        updateRecentData();
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
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

            recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mAdapter.setData(recentRoutes);
            mActivity.finishUpdate();

            super.onPostExecute(result);
        }
    }
}
