package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.dean.greendao.Geocode;
import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AroundHotelActivity;
import com.dean.travltotibet.activity.AroundScenicActivity;
import com.dean.travltotibet.model.Around;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 12/17/15.
 */
public class AroundSelectAdapter extends RecyclerView.Adapter<AroundSelectAdapter.AroundSelectViewHolder> {

    private ArrayList<AroundItem> mData;

    ArrayList<Hotel> hotels;

    ArrayList<Scenic> mScenics;

    private Context mContext;

    private RequestQueue mQueue;

    private ImageLoader imageLoader;

    private String aroundBelong;

    private String aroundType;

    private String routeName;

    public AroundSelectAdapter(Context mContext, String routeName, String aroundBelong, String aroundType) {
        this.mContext = mContext;
        this.routeName = routeName;
        this.aroundBelong = aroundBelong;
        this.aroundType = aroundType;

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
        setData();
    }

    public void setData() {

        mData = new ArrayList<>();

        if (aroundType.equals(Around.HOTEL)) {
            hotels = (ArrayList<Hotel>) TTTApplication.getDbHelper().getHotelList(routeName, aroundBelong);
            for (Hotel hotel : hotels) {
                AroundItem aroundItem = new AroundItem(hotel.getHotel_name(), null);
                mData.add(aroundItem);
            }
        } else if (aroundType.equals(Around.SCENIC)) {
            mScenics = (ArrayList<Scenic>) TTTApplication.getDbHelper().getScenicList(routeName);
            for (Scenic scenic : mScenics) {
                String url = scenic.getScenic_pic().split(Constants.URL_MARK)[0];
                AroundItem aroundItem = new AroundItem(scenic.getScenic_name(), url);
                mData.add(aroundItem);
            }
        }
    }

    @Override
    public AroundSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.around_select_list_item_view, parent, false);
        return new AroundSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AroundSelectViewHolder holder, final int position) {

        final AroundItem aroundItem = mData.get(position);

        // 默认图片
        holder.aroundPic.setDefaultImageResId(R.color.colorPrimary);
        // 错误图片
        holder.aroundPic.setErrorImageResId(R.color.colorPrimary);
        // 图片url(取第一个)
        String picURL = aroundItem.getAroundURL();
        holder.aroundPic.setImageUrl(picURL, imageLoader);

        // 设置名称
        holder.aroundName.setText(aroundItem.getAroundName());

        // 点击处理
        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转
                if (aroundType.equals(Around.HOTEL)) {
                    Intent intent = new Intent(mContext, AroundHotelActivity.class);
                    intent.putExtra(IntentExtra.INTENT_HOTEL, hotels.get(position));
                    mContext.startActivity(intent);
//                    ((Activity)mContext).finish();
                } else if (aroundType.equals(Around.SCENIC)) {
                    Intent intent = new Intent(mContext, AroundScenicActivity.class);
                    intent.putExtra(IntentExtra.INTENT_SCENIC, mScenics.get(position));
                    mContext.startActivity(intent);
//                    ((Activity)mContext).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class AroundSelectViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private NetworkImageView aroundPic;
        private TextView aroundName;

        public AroundSelectViewHolder(View itemView) {
            super(itemView);
            aroundPic = (NetworkImageView) itemView.findViewById(R.id.around_pic);
            aroundName = (TextView) itemView.findViewById(R.id.around_name);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

    public static class AroundItem {

        private String aroundName;
        private String aroundURL;

        public AroundItem(String aroundName, String aroundURL) {
            this.aroundName = aroundName;
            this.aroundURL = aroundURL;
        }

        public String getAroundName() {
            return aroundName;
        }

        public String getAroundURL() {
            return aroundURL;
        }
    }
}
