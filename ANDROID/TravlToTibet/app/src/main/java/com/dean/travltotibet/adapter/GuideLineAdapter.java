package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dean.greendao.Geocode;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.dialog.AroundHotelInfoDialogFragment;
import com.dean.travltotibet.dialog.AroundScenicInfoDialogFragment;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.model.HotelInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.dean.travltotibet.util.StringUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by DeanGuo on 1/10/16.
 */
public class GuideLineAdapter extends BaseAdapter {

    private ArrayList<Geocode> nonPathGeocode;

    private ArrayList<Geocode> allGeocode;  // not used any more

    private Context mContext;

    private boolean isForward;

    private ExpandableListener mExpandableListener;

    public GuideLineAdapter(Context context) {
        super();
        this.mContext = context;
    }

    private String curRoute;

    public static interface ExpandableListener {
        public void onExpand();

        public void onCollapse();
    }

    public void setData(ArrayList<Geocode> data) {
        nonPathGeocode = data;
//        // 非path数据
//        nonPathGeocode = new ArrayList<>();
//        for (Geocode geocode : data) {
//            if (!geocode.getTypes().equals(PointManager.PATH)) {
//                nonPathGeocode.add(geocode);
//            }
//        }
//
//        // 所有数据
//        this.allGeocode = data;
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
//            resetHolder(holder);
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

            holder.distanceText.setText(isForward ? geocode.getF_distance_point() : geocode.getR_distance_point());
        }
    }

    private void setUpDetailView(GuideDetailViewHolder holder, int position) {
        final Geocode geocode = getItem(position);
        // height
        String height = StringUtil.formatDoubleToInteger(geocode.getElevation());
        height = String.format(Constants.GUIDE_OVERALL_HEIGHT_FORMAT, height);
        holder.detailHeight.setText(height);

        // mileage
        String road = geocode.getRoad();
        String milestone = geocode.getMilestone();
        if (!TextUtils.isEmpty(milestone)) {
            if (TextUtils.isEmpty(road)) {
                milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT_NO_ROAD, milestone);
            } else {
                milestone = String.format(Constants.GUIDE_OVERALL_MILESTONE_FORMAT, road, milestone);
            }
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
            holder.detailGuideContent.setVisibility(View.GONE);
        }

        if (holder.isExpanded) {
            holder.headerToggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_remove_circle_outline, TTTApplication.getMyColor(R.color.colorPrimary)));
            holder.detailToggleLayout.setVisibility(View.VISIBLE);
        } else {
            holder.headerToggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_add_circle, TTTApplication.getMyColor(R.color.colorPrimary)));
            holder.detailToggleLayout.setVisibility(View.GONE);
        }

        // 设置btn（hotel scenic）
        setUpDetailBtn(holder, position);

