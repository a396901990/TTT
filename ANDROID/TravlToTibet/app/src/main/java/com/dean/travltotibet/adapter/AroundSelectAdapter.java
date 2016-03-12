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
import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AroundHotelActivity;
import com.dean.travltotibet.activity.AroundScenicActivity;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/17/15.
 */
public class AroundSelectAdapter extends RecyclerView.Adapter<AroundSelectAdapter.AroundSelectViewHolder> {

    private ArrayList<AroundItem> mData;

    ArrayList<Hotel> hotels;

    ArrayList<Scenic> mScenics;

    private Context mContext;

    private String aroundType;

    private boolean isForward;

    public AroundSelectAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(String routeName, String aroundBelong, String aroundType, boolean isForword) {

        mData = new ArrayList<>();
        this.aroundType = aroundType;
        this.isForward = isForword;

        // hotel
        if (aroundType.equals(AroundType.HOTEL)) {
            hotels = (ArrayList<Hotel>) TTTApplication.getDbHelper().getHotelListWithBelongName(routeName, aroundBelong);
            for (Hotel hotel : hotels) {
                AroundItem aroundItem = new AroundItem(hotel.getHotel_name(), null);
                mData.add(aroundItem);
            }
        }
        // scenic
        else if (aroundType.equals(AroundType.SCENIC)) {
            mScenics = (ArrayList<Scenic>) TTTApplication.getDbHelper().getScenicWithBelongName(routeName, aroundBelong, isForword);
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

        // 图片url(取第一个)
        String picURL = aroundItem.getAroundURL();
        if (!TextUtils.isEmpty(picURL)) {
            Picasso.with(mContext).load(picURL).error(R.color.light_gray).into(holder.aroundPic);
        }

        // 设置名称
        holder.aroundName.setText(aroundItem.getAroundName());

        // 点击处理
        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                // 跳转
                if (aroundType.equals(AroundType.HOTEL)) {
                    Intent intent = new Intent(mContext, AroundHotelActivity.class);
                    intent.putExtra(IntentExtra.INTENT_HOTEL, hotels.get(position));
                    intent.putExtra(IntentExtra.INTENT_ROUTE_DIR, isForward);
                    mContext.startActivity(intent);
                } else if (aroundType.equals(AroundType.SCENIC)) {
                    Intent intent = new Intent(mContext, AroundScenicActivity.class);
                    intent.putExtra(IntentExtra.INTENT_SCENIC, mScenics.get(position));
                    intent.putExtra(IntentExtra.INTENT_ROUTE_DIR, isForward);
                    mContext.startActivity(intent);
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
        private ImageView aroundPic;
        private TextView aroundName;

        public AroundSelectViewHolder(View itemView) {
            super(itemView);
            aroundPic = (ImageView) itemView.findViewById(R.id.around_pic);
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
