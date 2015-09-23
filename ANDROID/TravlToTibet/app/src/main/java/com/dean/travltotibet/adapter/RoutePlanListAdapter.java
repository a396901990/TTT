package com.dean.travltotibet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/22/15.
 */
public class RoutePlanListAdapter extends BaseAdapter {

    private ArrayList<RoutePlan> mData;
    private Context mContext;

    private TextView planName;
    private TextView planDay;
    private TextView planDescribe;

    public RoutePlanListAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public void setData(ArrayList<RoutePlan> data) {
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
            convertView = mInflater.inflate(R.layout.route_plan_list_item, null);
        }

        planName = (TextView) convertView.findViewById(R.id.plan_name);
        planDay = (TextView) convertView.findViewById(R.id.plan_day);
        planDescribe = (TextView) convertView.findViewById(R.id.plan_describe);

        RoutePlan routePlan = mData.get(position);
        planName.setText(routePlan.getPlan_name());
        planDay.setText(routePlan.getPlan_days());
        planDescribe.setText(routePlan.getDescribe());

        return convertView;
    }

}
