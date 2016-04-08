package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.greendao.Plan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ArticleActivity;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 3//16.
 */
public class PlanListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Plan> mData;

    private PlanItemListener mListener;

    public static interface PlanItemListener {
        public void onPlanClick(Plan plan);
    }

    public PlanListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
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

        final PlanViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_home_plan_list_item, parent, false);
            holder = new PlanViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PlanViewHolder) convertView.getTag();
        }


        final Plan plan = mData.get(position);

        holder.data.setText(String.format(Constants.HEADER_DAY, plan.getDay()));
        holder.detail_start.setText(plan.getStart());
        holder.detail_end.setText(plan.getEnd());
        holder.distance.setText(plan.getDistance());

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新chart视图
                mListener.onPlanClick(plan);
            }
        });

        return convertView;
    }

    public void setData(ArrayList<Plan> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mData == null) {
            return;
        } else {
            mData = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    public static class PlanViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView data;
        private TextView detail_start;
        private TextView detail_end;
        private TextView distance;

        public PlanViewHolder(View itemView) {
            data = (TextView) itemView.findViewById(R.id.plan_date);
            detail_start = (TextView) itemView.findViewById(R.id.plan_detail_start);
            detail_end = (TextView) itemView.findViewById(R.id.plan_detail_end);
            distance = (TextView) itemView.findViewById(R.id.plan_distance);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

    public void setListener(PlanItemListener mListener) {
        this.mListener = mListener;
    }

}
