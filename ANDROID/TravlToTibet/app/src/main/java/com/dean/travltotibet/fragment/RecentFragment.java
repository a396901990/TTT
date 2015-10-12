package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.greendao.RecentRoute;
import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.PrepareRoutePlanListAdapter;
import com.dean.travltotibet.adapter.RecentListAdapter;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/10/15.
 */
public class RecentFragment extends Fragment {

    private View root;
    private ListView mListView;
    private RecentListAdapter mAdapter;
    private ArrayList<RecentRoute> recentRoutes;

    public RecentFragment() {
    }

    public static RecentFragment newInstance() {
        RecentFragment fragment = new RecentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.recent_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getRecentData();
        initList();
    }

    public void updateRecentData() {
        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
        mAdapter.setData(recentRoutes);
        mAdapter.notifyDataSetInvalidated();
    }

    /**
     * 初始化列表
     */
    private void initList() {
        mListView = (ListView) root.findViewById(R.id.recent_list);
        mAdapter = new RecentListAdapter(getActivity());
        Log.e("recentRoutes.size()", recentRoutes.size()+"");

        if (recentRoutes != null) {
            mAdapter.setData(recentRoutes);
            mListView.setAdapter(mAdapter);
        }
    }

    /**
     * 获取最近显示数据
     */
    private void getRecentData() {
        recentRoutes = (ArrayList<RecentRoute>) TTTApplication.getDbHelper().getRecentRoute();
        for (RecentRoute recentRoute : recentRoutes) {
            Log.e("recentRoute", recentRoute.toString());
        }
    }


}
