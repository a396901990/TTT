package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.greendao.PrepareDetail;
import com.dean.greendao.PrepareInfo;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.PrepareDetailActivity;
import com.dean.travltotibet.adapter.PrepareDetailAdapter;
import com.dean.travltotibet.animator.ReboundItemAnimator;
import com.dean.travltotibet.model.InfoType;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 11/8/15.
 */
public class PrepareDetailFragment extends Fragment {

    private PrepareDetailActivity mActivity;

    private View root;

    private String mRoute;

    private String mType;

    private PrepareDetailAdapter mAdapter;
    private ArrayList<PrepareDetail> mPrepareDetails;
    private InfoType mInfoType;

    public PrepareDetailFragment(InfoType infoType, String route, String type) {
        this.mInfoType = infoType;
        this.mRoute = route;
        this.mType = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.prepare_detail_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (PrepareDetailActivity) getActivity();

        // 初始化数据
        initData();
        // 初始化列表
        initList();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        // 从PrepareInfo表中获取该路段的准备信息
        PrepareInfo prepareInfo = TTTApplication.getDbHelper().getPrepareInfo(mRoute, mType);
        if (prepareInfo == null) {
            return;
        }
        // 获取条目名字
        String prepareName = InfoType.getInfoResult(mInfoType, prepareInfo);

        // 根据名字从PrepareDetail表中获取详细数据
        mPrepareDetails = (ArrayList<PrepareDetail>) TTTApplication.getDbHelper().getPrepareDetails(prepareName, mInfoType.toString(), mType);
    }

    /**
     * 初始化列表
     */
    private void initList() {
        mAdapter = new PrepareDetailAdapter(mActivity);

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.prepare_detail_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        if (mPrepareDetails != null) {
            mAdapter.setData(mPrepareDetails);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
