package com.dean.travltotibet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.greendao.Plan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/04/15.
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private Context mContext;

    private ArrayList<Plan> mData;

    private PlanItemListener mListener;

    private Plan lastPlan;

    public PlanAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_plan_list_item, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {

        final Plan plan = mData.get(position);

        // 设置背景颜色
        if (plan.equals(lastPlan)) {
            holder.planContentView.setBackgroundColor(TTTApplication.getMyColor(R.color.half_colorPrimary));
        } else {
            holder.planContentView.setBackgroundColor(TTTApplication.getMyColor(R.color.less_gray_background));
        }

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
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<Plan> data) {
        this.mData = data;
        this.notifyItemRangeInserted(0, mData.size() - 1);
    }

    public void clearData() {
        int size = this.mData.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mData.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void setPlanListener(PlanItemListener planListener) {
        this.mListener = planListener;
    }


    public static class PlanViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private View planContentView;
        private TextView data;
        private TextView detail_start;
        private TextView detail_end;
        private TextView distance;

        public PlanViewHolder(View itemView) {
            super(itemView);
            data = (TextView) itemView.findViewById(R.id.plan_date);
            detail_start = (TextView) itemView.findViewById(R.id.plan_detail_start);
            detail_end = (TextView) itemView.findViewById(R.id.plan_detail_end);
            distance = (TextView) itemView.findViewById(R.id.plan_distance);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
            planContentView = itemView.findViewById(R.id.plan_content_view);
        }
    }

    public static interface PlanItemListener {
        public void onPlanClick(Plan plan);
    }

    public void setLastPlan(Plan lastPlan) {
        this.lastPlan = lastPlan;
        notifyDataSetChanged();
    }
}
