package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AroundScenicActivity;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/17/15.
 */
public class ScenicAdapter extends RecyclerView.Adapter<ScenicAdapter.ScenicViewHolder> {

    private ArrayList<Scenic> mData;

    private Context mContext;

    private RequestQueue mQueue;

    private ImageLoader imageLoader;

    public ScenicAdapter(Context mContext, ArrayList<Scenic> mData) {
        this.mContext = mContext;
        this.mData = mData;

        mQueue = Volley.newRequestQueue(mContext);

        imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
    }

    @Override
    public ScenicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_scenic_list_item_view, parent, false);
        return new ScenicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScenicViewHolder holder, int position) {

        final Scenic scenic = mData.get(position);

        // 默认图片
        holder.scenicPic.setDefaultImageResId(R.color.light_gray);
        // 错误图片
        holder.scenicPic.setErrorImageResId(R.color.gray);
        // 图片url(取第一个)
        String picURL = scenic.getScenic_pic().split(Constants.URL_MARK)[0];
        holder.scenicPic.setImageUrl(picURL, imageLoader);

        // 设置名称
        holder.scenicName.setText(scenic.getScenic_name());

        // 点击处理
        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转
                Intent intent = new Intent(mContext, AroundScenicActivity.class);
                intent.putExtra(IntentExtra.INTENT_SCENIC, scenic);
                intent.putExtra(IntentExtra.INTENT_ROUTE_DIR, true);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ScenicViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private NetworkImageView scenicPic;
        private TextView scenicName;

        public ScenicViewHolder(View itemView) {
            super(itemView);
            scenicPic = (NetworkImageView) itemView.findViewById(R.id.scenic_pic);
            scenicName = (TextView) itemView.findViewById(R.id.scenic_name);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }
}
