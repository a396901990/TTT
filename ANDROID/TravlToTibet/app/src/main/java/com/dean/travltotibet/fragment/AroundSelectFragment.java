package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AroundSelectActivity;
import com.dean.travltotibet.adapter.AroundSelectAdapter;
import com.dean.travltotibet.model.AroundType;

/**
 * Created by DeanGuo on 1/13/16.
 * <p/>
 * 用来显示route风景视图
 */
public class AroundSelectFragment extends Fragment {

    private View root;

    private AroundSelectActivity aroundSelectActivity;

    private AroundSelectAdapter adapter;

    private String routeName;

    private String aroundType;

    private String aroundBelong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_select_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aroundSelectActivity = (AroundSelectActivity) getActivity();
        routeName = aroundSelectActivity.getRouteName();
        aroundType = aroundSelectActivity.getAroundType();
        aroundBelong = aroundSelectActivity.getAroundBelong();
        
        initView();
    }

    private void initView() {

        adapter = new AroundSelectAdapter(getActivity(), routeName, aroundBelong, aroundType);

        TextView mTitle = (TextView) root.findViewById(R.id.around_select_title);
        mTitle.setText(AroundType.getAroundName(aroundType));

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.around_select_recycler);
        // 设置横向layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }
}
