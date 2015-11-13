/**
 * Copyright 2012, FMR LLC. All Rights Reserved. Fidelity Confidential
 * Information
 */
package com.dean.travltotibet.adapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.ChartSettingFragment;
import com.dean.travltotibet.model.PointCheck;
import com.dean.travltotibet.ui.SwitchButton;
import com.dean.travltotibet.util.PointManager;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.DropListener;

/**
 * Created by DeanGuo on 11/10/15.
 */
public class ChartSettingSelectedListAdapter
        extends BaseAdapter
        implements DropListener {

    private final DragSortListView mListView;

    private ChartSettingFragment mController;

    private List<PointCheck> mAllPoints = new LinkedList<PointCheck>();

    public ChartSettingSelectedListAdapter(ChartSettingFragment controller) {
        mController = controller;

        mListView = mController.getmSelectedList();
        mListView.setDropListener(this);

        // 获取当前击点
        String[] currentPoints = PointManager.getCurrentPoints();
        for (String point : currentPoints) {
            if (!TextUtils.isEmpty(point)) {
                mAllPoints.add(new PointCheck(point, true));
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
        //name.setTextColor(TTTApplication.getResourceUtil().getResources().getColor(PointManager.getColor(pointCheck.getName())));

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
        switchButton.setStatus(SwitchButton.STATUS.ON);
        switchButton.setOnStatusChangeListener(new SwitchButton.OnStatusChangeListener() {
            @Override
            public void onChange(SwitchButton.STATUS status) {

                switch (status) {
                    case ON:
                        break;
                    case OFF:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mController.setToUnselectedList(pointCheck);
                            }
                        }, SwitchButton.DEFAULT_DURATION);
                        break;
                }
            }
        });
    }

    @Override
    public void drop(final int from, final int to) {
        mAllPoints.add(to, mAllPoints.remove(from));
        notifyDataSetChanged();
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
        String[] currentPoints = PointManager.getCurrentPoints();
        for (String point : currentPoints) {
            mAllPoints.add(new PointCheck(point, true));
        }

        notifyDataSetChanged();
    }

    public String[] getSelectedPoints() {
        ArrayList<String> selectedPoints = new ArrayList<String>();
        for (PointCheck pointCheck : mAllPoints) {
            selectedPoints.add(pointCheck.getName());
        }
        return selectedPoints.toArray(new String[0]);
    }

    public String[] getAllPoints() {
        ArrayList<String> mPoints = new ArrayList<String>();
        for (PointCheck pointCheck : mAllPoints) {
            mPoints.add(pointCheck.getName());
        }
        return mPoints.toArray(new String[0]);
    }

    public List<PointCheck> getData() {
        return mAllPoints;
    }

    public void setData(List<PointCheck> mAllPoints) {
        this.mAllPoints = mAllPoints;
    }
}
