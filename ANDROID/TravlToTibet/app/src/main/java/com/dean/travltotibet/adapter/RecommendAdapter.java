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

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 12/01/15.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    private Context mContext;

    private ArrayList<Route> mData;

    public RecommendAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recommend_list_item, parent, false);
        return new RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        final Route route = mData.get(position);

        // 图片url(取第一个)
        if (!TextUtils.isEmpty(route.getPic_url().trim())) {
            String picURL = route.getPic_url().split(Constants.URL_MARK)[0];
            Picasso.with(mContext)
                    .load(picURL)
                    .resizeDimen(R.dimen.home_recommend_item_width, R.dimen.home_recommend_item_height)
                    .error(R.color.light_gray)
                    .centerInside()
                    .into(holder.backgroundView);
        }

        holder.mainTitle.setText(route.getName());
        holder.subTitle.setText(route.getDescribe());
//        if (!TextUtils.isEmpty(route.getDay())) {
//            holder.roadTitle.setText(route.getDay());
//            holder.roadTitle.setVisibility(View.VISIBLE);
//        } else {
//            holder.roadTitle.setVisibility(View.GONE);
//        }
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

    public static class RecommendViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private ImageView backgroundView;
        private TextView mainTitle;
        private TextView subTitle;
        private TextView roadTitle;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            backgroundView = (ImageView) itemView.findViewById(R.id.background_view);
            mainTitle = (TextView) itemView.findViewById(R.id.main_title);
            subTitle = (TextView) itemView.findViewById(R.id.sub_title);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
            roadTitle = (TextView) itemView.findViewById(R.id.road_title);
        }
    }
}
