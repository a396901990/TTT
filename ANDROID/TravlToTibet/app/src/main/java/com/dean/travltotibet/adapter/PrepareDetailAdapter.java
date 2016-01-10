package com.dean.travltotibet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dean.greendao.Plan;
import com.dean.greendao.PrepareDetail;
import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.MaterialRippleLayout;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/04/15.
 */
public class PrepareDetailAdapter extends RecyclerView.Adapter<PrepareDetailAdapter.PrepareDetailHolder> {

    private ArrayList<PrepareDetail> mData;
    private Context mContext;


    public PrepareDetailAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public PrepareDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prepare_detail_list_item_view, parent, false);
        return new PrepareDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(final PrepareDetailHolder holder, int position) {

        final Animation slidedown = AnimationUtils.loadAnimation(mContext, R.anim.card_slide_down);
        final Animation slideup = AnimationUtils.loadAnimation(mContext, R.anim.card_slide_up);

        holder.toggleButton.setFocusable(false);
        slidedown.setInterpolator(new BounceInterpolator());

        slideup.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                holder.toggleLayout.setVisibility(View.GONE);
            }
        });


        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub


                if (holder.toggleButton.isChecked()) {
                    holder.toggleLayout.startAnimation(slidedown);
                    holder.toggleLayout.setVisibility(View.VISIBLE);

                } else {

                    holder.toggleLayout.startAnimation(slideup);

                }
            }

        });


        /**
         * ON CLICK LISTENER FOR CHILD
         * **/
        holder.headerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (holder.toggleButton.isChecked()) {
                    holder.toggleButton.setChecked(false);
                } else {
                    holder.toggleButton.setChecked(true);
                }

            }
        });

        PrepareDetail prepareDetail = mData.get(position);
        String title = prepareDetail.getTitle();
        String summary = prepareDetail.getSummary();
        String item = prepareDetail.getDetail();

        holder.detailTitle.setText(title);

        // 如果summary为空则隐藏不显示
        if (TextUtils.isEmpty(summary)) {
            holder.detailSummary.setVisibility(View.GONE);
        } else {
            holder.detailSummary.setVisibility(View.VISIBLE);
            holder.detailSummary.setText(summary);
        }

        holder.detailItem.setText(item);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<PrepareDetail> data) {
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

    public static class PrepareDetailHolder extends RecyclerView.ViewHolder {

        TextView detailTitle;
        TextView detailSummary;
        TextView detailItem;

        ToggleButton toggleButton;
        LinearLayout toggleLayout;
        MaterialRippleLayout headerLayout;

        public PrepareDetailHolder(View itemView) {
            super(itemView);
            this.detailTitle = (TextView) itemView.findViewById(R.id.detail_title);
            this.detailSummary = (TextView) itemView.findViewById(R.id.detail_summary);
            this.detailItem = (TextView) itemView.findViewById(R.id.detail_item);

            this.headerLayout = (MaterialRippleLayout) itemView.findViewById(R.id.header_layout);
            this.toggleLayout = (LinearLayout) itemView.findViewById(R.id.toggle_layout);
            this.toggleButton = (ToggleButton) itemView.findViewById(R.id.expand_collapse);
        }
    }

    public static interface ItemClickListener {
        public void onItemClick(Plan plan);
    }

}
