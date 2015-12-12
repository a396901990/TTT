/**
 * Copyright 2012, FMR LLC. All Rights Reserved. Fidelity Confidential
 * Information
 */
package com.dean.travltotibet.adapter;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.ChartSettingFragment;
import com.dean.travltotibet.model.PointCheck;
import com.dean.travltotibet.ui.SwitchButton;
import com.dean.travltotibet.ui.chart.PointManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by DeanGuo on 11/10/15.
 */
public class ChartSettingUnSelectedListAdapter
        extends BaseAdapter {

    private List<PointCheck> mAllPoints = new LinkedList<PointCheck>();

    private ChartSettingFragment mController;

    public ChartSettingUnSelectedListAdapter(ChartSettingFragment controller) {
        mController = controller;

        // 获取点击点
        List<String> default_points = new ArrayList<String>();
        String[] currentPoints = PointManager.getCurrentPoints();
        for (String point : currentPoints) {
            if (!TextUtils.isEmpty(point)) {
                default_points.add(point);
            }
        }

        // 所有未点击点
        String[] allPoints = PointManager.getAllPoints();
        for (String point : allPoints) {
            if (!TextUtils.isEmpty(point)) {
                if (!default_points.contains(point)) {
                    mAllPoints.add(new PointCheck(point, false));
                }
            }
        }
    }

    private void bindData(final View root, final int position) {
        final PointCheck pointCheck = getItem(position);
        if (pointCheck == null || TextUtils.isEmpty(pointCheck.getName())) {
            return;
        }

        TextView name = (TextView) root.findViewById(R.id.name);
        name.setText(PointManager.getTitle(pointCheck.getName()));
        //name.setTextColor(TTTApplication.getResourceUtil().getResources().getMyColor(PointManager.getMyColor(pointCheck.getName())));

//        final CheckBox check = (CheckBox) root.findViewById(R.id.checkbox);
//        // have to clear the listener when reset state
//        check.setOnCheckedChangeListener(null);
//        check.setChecked(pointCheck.isChecked);
//
//        check.setTag(pointCheck);
//        check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (buttonView.getTag() instanceof PointCheck) {
//                    ((PointCheck) buttonView.getTag()).setIsChecked(isChecked);
//                }
//
//            }
//
//        });

        final SwitchButton switchButton = (SwitchButton) root.findViewById(R.id.switch_btn);
        switchButton.setSelectedBGColor(TTTApplication.getResourceUtil().getResources().getColor(PointManager.getColor(pointCheck.getName())));
        switchButton.setOnStatusChangeListener(null);
        switchButton.setStatus(SwitchButton.STATUS.OFF);
        switchButton.setOnStatusChangeListener(new SwitchButton.OnStatusChangeListener() {
            @Override
            public void onChange(SwitchButton.STATUS status) {

                switch (status) {
                    case ON:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mController.setToSelectedList(pointCheck);
                            }
                        }, SwitchButton.DEFAULT_DURATION);
                        break;
                    case OFF:
                        break;
                }
            }
        });
    }

    @Override
    public int getCount() {
        return mAllPoints.size();
    }

    @Override
    public PointCheck getItem(final int position) {
        return mAllPoints.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_setting_list_item, parent, false);
        } else {
            v = convertView;
        }

        if (getCount() != 0) {
            bindData(v, position);
        }
        return v;
    }

    /**
     * 恢复默认
     */
    public void resetToDefault() {
        PointManager.setCurrentPoints(TTTApplication.getMyResources().getStringArray(R.array.default_points));
        PointManager.setAllPoints(TTTApplication.getMyResources().getStringArray(R.array.default_all_points));

        mAllPoints = new LinkedList<PointCheck>();

        // 获取点击点
        List<String> default_points = new ArrayList<String>();
        String[] currentPoints = PointManager.getCurrentPoints();
        for (String point : currentPoints) {
            default_points.add(point);
        }

        // 所有未点击点
        String[] allPoints = PointManager.getAllPoints();
        for (String point : allPoints) {
            if (!default_points.contains(point)) {
                mAllPoints.add(new PointCheck(point, false));
            }
        }

        notifyDataSetChanged();
    }

    public List<PointCheck> getData() {
        return mAllPoints;
    }

    public void setData(List<PointCheck> mAllPoints) {
        this.mAllPoints = mAllPoints;
    }
}
