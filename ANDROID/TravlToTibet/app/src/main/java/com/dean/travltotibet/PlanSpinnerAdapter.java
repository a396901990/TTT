package com.dean.travltotibet;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlanSpinnerAdapter
    extends BaseAdapter
{
    TextView data;

    TextView detail;

    private ArrayList<PlanNavItem> mPlans;

    private Context context;

    public PlanSpinnerAdapter( Context context )
    {
        super();
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return mPlans.size();
    }

    @Override
    public Object getItem( int position )
    {
        return mPlans.get(position);
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        if (convertView == null)
        {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.plan_nav_layout, null);
        }

        data = (TextView) convertView.findViewById(R.id.plan_dropdown_title);
        data.setText(mPlans.get(position).getPlanDate());
        
        return convertView;
    }

    @Override
    public View getDropDownView( int position, View convertView, ViewGroup parent )
    {
        if (convertView == null)
        {
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.plan_item_layout, null);
        }

        data = (TextView) convertView.findViewById(R.id.plan_date);
        detail = (TextView) convertView.findViewById(R.id.plan_detail);

        data.setText(mPlans.get(position).getPlanDate());
        detail.setText(mPlans.get(position).getPlanDetail());

        return convertView;
    }

    public void setData( ArrayList<PlanNavItem> mData )
    {
        this.mPlans = mData;
    }

    public static class PlanNavItem
    {
        private String planDate;

        private String planDetail;

        public PlanNavItem( String planDate, String planDetail )
        {
            super();
            this.planDate = planDate;
            this.planDetail = planDetail;
        }

        public String getPlanDate()
        {
            return planDate;
        }

        public void setPlanDate( String planDate )
        {
            this.planDate = planDate;
        }

        public String getPlanDetail()
        {
            return planDetail;
        }

        public void setPlanDetail( String planDetail )
        {
            this.planDetail = planDetail;
        }
    }

}
