package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.adapter.ScenicAdapter;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/17/15.
 * <p/>
 * 用来显示route风景视图
 */
public class InfoScenicFragment extends BaseInfoFragment {

    private InfoActivity infoActivity;

    private View root;

    private ScenicAdapter adapter;

    private ArrayList<Scenic> mScenics;

    private RecyclerView mRecyclerView;

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

    }

    private void initView() {

        // 取出所有风景图片
        mScenics = (ArrayList<Scenic>) TTTApplication.getDbHelper().getScenicList(infoActivity.getRoute());

        adapter = new ScenicAdapter(getActivity(), mScenics);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.scenic_recycler);
        // 设置横向layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void updateType(String type) {
        super.updateType(type);
    }
}
