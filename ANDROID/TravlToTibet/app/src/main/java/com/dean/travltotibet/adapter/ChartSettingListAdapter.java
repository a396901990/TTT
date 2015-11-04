/**
 * Copyright 2012, FMR LLC. All Rights Reserved. Fidelity Confidential
 * Information
 */
package com.dean.travltotibet.adapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.PointManager;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.DropListener;

/**
 * Adapter for watch list position columns
 *
 * @author Bob Qi (a483334)
 */
public class ChartSettingListAdapter
        extends BaseAdapter
        implements DropListener {

    private final DragSortListView mListView;

    private List<PointCheck> mAllPoints = new LinkedList<PointCheck>();

    public ChartSettingListAdapter(final DragSortListView list) {
        mListView = list;
        mListView.setDropListener(this);

        // 获取默认点击点
        List<String> default_points = new ArrayList<String>();
        String[] currentPoints = PointManager.getCurrentPoints();
        for (String point : currentPoints) {
            default_points.add(point);
        }

        // 所有点
        String[] allPoints = PointManager.getAllPoints();
        for (String point : allPoints) {
            if (default_points.contains(point)) {
                mAllPoints.add(new PointCheck(point, true));
            } else {
                mAllPoints.add(new PointCheck(point, false));
            }
        }
    }

    private void bindData(final View root, final int position) {
        final PointCheck pointCheck = getItem(position);

        TextView name = (TextView) root.findViewById(R.id.name);
        name.setText(PointManager.getTitle(pointCheck.getName()));
        name.setTextColor(TTTApplication.getResourceUtil().getResources().getColor(PointManager.getColor(pointCheck.getName())));

        CheckBox check = (CheckBox) root.findViewById(R.id.checkbox);
        // have to clear the listener when reset state
        check.setOnCheckedChangeListener(null);
        check.setChecked(pointCheck.isChecked);

        check.setTag(pointCheck);
        check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getTag() instanceof PointCheck) {
                    ((PointCheck) buttonView.getTag()).setIsChecked(isChecked);
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

        bindData(v, position);
        return v;
    }

    /**
     * 恢复默认
     */
    public void resetToDefault() {
        PointManager.setCurrentPoints(TTTApplication.getMyResources().getStringArray(R.array.default_points));
        PointManager.setAllPoints(TTTApplication.getMyResources().getStringArray(R.array.default_all_points));

        mAllPoints = new LinkedList<PointCheck>();

        // 获取默认点击点
        List<String> default_points = new ArrayList<String>();
        String[] currentPoints = PointManager.getCurrentPoints();
        for (String point : currentPoints) {
            default_points.add(point);
        }

        // 所有点
        String[] allPoints = PointManager.getAllPoints();
        for (String point : allPoints) {
            if (default_points.contains(point)) {
                mAllPoints.add(new PointCheck(point, true));
            } else {
                mAllPoints.add(new PointCheck(point, false));
            }
        }

        notifyDataSetChanged();
    }

    public String[] getSelectedPoints() {
        ArrayList<String> selectedPoints = new ArrayList<String>();
        for (PointCheck pointCheck : mAllPoints) {
            if (pointCheck.isChecked) {
                selectedPoints.add(pointCheck.getName());
            }
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

    public class PointCheck {

        private String name;
        private boolean isChecked;

        public PointCheck(String name, boolean isChecked) {
            this.name = name;
            this.isChecked = isChecked;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setIsChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }
    }
}
