package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.greendao.PrepareDetail;
import com.dean.greendao.PrepareInfo;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.PrepareDetailActivity;
import com.dean.travltotibet.adapter.InfoPrepareDetailAdapter;
import com.dean.travltotibet.model.InfoType;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 11/8/15.
 */
public class PrepareDetailFragment extends Fragment {

    private PrepareDetailActivity mActivity;

    private View root;

    private String route;
    private InfoType type;

    private ListView mListView;
    private InfoPrepareDetailAdapter mAdapter;
    private ArrayList<PrepareDetail> prepareDetails;

    public PrepareDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.prepare_detail_fragment, null);
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

        // 类型
        type = mActivity.getType();
        // 路线
        route = mActivity.getRoute();

        // 从PrepareInfo表中获取该路段的准备信息
        PrepareInfo prepareInfo = TTTApplication.getDbHelper().getPrepareInfo(mActivity.getRoute());

        // 获取条目名字
        String prepareName = InfoType.getInfoResult(type, prepareInfo);

        // 根据名字从PrepareDetail表中获取详细数据
        prepareDetails = (ArrayList<PrepareDetail>) TTTApplication.getDbHelper().getPrepareDetails(prepareName, type.toString());

        for(PrepareDetail prepareDetail : prepareDetails) {
            Log.e("getTitle", prepareDetail.getTitle());
            Log.e("getSummary", prepareDetail.getSummary());
            Log.e("getDetail", prepareDetail.getDetail());
        }

    }

    /**
     * 初始化列表
     */
    private void initList() {
        mListView = (ListView) root.findViewById(R.id.detail_list);
        mAdapter = new InfoPrepareDetailAdapter(mActivity);
        mAdapter.setData(prepareDetails);
        mListView.setAdapter(mAdapter);
    }

}
