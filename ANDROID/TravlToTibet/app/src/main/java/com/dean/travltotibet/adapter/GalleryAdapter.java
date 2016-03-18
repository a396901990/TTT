package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AroundHotelActivity;
import com.dean.travltotibet.activity.AroundScenicActivity;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.GalleryInfo;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/17/15.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private String type;

    private ArrayList<GalleryInfo> mData;

    private Context mContext;

    public GalleryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_scenic_list_item_view, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {

        final GalleryInfo galleryInfo = mData.get(position);

        // 图片url(取第一个)
        String picURL = galleryInfo.getUrl().split(Constants.URL_MARK)[0];
        if (!TextUtils.isEmpty(picURL)) {
            Picasso.with(mContext).load(picURL).error(R.color.light_gray).into(holder.urlPic);
        }
        // 设置名称
        holder.urlName.setText(galleryInfo.getName());

        // 点击处理
        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                // hotel
                if (AroundType.HOTEL.equals(type)) {
                    Intent intent = new Intent(mContext, AroundHotelActivity.class);
                    intent.putExtra(IntentExtra.INTENT_GALLERY, galleryInfo);
                    mContext.startActivity(intent);
                }
                // scenic
                else if (AroundType.SCENIC.equals(type)) {
                    Intent intent = new Intent(mContext, AroundScenicActivity.class);
                    intent.putExtra(IntentExtra.INTENT_GALLERY, galleryInfo);
                    intent.putExtra(IntentExtra.INTENT_ROUTE_DIR, true);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    public void setData(ArrayList<GalleryInfo> data) {
        this.mData = data;
    }

    public void clearData() {
        if (mData == null) {
            return;
        } else {
            mData = new ArrayList<>();
        }
    }

    public void addData(ArrayList<GalleryInfo> addDatas) {
        mData.addAll(addDatas);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private ImageView urlPic;
        private TextView urlName;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            urlPic = (ImageView) itemView.findViewById(R.id.scenic_pic);
            urlName = (TextView) itemView.findViewById(R.id.scenic_name);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

    public void setType(String type) {
        this.type = type;
    }

}
