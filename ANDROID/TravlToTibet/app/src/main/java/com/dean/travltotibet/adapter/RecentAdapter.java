package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.greendao.RecentRoute;
import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by DeanGuo on 11/30/15.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {

    private Context mContext;

    private ArrayList<RecentRoute> mData;

    private RecentCallBack mRecentCallBack;

    private Activity mActivity;

    public static interface RecentCallBack {
        void update();
    }

    public RecentAdapter(Context mContext) {
        this.mContext = mContext;
        mActivity = (Activity) mContext;
    }

    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recent_list_item, parent, false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecentViewHolder holder, final int position) {

        final RecentRoute recentRoute = mData.get(position);
        // 类型图片
        holder.mTitleView.setImageDrawable(TravelType.getTypeImageSrcWithColor(recentRoute.getType(), R.color.white));

        // 路线名称
        holder.mRouteName.setText(recentRoute.getRoute_name());
        // 路线起点,终点
        String start = TTTApplication.getDbHelper().getFromName(recentRoute.getRoute(), recentRoute.getFR());
        String end = TTTApplication.getDbHelper().getToName(recentRoute.getRoute(), recentRoute.getFR());
        holder.mRouteStartEnd.setText(String.format(Constants.HEADER_START_END, start, end));

        // 计划描述，天数
        RoutePlan curRoutePlan = TTTApplication.getDbHelper().getRoutePlanWithPlanID(recentRoute.getRoute_plan_id());
        String name = curRoutePlan.getPlan_name();
        String day = curRoutePlan.getPlan_days();
        holder.mPlanName.setText(name);
        holder.mPlanDay.setText(day);

        // 图片url(随机)
        String[] picURLs = TTTApplication.getDbHelper().getRoutePics(recentRoute.getRoute());
        Random random = new Random();
        int picId = random.nextInt(picURLs.length);
        if (!TextUtils.isEmpty(picURLs[picId])) {
            Picasso.with(mContext).load(picURLs[picId]).error(R.color.light_gray).into(holder.mBackgroundView);
        }

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                // 跳转到RouteActivity
                Intent intent = new Intent(mContext, RouteActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROUTE, recentRoute.getRoute());
                intent.putExtra(IntentExtra.INTENT_ROUTE_TYPE, recentRoute.getType());
                intent.putExtra(IntentExtra.INTENT_ROUTE_DIR, recentRoute.getFR());
                intent.putExtra(IntentExtra.INTENT_ROUTE_PLAN_ID, Long.parseLong(recentRoute.getRoute_plan_id()));
                mContext.startActivity(intent);
            }
        });

        holder.mDelIcon.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_delete, TTTApplication.getMyColor(R.color.white)));
        holder.mDelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRouteRecent(recentRoute, position);
            }
        });
    }

    private void deleteRouteRecent(final RecentRoute recentRoute, final int position) {
        if (ScreenUtil.isFastClick()) {
            return;
        }
        new MaterialDialog.Builder(mActivity)
                .title(mActivity.getString(R.string.delete_recent_title))
                .content(mActivity.getString(R.string.delete_recent_msg))
                .positiveText(mActivity.getString(R.string.ok_btn))
                .negativeText(mActivity.getString(R.string.cancel_btn))
                .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        TTTApplication.getDbHelper().deleteRecentRoute(recentRoute);
                        mData.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mData.size());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<RecentRoute> data) {
        this.mData = data;
//        this.notifyItemRangeInserted(0, mData.size() - 1);
        this.notifyDataSetChanged();
    }

    public void clearData() {
        if (mData == null) {
            return;
        } else {
            int size = this.mData.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    mData.remove(0);
                }

                this.notifyItemRangeRemoved(0, size);
            }
        }
    }

    public static class RecentViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private ImageView mTitleView;
        private TextView mRouteName;
        private TextView mRouteStartEnd;
        private TextView mPlanName;
        private TextView mPlanDay;
        private ImageView mDelIcon;
        private ImageView mBackgroundView;

        public RecentViewHolder(View itemView) {
            super(itemView);
            mTitleView = (ImageView) itemView.findViewById(R.id.type_icon);
            mDelIcon = (ImageView) itemView.findViewById(R.id.del_icon);
            mRouteName = (TextView) itemView.findViewById(R.id.route_name);
            mRouteStartEnd = (TextView) itemView.findViewById(R.id.route_start_end);
            mPlanName = (TextView) itemView.findViewById(R.id.route_plan_name);
            mPlanDay = (TextView) itemView.findViewById(R.id.route_plan_day);
            mBackgroundView = (ImageView) itemView.findViewById(R.id.background_image);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

    public RecentCallBack getRecentCallBack() {
        return mRecentCallBack;
    }

    public void setRecentCallBack(RecentCallBack mRecentCallBack) {
        this.mRecentCallBack = mRecentCallBack;
    }

}
