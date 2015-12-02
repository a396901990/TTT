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

    private String mRoute;

    private ListView mListView;
    private InfoPrepareDetailAdapter mAdapter;
    private ArrayList<PrepareDetail> mPrepareDetails;
    private InfoType mInfoType;

    public PrepareDetailFragment(InfoType infoType, String route) {
        this.mInfoType = infoType;
        this.mRoute = route;
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

        // 从PrepareInfo表中获取该路段的准备信息
        PrepareInfo prepareInfo = TTTApplication.getDbHelper().getPrepareInfo(mRoute);

        // 获取条目名字
        String prepareName = InfoType.getInfoResult(mInfoType, prepareInfo);

        // 根据名字从PrepareDetail表中获取详细数据
        mPrepareDetails = (ArrayList<PrepareDetail>) TTTApplication.getDbHelper().getPrepareDetails(prepareName, mInfoType.toString());

//        for(PrepareDetail prepareDetail : mPrepareDetails) {
//            Log.e("getTitle", prepareDetail.getTitle());
//            Log.e("getSummary", prepareDetail.getSummary());
//            Log.e("getDetail", prepareDetail.getDetail());
//        }

    }

    /**
     * 初始化列表
     */
    private void initList() {
        mListView = (ListView) root.findViewById(R.id.detail_list);
        mAdapter = new InfoPrepareDetailAdapter(mActivity);
        if (mPrepareDetails != null) {
            mAdapter.setData(mPrepareDetails);
            mListView.setAdapter(mAdapter);
        }
    }

}
