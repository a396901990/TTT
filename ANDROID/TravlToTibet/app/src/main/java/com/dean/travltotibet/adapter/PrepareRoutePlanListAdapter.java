package com.dean.travltotibet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/22/15.
 */
public class PrepareRoutePlanListAdapter extends BaseAdapter {

    private ListView mListView;
    private ArrayList<RoutePlan> mData;
    private Context mContext;

    private TextView planName;
    private TextView planDay;
    private TextView planDescribe;
    private LinearLayout footerView;

    public PrepareRoutePlanListAdapter(Context context, ListView listView) {
        super();
        this.mContext = context;
        this.mListView = listView;
        footerView = new LinearLayout(mContext);
    }

    public void setData(ArrayList<RoutePlan> data) {
        this.mData = data;
//        setHeight();
    }

    private void setHeight() {

        footerView.setBackgroundColor(TTTApplication.getMyColor(R.color.white));
        int height = 180;
        int listHeight = ScreenUtil.dip2px(mContext, 60) * 3;

//        mListView.removeFooterView(footerView);
        if (getCount() < 3) {
//            footerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(mContext, 180)));
//            mListView.addFooterView(footerView);
            mListView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, listHeight));
        }

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
            convertView = mInflater.inflate(R.layout.info_plan_confirm_list_item, null);
        }

        planName = (TextView) convertView.findViewById(R.id.plan_name);
        planDay = (TextView) convertView.findViewById(R.id.plan_day);
        planDescribe = (TextView) convertView.findViewById(R.id.plan_describe);

        RoutePlan routePlan = mData.get(position);
        planName.setText(routePlan.getPlan_name());
        planDay.setText(String.format(Constants.ROUTE_PLAN_DAY, routePlan.getPlan_days()));

        if (TextUtils.isEmpty(routePlan.getDescribe())) {
//            planDescribe.setVisibility(View.GONE);
            planDescribe.setText("");
        } else {
//            planDescribe.setVisibility(View.VISIBLE);
            planDescribe.setText(routePlan.getDescribe());
        }
        return convertView;
    }

}
