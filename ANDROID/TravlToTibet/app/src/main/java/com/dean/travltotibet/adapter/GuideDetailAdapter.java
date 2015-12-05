package com.dean.travltotibet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.AnimatedExpandableListView;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.StringUtil;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/5/15.
 */
public class GuideDetailAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;

    private ArrayList<Geocode> items;

    public GuideDetailAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<Geocode> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public Geocode getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
        GuideDetailHolder holder;
        if (convertView == null) {
            holder = new GuideDetailHolder();
            convertView = inflater.inflate(R.layout.route_guide_detail_list_item, parent,
                    false);
            holder.detailHeight = (TextView) convertView
                    .findViewById(R.id.detail_height);
            holder.detailMileage = (TextView) convertView
                    .findViewById(R.id.detail_milestone);
            holder.detailGuide = (TextView) convertView
                    .findViewById(R.id.detail_guide);

            convertView.setTag(holder);
        } else {
            holder = (GuideDetailHolder) convertView.getTag();
        }

        /** set data */
        Geocode geocode = getChild(groupPosition, childPosition);

        // height
        String height = StringUtil.formatDoubleToInteger(geocode.getElevation());
        height = String.format(Constants.GUIDE_OVERALL_HEIGHT_FORMAT, height);
        holder.detailHeight.setText(height);

        // mileage
        String road = geocode.getRoad();
        if (!TextUtils.isEmpty(road)) {
            road = road.split("/")[1];
        }
        String milestone = StringUtil.formatDoubleToFourInteger(geocode.getMilestone());
        milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, road, milestone);
        holder.detailMileage.setText(milestone);

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Geocode getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final GuideDetailHolder holder;

        if (convertView == null) {
            holder = new GuideDetailHolder();
            convertView = inflater.inflate(R.layout.route_group_header_list_item, parent,
                    false);
            holder.headerTitle = (TextView) convertView
                    .findViewById(R.id.header_title);
            holder.headerLayout = (MaterialRippleLayout) convertView.findViewById(R.id.header_layout);
            holder.toggleButton = (ToggleButton) convertView.findViewById(R.id.expand_collapse);
            convertView.setTag(holder);
        } else {
            holder = (GuideDetailHolder) convertView.getTag();
        }

        Geocode item = getGroup(groupPosition);

        holder.headerTitle.setText(item.getName());

        if (isExpanded) {
            holder.toggleButton.setChecked(true);
        } else {
            holder.toggleButton.setChecked(false);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    public static class GuideDetailHolder {
        TextView headerTitle;
        TextView detailHeight;
        TextView detailMileage;
        TextView detailGuide;

        ToggleButton toggleButton;
        LinearLayout toggleLayout;
        MaterialRippleLayout headerLayout;
        LinearLayout positionLayout;
        LinearLayout guideLayout;
    }
}