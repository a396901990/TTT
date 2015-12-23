package com.dean.travltotibet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.greendao.PrepareDetail;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.ListUtil;
import com.dean.travltotibet.util.StringUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 4/10/15.
 */
public class RouteGuideDetailAdapter extends BaseAdapter {

    private ArrayList<Geocode> mData;
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
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Geocode getItem(int position) {
        return mData.get(position);
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
            convertView = mInflater.inflate(R.layout.route_guide_detail_list_item_view, null);

            holder = new GuideDetailViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GuideDetailViewHolder) convertView.getTag();
        }

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

        Geocode geocode = getItem(position);

        // headerTitle
        holder.headerTitle.setText(geocode.getName());

        // height
        String height = StringUtil.formatDoubleToInteger(geocode.getElevation());
        height = String.format(Constants.GUIDE_OVERALL_HEIGHT_FORMAT, height);
        holder.detailHeight.setText(height);

        // mileage
        String road = geocode.getRoad();
        String milestone = geocode.getMilestone();
        milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, road, milestone);
        holder.detailMileage.setText(milestone);

        // detail
        String detail;
        // 如果最后一个点显示e_detail
        if (geocode.getName().equals(getItem(getCount() - 1).getName())) {
            detail = geocode.getE_detail();
        }
        // 不是最后一个点根据正反攻略取结果
        else {
            detail = isForward ? geocode.getF_detail() : geocode.getR_detail();
        }
        if (!TextUtils.isEmpty(detail)) {
            //holder.detailGuideContent.setVisibility(View.VISIBLE);
            holder.detailGuide.setText(detail);
        } else {
            //holder.detailGuideContent.setVisibility(View.INVISIBLE);
        }

        if (holder.isExpanded) {
            holder.toggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_remove_circle_outline, TTTApplication.getMyColor(R.color.colorPrimary)));
            holder.toggleLayout.setVisibility(View.VISIBLE);
        } else {
            holder.toggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_add_circle, TTTApplication.getMyColor(R.color.colorPrimary)));
            holder.toggleLayout.setVisibility(View.GONE);
        }

        return convertView;
    }

    public class GuideDetailViewHolder {

        TextView headerTitle;
        TextView detailHeight;
        TextView detailMileage;
        TextView detailGuide;

        ImageView toggleButton;
        View detailGuideContent;

        MaterialRippleLayout headerLayout;
        LinearLayout toggleLayout;

        boolean isExpanded = false;

        public GuideDetailViewHolder(View v) {

            this.headerTitle = (TextView) v.findViewById(R.id.header_title);

            this.headerLayout = (MaterialRippleLayout) v.findViewById(R.id.header_layout);
            this.toggleLayout = (LinearLayout) v.findViewById(R.id.toggle_layout);
            this.toggleButton = (ImageView) v.findViewById(R.id.expand_collapse);

            this.detailHeight = (TextView) v.findViewById(R.id.detail_height);
            this.detailMileage = (TextView) v.findViewById(R.id.detail_milestone);
            this.detailGuide = (TextView) v.findViewById(R.id.detail_guide);
            this.detailGuideContent = v.findViewById(R.id.detail_guide_content);
        }

    }

    public void expandView(final GuideDetailViewHolder holder) {
        final Animation slideDown = AnimationUtils.loadAnimation(mContext, R.anim.card_slide_down);


        holder.toggleButton.setFocusable(false);
        slideDown.setInterpolator(new BounceInterpolator());


        holder.toggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_remove_circle_outline, TTTApplication.getMyColor(R.color.colorPrimary)));
        holder.toggleLayout.startAnimation(slideDown);
        holder.toggleLayout.setVisibility(View.VISIBLE);
        mExpandableListener.onExpand();
    }

    public void collapseView(final GuideDetailViewHolder holder) {
        final Animation slideUp = AnimationUtils.loadAnimation(mContext, R.anim.card_slide_up);
        slideUp.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                holder.toggleLayout.setVisibility(View.GONE);
                mExpandableListener.onCollapse();
            }
        });

        holder.toggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_add_circle, TTTApplication.getMyColor(R.color.colorPrimary)));
        holder.toggleLayout.startAnimation(slideUp);
    }

    public void setIsForward(boolean isForward) {
        this.isForward = isForward;
    }

    public void setExpandableListener(ExpandableListener mExpandableListener) {
        this.mExpandableListener = mExpandableListener;
    }
}
