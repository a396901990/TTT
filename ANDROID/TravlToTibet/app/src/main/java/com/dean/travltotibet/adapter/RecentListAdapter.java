package com.dean.travltotibet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 * Created by DeanGuo on 9/10/15.
 */
public class RecentListAdapter extends BaseAdapter {

    private ArrayList<RecentRoute> mData;
    private Context mContext;

    private ImageView mTitleView;
    private TextView mRouteName;
    private TextView mRouteStart;
    private TextView mRouteEnd;
    private TextView mPlanNameDay;

    public RecentListAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public void setData(ArrayList<RecentRoute> data) {
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.recent_list_item, null);
        }

        mTitleView = (ImageView) convertView.findViewById(R.id.type_icon);
        mRouteName = (TextView) convertView.findViewById(R.id.route_name);
        mRouteStart = (TextView) convertView.findViewById(R.id.route_start);
        mRouteEnd = (TextView) convertView.findViewById(R.id.route_end);
        mPlanNameDay = (TextView) convertView.findViewById(R.id.route_plan_name_day);

        RecentRoute recentRoute = mData.get(position);
        // 类型图片
        mTitleView.setImageDrawable(TravelType.getBlueTypeImageSrc(recentRoute.getType()));

        // 路线名称
        mRouteName.setText(recentRoute.getRoute_name());
        // 路线起点,终点
        String start = TTTApplication.getDbHelper().getFromName(recentRoute.getRoute(), recentRoute.getFR());
        String end = TTTApplication.getDbHelper().getToName(recentRoute.getRoute(), recentRoute.getFR());
        mRouteStart.setText(start);
        mRouteEnd.setText(end);

        // 计划描述，天数
        RoutePlan curRoutePlan = TTTApplication.getDbHelper().getRoutePlanWithPlanID(recentRoute.getRoute_plan_id());
        String name = curRoutePlan.getPlan_name();
        String day = curRoutePlan.getPlan_days();
        mPlanNameDay.setText(String.format(Constants.RECENT_PLAN_NAME_DAY, name, day));

        return convertView;
    }

}
