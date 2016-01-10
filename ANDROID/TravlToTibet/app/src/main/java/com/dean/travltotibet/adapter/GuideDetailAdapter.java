package com.dean.travltotibet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.ui.chart.PointManager;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.StringUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/5/15.
 */
public class GuideDetailAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;

    private ArrayList<Geocode> nonPathGeocode;

    private ArrayList<Geocode> allGeocode;

    private boolean isForward;

    public GuideDetailAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<Geocode> data) {

        // 非path数据
        nonPathGeocode = new ArrayList<>();
        for (Geocode geocode : data) {
            if (!geocode.getTypes().equals(PointManager.PATH)) {
                nonPathGeocode.add(geocode);
            }
        }

        // 所有数据
        this.allGeocode = data;
        notifyDataSetChanged();
    }

    @Override
    public Geocode getChild(int groupPosition, int childPosition) {
        return nonPathGeocode.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        GuideDetailHolder holder;
        if (convertView == null) {
            holder = new GuideDetailHolder();
            convertView = inflater.inflate(R.layout.route_guide_detail_detail_list_item, parent, false);

            holder.detailHeight = (TextView) convertView.findViewById(R.id.detail_height);
            holder.detailMileage = (TextView) convertView.findViewById(R.id.detail_milestone);
            holder.detailGuide = (TextView) convertView.findViewById(R.id.detail_guide);
            holder.detailGuideContent = convertView.findViewById(R.id.detail_guide_content);
            holder.detailDistance = (TextView) convertView.findViewById(R.id.detail_distance);
            holder.detailDistanceContent = convertView.findViewById(R.id.detail_distance_content);
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
        String milestone = geocode.getMilestone();
        if (!TextUtils.isEmpty(milestone)) {
            milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, road, milestone);
        } else {
            milestone = String.format(Constants.GUIDE_OVERALL_ROAD_FORMAT, road);
        }
        holder.detailMileage.setText(milestone);

        // detail, distance
        String detail, distance;
        // 如果是最后一个点显示e_detail，并隐藏distance content
        if (groupPosition == getGroupCount()-1) {
            detail = geocode.getE_detail();

            holder.detailDistanceContent.setVisibility(View.INVISIBLE);
        }
        // 不是最后一个点根据正反攻略取结果，并显示distance content
        else {
            detail = isForward ? geocode.getF_detail() : geocode.getR_detail();

            String start = geocode.getName();
            String end = getGroup(groupPosition + 1).getName();
            holder.detailDistance.setText(getDistance(start, end));
        }

        if (!TextUtils.isEmpty(detail)) {
            holder.detailGuideContent.setVisibility(View.VISIBLE);
            holder.detailGuide.setText(detail);
        } else {
            holder.detailGuideContent.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    @Override
    public Geocode getGroup(int groupPosition) {
        return nonPathGeocode.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return nonPathGeocode.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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
            convertView = inflater.inflate(R.layout.route_guide_detail_header_list_item, parent, false);

            holder.headerTitle = (TextView) convertView.findViewById(R.id.header_title);
            holder.toggleButton = (ImageView) convertView.findViewById(R.id.expand_collapse);
            holder.headerDistance = (TextView) convertView.findViewById(R.id.header_distance);
            holder.headerLayout = (MaterialRippleLayout) convertView.findViewById(R.id.header_layout);
            holder.headerDistanceContent = convertView.findViewById(R.id.header_distance_content);
            holder.headerTopLine = convertView.findViewById(R.id.header_top_line);
            holder.headerBottomLine = convertView.findViewById(R.id.header_bottom_line);

            convertView.setTag(holder);
        } else {
            holder = (GuideDetailHolder) convertView.getTag();
        }

        Geocode geocode = getGroup(groupPosition);

        holder.headerTitle.setText(geocode.getName());

        // 第一个点（隐藏上半部分，显示下半部分，显示distance）
        if (getGroupId(groupPosition) == 0) {
            holder.headerTopLine.setVisibility(View.GONE);

            holder.headerBottomLine.setVisibility(View.VISIBLE);
            holder.headerDistanceContent.setVisibility(View.VISIBLE);
        }
        // 最后一个点（隐藏下半部分，隐藏distance，显示上半部分）
        else if (getGroupId(groupPosition) == getGroupCount()-1) {
            holder.headerBottomLine.setVisibility(View.GONE);
            holder.headerDistanceContent.setVisibility(View.GONE);

            holder.headerTopLine.setVisibility(View.VISIBLE);
        }
        // 其他点（全部显示）
        else {
            holder.headerTopLine.setVisibility(View.VISIBLE);
            holder.headerBottomLine.setVisibility(View.VISIBLE);
            holder.headerDistanceContent.setVisibility(View.VISIBLE);
        }

        // 根据打开/折叠显示toggleButton，headerDistanceContent
        if (isExpanded) {
            holder.toggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_remove_circle_outline, TTTApplication.getMyColor(R.color.colorPrimary)));
            holder.headerDistanceContent.setVisibility(View.GONE);
        } else {
            holder.toggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_add_circle, TTTApplication.getMyColor(R.color.colorPrimary)));
            // 折叠状态，并且不是最后一个点显示distance
            if (groupPosition != getGroupCount()-1) {
                holder.headerDistanceContent.setVisibility(View.VISIBLE);

                String start = geocode.getName();
                String end = getGroup(groupPosition + 1).getName();
                holder.headerDistance.setText(getDistance(start, end));
            }
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
        TextView headerDistance;
        View headerDistanceContent;
        MaterialRippleLayout headerLayout;
        ImageView toggleButton;

        View headerTopLine;
        View headerBottomLine;


        TextView detailHeight;
        TextView detailMileage;
        TextView detailGuide;
        TextView detailDistance;
        View detailDistanceContent;
        View detailGuideContent;
    }

    public boolean isForward() {
        return isForward;
    }

    public void setIsForward(boolean isForward) {
        this.isForward = isForward;
    }

    public String getDistance(String start, String end) {

        double distance = 0;
        boolean isBegin = false;
        for (Geocode geo : allGeocode) {
            if (geo.getName().equals(start)) {
                isBegin = true;
            }
            if (geo.getName().equals(end)) {
                isBegin = false;
            }
            if (isBegin) {
                distance += isForward ? geo.getF_distance() : geo.getR_distance();
            }
        }

        return String.format(Constants.TIMELINE_DISTANCE, StringUtil.formatDouble(Constants.STRING_INTEGER_FORMATTER, distance));
    }
}