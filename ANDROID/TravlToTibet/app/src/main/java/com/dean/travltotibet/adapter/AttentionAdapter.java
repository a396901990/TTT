package com.dean.travltotibet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.greendao.RouteAttention;
import com.dean.travltotibet.R;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/21/15.
 */
public class AttentionAdapter extends RecyclerView.Adapter<AttentionAdapter.AttentionViewHolder> {

    private ArrayList<RouteAttention> mData;

    @Override
    public AttentionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attention_list_item, parent, false);
        return new AttentionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttentionViewHolder holder, int position) {

        final RouteAttention attention = mData.get(position);
        holder.attentionTitle.setText(attention.getAttention_title());
        holder.attentionDetail.setText(attention.getAttention_detail());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<RouteAttention> data) {
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

    public static class AttentionViewHolder extends RecyclerView.ViewHolder {

        private TextView attentionTitle;
        private TextView attentionDetail;

        public AttentionViewHolder(View itemView) {
            super(itemView);

            attentionTitle = (TextView) itemView.findViewById(R.id.attention_title);
            attentionDetail = (TextView) itemView.findViewById(R.id.attention_detail);
        }
    }

}
