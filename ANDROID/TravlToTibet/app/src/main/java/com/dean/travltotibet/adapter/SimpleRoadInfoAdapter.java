package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.greendao.Plan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RoadInfoDetailActivity;
import com.dean.travltotibet.model.RoadInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/04/15.
 */
public class SimpleRoadInfoAdapter extends RecyclerView.Adapter<SimpleRoadInfoAdapter.SimpleRoadInfoViewHolder> {

    private Context mContext;

    private ArrayList<RoadInfo> mData;

    private PlanItemListener mListener;

    public SimpleRoadInfoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public SimpleRoadInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_road_info_list_item, parent, false);
        return new SimpleRoadInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleRoadInfoViewHolder holder, int position) {

        final RoadInfo roadInfo = mData.get(position);
        holder.roadText.setText(roadInfo.getTitle());
        holder.priorityIcon.setImageResource(RoadInfo.getPriorityIcon(roadInfo.getPriority()));
        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, RoadInfoDetailActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROAD, roadInfo);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<RoadInfo> data) {
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


    public static class SimpleRoadInfoViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView roadText;
        private ImageView priorityIcon;

        public SimpleRoadInfoViewHolder(View itemView) {
            super(itemView);
            roadText = (TextView) itemView.findViewById(R.id.road_text);
            priorityIcon = (ImageView) itemView.findViewById(R.id.priority_icon);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

    public static interface PlanItemListener {
        public void onPlanClick(Plan plan);
    }

}
