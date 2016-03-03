package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.TeamShowRequestCommentActivity;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 2/17/16.
 */
public class TeamRequestAdapter extends RecyclerView.Adapter<TeamRequestAdapter.TeamRequestViewHolder> {

    private Context mContext;

    private ArrayList<TeamRequest> mData;

    public TeamRequestAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public TeamRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_request_list_item, parent, false);
        return new TeamRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TeamRequestViewHolder holder, int position) {

        final TeamRequest request = mData.get(position);

        holder.mTitle.setText(request.getTitle());
        holder.mTypeIcon.setImageDrawable(TravelType.getTypeImageSrcWithColor(request.getTravelType(), R.color.white));
        holder.mDestinationName.setText(request.getDestination());
        holder.mDate.setText(String.format(Constants.TEAM_REQUEST_DAY, request.getStartDate(), request.getEndDate()));
        holder.mUserTime.setText(String.format(Constants.TEAM_REQUEST_USER_TIME, request.getUserName(), request.getCreatedAt()));
        holder.mWatch.setText(request.getWatch()+"");
        holder.mComment.setText(request.getComments() + "");

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, TeamShowRequestCommentActivity.class);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, request);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<TeamRequest> data) {
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


    public static class TeamRequestViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView mTitle;
        private TextView mDestinationName;
        private TextView mUserTime;
        private TextView mDate;
        private TextView mWatch;
        private TextView mComment;
        private ImageView mTypeIcon;

        public TeamRequestViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDestinationName = (TextView) itemView.findViewById(R.id.destination_name);
            mDate = (TextView) itemView.findViewById(R.id.plan_date);
            mUserTime = (TextView) itemView.findViewById(R.id.user_time);
            mTypeIcon = (ImageView) itemView.findViewById(R.id.type_icon);
            mWatch = (TextView) itemView.findViewById(R.id.watch);
            mComment = (TextView) itemView.findViewById(R.id.comment);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

}
