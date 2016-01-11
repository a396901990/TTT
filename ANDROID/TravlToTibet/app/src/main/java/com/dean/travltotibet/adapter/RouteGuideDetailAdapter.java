package com.dean.travltotibet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
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
 * Created by DeanGuo on 4/10/15.
 */
public class RouteGuideDetailAdapter extends BaseAdapter {

    private ArrayList<Geocode> nonPathGeocode;

    private ArrayList<Geocode> allGeocode;

    private Context mContext;

    private boolean isForward;

    private ExpandableListener mExpandableListener;

    public RouteGuideDetailAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public static interface ExpandableListener {
        public void onExpand();

        public void onCollapse();
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
    public int getCount() {
        return nonPathGeocode.size();
    }

    @Override
    public Geocode getItem(int position) {
        return nonPathGeocode.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GuideDetailViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.guide_line_list_view, null);

            holder = new GuideDetailViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GuideDetailViewHolder) convertView.getTag();
            resetHolder(holder);
        }

        setUpHeaderView(holder, position);
        setUpDetailView(holder, position);
        setUpDistance(holder, position);

        return convertView;
    }

    private void setUpDistance(GuideDetailViewHolder holder, int position) {
        Geocode geocode = getItem(position);
        // 如果是最后一个点隐藏distance content
        if (position == getCount() - 1) {
            holder.distanceContent.setVisibility(View.INVISIBLE);
        }
        // 不是最后一个点显示distance content并赋值
        else {
            holder.distanceContent.setVisibility(View.VISIBLE);

            String start = geocode.getName();
            String end = getItem(position + 1).getName();
            holder.distanceText.setText(getDistance(start, end));
        }
    }

    private void setUpDetailView(GuideDetailViewHolder holder, int position) {
        Geocode geocode = getItem(position);
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

        // detail
        String detail;
        // 如果是最后一个点显示e_detail，并隐藏distance content
        if (position == getCount() - 1) {
            detail = geocode.getE_detail();
            holder.distanceContent.setVisibility(View.GONE);
        }
        // 不是最后一个点根据正反攻略取结果，并显示distance content
        else {
            detail = isForward ? geocode.getF_detail() : geocode.getR_detail();
        }

        // 没有内容不显示detail
        if (!TextUtils.isEmpty(detail)) {
            holder.detailGuideContent.setVisibility(View.VISIBLE);
            holder.detailGuide.setText(detail);
        } else {
            holder.detailGuideContent.setVisibility(View.INVISIBLE);
        }

        if (holder.isExpanded) {
            holder.headerToggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_remove_circle_outline, TTTApplication.getMyColor(R.color.colorPrimary)));
            holder.detailToggleLayout.setVisibility(View.VISIBLE);
        } else {
            holder.headerToggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_add_circle, TTTApplication.getMyColor(R.color.colorPrimary)));
            holder.detailToggleLayout.setVisibility(View.GONE);
        }
    }

    private void setUpHeaderView(final GuideDetailViewHolder holder, final int position) {

        final Geocode geocode = getItem(position);
        // headerTitle
        holder.headerTitle.setText(geocode.getName());

        // 第一个点（隐藏上半部分，显示下半部分）
        if (position == 0) {
            holder.headerTopLine.setVisibility(View.GONE);
            holder.headerBottomLine.setVisibility(View.VISIBLE);
        }
        // 最后一个点（隐藏下半部分，隐藏distance）
        else if (position == getCount() - 1) {
            holder.headerBottomLine.setVisibility(View.GONE);
            holder.headerTopLine.setVisibility(View.VISIBLE);
        }
        // 其他点（全部显示）
        else {
            holder.headerTopLine.setVisibility(View.VISIBLE);
            holder.headerBottomLine.setVisibility(View.VISIBLE);
        }

        if (holder.isExpanded) {
            holder.headerToggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_remove_circle_outline, TTTApplication.getMyColor(R.color.colorPrimary)));
        } else {
            holder.headerToggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_add_circle, TTTApplication.getMyColor(R.color.colorPrimary)));
        }

        // 设置监听
        holder.headerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (holder.isExpanded) {
                    holder.isExpanded = false;
                    collapseView(holder);
                } else {
                    holder.isExpanded = true;
                    expandView(holder);
                }
            }
        });
    }

    public class GuideDetailViewHolder {

        // header
        TextView headerTitle;
        ImageView headerToggleButton;
        MaterialRippleLayout headerLayout;
        View headerTopLine;
        View headerBottomLine;

        // distance
        View distanceDivideContent;
        View distanceContent;
        TextView distanceText;

        // detail
        TextView detailHeight;
        TextView detailMileage;
        TextView detailGuide;
        View detailGuideContent;
        View detailToggleLayout;

        boolean isExpanded = false;

        public GuideDetailViewHolder(View v) {

            this.headerTitle = (TextView) v.findViewById(R.id.header_title);
            this.headerToggleButton = (ImageView) v.findViewById(R.id.expand_collapse);
            this.headerLayout = (MaterialRippleLayout) v.findViewById(R.id.header_layout);
            this.headerTopLine = v.findViewById(R.id.header_top_line);
            this.headerBottomLine = v.findViewById(R.id.header_bottom_line);

            this.distanceText = (TextView) v.findViewById(R.id.distance_text);
            this.distanceContent = v.findViewById(R.id.distance_content);
            this.distanceDivideContent = v.findViewById(R.id.distance_divide_content);

            this.detailHeight = (TextView) v.findViewById(R.id.detail_height);
            this.detailMileage = (TextView) v.findViewById(R.id.detail_milestone);
            this.detailGuide = (TextView) v.findViewById(R.id.detail_guide);
            this.detailGuideContent = v.findViewById(R.id.detail_guide_content);
            this.detailToggleLayout = v.findViewById(R.id.toggle_layout);
        }

    }

    /**
     * 打开视图
     */
    public void expandView(final GuideDetailViewHolder holder) {
        final Animation slideDown = AnimationUtils.loadAnimation(mContext, R.anim.card_slide_down);


        holder.headerToggleButton.setFocusable(false);
        slideDown.setInterpolator(new BounceInterpolator());


        holder.headerToggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_remove_circle_outline, TTTApplication.getMyColor(R.color.colorPrimary)));
        holder.detailToggleLayout.startAnimation(slideDown);
        holder.detailToggleLayout.setVisibility(View.VISIBLE);
        holder.distanceDivideContent.setVisibility(View.GONE);

        mExpandableListener.onExpand();
    }

    /**
     * 收起视图
     */
    public void collapseView(final GuideDetailViewHolder holder) {
        final Animation slideUp = AnimationUtils.loadAnimation(mContext, R.anim.card_slide_up);
        slideUp.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                holder.headerToggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_add_circle, TTTApplication.getMyColor(R.color.colorPrimary)));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                holder.detailToggleLayout.setVisibility(View.GONE);
                holder.distanceDivideContent.setVisibility(View.VISIBLE);
                mExpandableListener.onCollapse();
            }
        });
        holder.detailToggleLayout.startAnimation(slideUp);
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

    public void resetHolder(GuideDetailViewHolder holder) {
        holder.isExpanded = false;
        holder.distanceDivideContent.setVisibility(View.VISIBLE);
    }

    public void setIsForward(boolean isForward) {
        this.isForward = isForward;
    }

    public void setExpandableListener(ExpandableListener mExpandableListener) {
        this.mExpandableListener = mExpandableListener;
    }
}
