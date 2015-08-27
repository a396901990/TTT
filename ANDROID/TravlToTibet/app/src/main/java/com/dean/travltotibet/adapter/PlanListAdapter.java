package com.dean.travltotibet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dean.travltotibet.R;

import java.util.ArrayList;

public class PlanListAdapter
    extends BaseAdapter
{
    TextView data;

    TextView detail_start;

    TextView detail_end;

    TextView distance;

    private ArrayList<PlanListItem> mPlans;

    private Context context;

    public PlanListAdapter(Context context)
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
            convertView = mInflater.inflate(R.layout.plan_list_item, null);
        }

        data = (TextView) convertView.findViewById(R.id.plan_date);
        detail_start = (TextView) convertView.findViewById(R.id.plan_detail_start);
        detail_end = (TextView) convertView.findViewById(R.id.plan_detail_end);
        distance = (TextView) convertView.findViewById(R.id.plan_distance);

        data.setText(mPlans.get(position).getPlanDate());
        detail_start.setText(mPlans.get(position).getPlanDetailStart());
        detail_end.setText(mPlans.get(position).getPlanDetailEnd());
        distance.setText(mPlans.get(position).getPlanDistance());

        return convertView;
    }

    public void setData( ArrayList<PlanListItem> mData )
    {
        this.mPlans = mData;
    }

    public static class PlanListItem
    {
        private String planDate;

        private String planDetailStart;

        private String planDetailEnd;

        private String planDistance;

        public PlanListItem( String planDate, String planDetailStart, String planDetailEnd)
        {
            super();
            this.planDate = planDate;
            this.planDetailStart = planDetailStart;
            this.planDetailEnd = planDetailEnd;
        }

        public PlanListItem( String planDate, String planDetailStart, String planDetailEnd, String planDistance)
        {
            super();
            this.planDate = planDate;
            this.planDetailStart = planDetailStart;
            this.planDetailEnd = planDetailEnd;
            this.planDistance = planDistance;
        }

        public String getPlanDate()
        {
            return planDate;
        }

        public String getPlanDetailStart() {
            return planDetailStart;
        }

        public String getPlanDetailEnd() {
            return planDetailEnd;
        }

        public String getPlanDistance() {
            return planDistance;
        }
    }

}
