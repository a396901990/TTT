package com.dean.travltotibet.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dean.greendao.PrepareDetail;
import com.dean.greendao.PrepareInfo;
import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.adapter.InfoPrepareDetailAdapter;
import com.dean.travltotibet.adapter.RoutePlanListAdapter;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 4/10/15.
 */
public class PrepareDetailDialog extends DialogFragment {

    private InfoRouteActivity infoRouteActivity;

    private View root;

    private String route;
    private InfoType type;

    private ListView mListView;
    private InfoPrepareDetailAdapter mAdapter;
    private ArrayList<PrepareDetail> prepareDetails;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        infoRouteActivity = (InfoRouteActivity) getActivity();
        root = LayoutInflater.from(getActivity()).inflate(R.layout.info_prepare_dialog, null);

        // 初始化数据
        initData();
        // 初始化列表
        initList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(root).setTitle(InfoType.INFO_TEXT.get(type));
        return builder.create();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        // 类型
        if (getArguments() != null) {
            type = (InfoType) getArguments().getSerializable(Constants.INTENT_PREPARE_TYPE);
        }

        // 路线
        route = infoRouteActivity.getRoute();

        // 从PrepareInfo表中获取该路段的准备信息
        PrepareInfo prepareInfo = TTTApplication.getDbHelper().getPrepareInfo(infoRouteActivity.getRoute());

        // 获取条目名字
        String prepareName = InfoType.getInfoResult(type, prepareInfo);

        // 根据名字从PrepareDetail表中获取详细数据
        prepareDetails = (ArrayList<PrepareDetail>) TTTApplication.getDbHelper().getPrepareDetails(prepareName, type.toString());

        for(PrepareDetail prepareDetail : prepareDetails) {
            Log.e("getTitle", prepareDetail.getTitle());
            Log.e("getDetail", prepareDetail.getDetail());
        }

    }

    /**
     * 初始化列表
     */
    private void initList() {
        mListView = (ListView) root.findViewById(R.id.detail_list);
        mAdapter = new InfoPrepareDetailAdapter(infoRouteActivity);
        mAdapter.setData(prepareDetails);
        mListView.setAdapter(mAdapter);
    }

}
