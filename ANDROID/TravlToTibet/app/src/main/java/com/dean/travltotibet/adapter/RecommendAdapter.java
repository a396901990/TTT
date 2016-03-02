package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/01/15.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    private Context mContext;

    private ArrayList<Route> mData;

    private RequestQueue mQueue;

    private ImageLoader imageLoader;

    public RecommendAdapter(Context context) {
        this.mContext = context;
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
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recommend_list_item, parent, false);
        return new RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        final Route route = mData.get(position);

        // 默认图片
        holder.backgroundView.setDefaultImageResId(R.color.light_gray);
        // 错误图片
        holder.backgroundView.setErrorImageResId(R.color.gray);
        // 图片url(取第一个)
        String picURL = route.getPic_url().split(Constants.URL_MARK)[0];
        holder.backgroundView.setImageUrl(picURL, imageLoader);

        holder.mainTitle.setText(route.getName());
        holder.subTitle.setText(route.getDescribe());

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                // 跳转到InfoRouteActivity(类型BIKE)
                Intent intent = new Intent(mContext, InfoActivity.class);
                intent.putExtra(IntentExtra.INTENT_ROUTE, route.getRoute());
                intent.putExtra(IntentExtra.INTENT_ROUTE_NAME, route.getName());
                intent.putExtra(IntentExtra.INTENT_ROUTE_TYPE, TravelType.BIKE);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<Route> mData) {
        this.mData = mData;
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

    public static class RecommendViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private NetworkImageView backgroundView;
        private TextView mainTitle;
        private TextView subTitle;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            backgroundView = (NetworkImageView) itemView.findViewById(R.id.background_view);
            mainTitle = (TextView) itemView.findViewById(R.id.main_title);
            subTitle = (TextView) itemView.findViewById(R.id.sub_title);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }
}
