package com.dean.travltotibet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dean.travltotibet.R;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 3/9/15.
 */
public class CommonGridAdapter extends RecyclerView.Adapter<CommonGridAdapter.CommonViewHolder> {

    private Context mContext;

    private ArrayList<String> mData;

    public CommonGridAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private SelectCallBack selectCallBack;

    public static interface SelectCallBack {
        void onItemSelect(String name);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_grid_item_view, parent, false);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, int position) {

        final String item = mData.get(position);

        holder.itemName.setText(item);

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectCallBack != null) {
                    selectCallBack.onItemSelect(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<String> data) {
        this.mData = data;
        this.notifyItemRangeInserted(0, mData.size() - 1);
    }

    public void clearData() {
        if (mData == null) {
            return;
        } else {
            int size = this.mData.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    mData.remove(0);
                }

                this.notifyItemRangeRemoved(0, size);
            }
        }
    }

    public static class CommonViewHolder extends RecyclerView.ViewHolder {

        private View rippleLayout;
        private TextView itemName;

        public CommonViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            rippleLayout = itemView.findViewById(R.id.ripple_view);
        }
    }

    public SelectCallBack getSelectCallBack() {
        return selectCallBack;
    }

    public void setSelectCallBack(SelectCallBack selectCallBack) {
        this.selectCallBack = selectCallBack;
    }

}