//        if (holder.isExpanded) {
//            setHotelBtnVisibility(holder);
//        }
    }

    /**
     * 设置最下方btn（hotel，scenic）
     *
     * @param holder
     */
    private void setUpDetailBtn(GuideDetailViewHolder holder, int position) {
        // hotel
        final Geocode geocode = getItem(position);
//        final String around = (String) holder.headerAroundContent.getTag();
        final String around = geocode.getAround_type();
        holder.hotelBtn.setVisibility(View.GONE);
        holder.scenicBtn.setVisibility(View.GONE);

        if (TextUtils.isEmpty(around)) {
            return;
        } else {
            if (around.contains(AroundType.HOTEL)) {
                // 当打开详细时会判断是否有旅店数据
                holder.hotelBtn.setVisibility(View.VISIBLE);
                holder.hotelBtn.setTag(geocode.getName());
                holder.hotelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ScreenUtil.isFastClick()) {
                            return;
                        }
                        DialogFragment dialogFragment = new AroundHotelInfoDialogFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(IntentExtra.INTENT_ROUTE, geocode.getRoute());
                        bundle.putString(IntentExtra.INTENT_AROUND_BELONG, geocode.getName());
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(((Activity) mContext).getFragmentManager(), AroundHotelInfoDialogFragment.class.getName());
                    }
                });
            }
            // scenic
            if (around.contains(AroundType.SCENIC)) {

                // 排除方向错误（正风景，但反向 || 反风景但正向），其他都显示风景
                if ((around.contains(AroundType.SCENIC_F) && !isForward) || (around.contains(AroundType.SCENIC_R) && isForward)) {
                    return;
                } else {
                    holder.scenicBtn.setVisibility(View.VISIBLE);
                    holder.scenicBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ScreenUtil.isFastClick()) {
                                return;
                            }
                            DialogFragment dialogFragment = new AroundScenicInfoDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(IntentExtra.INTENT_ROUTE, geocode.getRoute());
                            bundle.putString(IntentExtra.INTENT_AROUND_BELONG, geocode.getName());
                            bundle.putBoolean(IntentExtra.INTENT_ROUTE_DIR, isForward);
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show(((Activity) mContext).getFragmentManager(), AroundScenicInfoDialogFragment.class.getName());
                        }
                    });
                }
            }
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

        addAroundIcon(holder, position);
    }

    private void addAroundIcon(GuideDetailViewHolder holder, int position) {

        holder.headerAroundContent.removeAllViews();

        Geocode geocode = getItem(position);
        // logic to get around
        String aroundType = geocode.getAround_type();
        if (TextUtils.isEmpty(aroundType)) {
            return;
        }

        String[] arounds = aroundType.split(Constants.REPLACE_MARK);

        for (final String around : arounds) {

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ScreenUtil.dip2px(mContext, 15), ScreenUtil.dip2px(mContext, 15));
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.rightMargin = ScreenUtil.dip2px(mContext, 3);

            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(R.drawable.border_gray);

            int padding = ScreenUtil.dip2px(mContext, 2);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setLayoutParams(layoutParams);

            // 如果是风景类型，做特殊逻辑
            if (around.contains(AroundType.SCENIC)) {
                // 风景 || 正风景切正向 || 反风景且是反向
                if (around.equals(AroundType.SCENIC) || (around.equals(AroundType.SCENIC_F) && isForward) || (around.equals(AroundType.SCENIC_R) && !isForward)) {
                    imageView.setImageDrawable(AroundType.getAroundDrawableSrc(AroundType.SCENIC));
                    holder.headerAroundContent.addView(imageView);
                }
            } else {
                imageView.setImageDrawable(AroundType.getAroundDrawableSrc(around));
                holder.headerAroundContent.addView(imageView);
            }

        }
//        holder.headerAroundContent.setTag(aroundType);
    }

    public class GuideDetailViewHolder {

        // header
        TextView headerTitle;
        ImageView headerToggleButton;
        MaterialRippleLayout headerLayout;
        LinearLayout headerAroundContent;
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

        View hotelBtn;
        View scenicBtn;


        boolean isExpanded = false;

        public GuideDetailViewHolder(View v) {

            this.headerTitle = (TextView) v.findViewById(R.id.header_title);
            this.headerToggleButton = (ImageView) v.findViewById(R.id.expand_collapse);
            this.headerLayout = (MaterialRippleLayout) v.findViewById(R.id.header_layout);
            this.headerAroundContent = (LinearLayout) v.findViewById(R.id.header_around_content);
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

            this.hotelBtn = v.findViewById(R.id.hotel_btn);
            this.scenicBtn = v.findViewById(R.id.scenic_btn);

        }

    }

    /**
     * 打开视图
     */
    public void expandView(final GuideDetailViewHolder holder) {
        final Animation slideDown = AnimationUtils.loadAnimation(mContext, R.anim.card_slide_down);


        holder.headerToggleButton.setFocusable(false);
//        slideDown.setInterpolator(new BounceInterpolator());
        slideDown.setInterpolator(new AccelerateDecelerateInterpolator());


        holder.headerToggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_remove_circle_outline, TTTApplication.getMyColor(R.color.colorPrimary)));
        holder.detailToggleLayout.startAnimation(slideDown);
        holder.detailToggleLayout.setVisibility(View.VISIBLE);
        holder.distanceDivideContent.setVisibility(View.GONE);

//        setHotelBtnVisibility(holder);

        mExpandableListener.onExpand();

    }

    private void setHotelBtnVisibility(final GuideDetailViewHolder holder) {
        String hotelBelong = (String) holder.hotelBtn.getTag();
        Log.e("hotelBelong", hotelBelong);
        if (!TextUtils.isEmpty(hotelBelong)) {
            BmobQuery<HotelInfo> query = new BmobQuery<HotelInfo>();
//            query.addWhereContains("route", curRoute);
            query.addWhereContains("hotelBelong", hotelBelong);
            query.count(mContext, HotelInfo.class, new CountListener() {
                @Override
                public void onSuccess(int count) {
                    if (count > 0) {
                        holder.hotelBtn.setVisibility(View.VISIBLE);
                    } else {
                        holder.hotelBtn.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(int code, String msg) {
                    holder.hotelBtn.setVisibility(View.GONE);
                }
            });
        }
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

    // not used
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

    public String getCurRoute() {
        return curRoute;
    }

    public void setCurRoute(String curRoute) {
        this.curRoute = curRoute;
    }
}
