package com.dean.travltotibet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.greendao.RecentRoute;
import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 11/30/15.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {

    private ArrayList<RecentRoute> mData;

    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_list_item,
                parent, false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentViewHolder holder, int position) {

        RecentRoute recentRoute = mData.get(position);
        // 类型图片
        holder.mTitleView.setImageDrawable(TravelType.getWhiteTypeImageSrc(recentRoute.getType()));

        // 路线名称
        holder.mRouteName.setText(recentRoute.getRoute_name());
        // 路线起点,终点
        String start = TTTApplication.getDbHelper().getFromName(recentRoute.getRoute(), recentRoute.getFR());
        String end = TTTApplication.getDbHelper().getToName(recentRoute.getRoute(), recentRoute.getFR());
        holder.mRouteStart.setText(start);
        holder.mRouteEnd.setText(end);

        // 计划描述，天数
        RoutePlan curRoutePlan = TTTApplication.getDbHelper().getRoutePlanWithPlanID(recentRoute.getRoute_plan_id());
        String name = curRoutePlan.getPlan_name();
        String day = curRoutePlan.getPlan_days();
        holder.mPlanNameDay.setText(String.format(Constants.RECENT_PLAN_NAME_DAY, name, day));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<RecentRoute> data) {
        this.mData = data;
    }

    class RecentViewHolder extends RecyclerView.ViewHolder {

        private ImageView mTitleView;
        private TextView mRouteName;
        private TextView mRouteStart;
        private TextView mRouteEnd;
        private TextView mPlanNameDay;

        public RecentViewHolder(View itemView) {
            super(itemView);
            mTitleView = (ImageView) itemView.findViewById(R.id.type_icon);
            mRouteName = (TextView) itemView.findViewById(R.id.route_name);
            mRouteStart = (TextView) itemView.findViewById(R.id.route_start);
            mRouteEnd = (TextView) itemView.findViewById(R.id.route_end);
            mPlanNameDay = (TextView) itemView.findViewById(R.id.route_plan_name_day);
        }
    }
}
