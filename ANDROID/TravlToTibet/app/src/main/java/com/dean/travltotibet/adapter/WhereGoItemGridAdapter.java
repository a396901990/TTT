package com.dean.travltotibet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dean.greendao.PrepareDetail;
import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 4/10/15.
 */
public class WhereGoItemGridAdapter extends BaseAdapter {

    private ArrayList<Route> mData;
    private Context mContext;

    public WhereGoItemGridAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public void setData(ArrayList<Route> data) {
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.where_go_place_item, null);
        }

        TextView placeTitle = (TextView) convertView.findViewById(R.id.place_title);
        TextView placeDistance = (TextView) convertView.findViewById(R.id.place_distance);

        Route routeItem = mData.get(position);

        String title = routeItem.getName();
        String distance = routeItem.getDistance();
        placeTitle.setText(title);
        placeDistance.setText(distance);

        NumberProgressBar rateHard = (NumberProgressBar) convertView.findViewById(R.id.rate_hard);
        NumberProgressBar rateView = (NumberProgressBar) convertView.findViewById(R.id.rate_view);
        NumberProgressBar rateRoad = (NumberProgressBar) convertView.findViewById(R.id.rate_road);
        if (!TextUtils.isEmpty(routeItem.getRank_hard())) {
            rateHard.setProgress(Integer.parseInt(routeItem.getRank_hard()));
        }
        if (!TextUtils.isEmpty(routeItem.getRank_view())) {
            rateView.setProgress(Integer.parseInt(routeItem.getRank_view()));
        }
        if (!TextUtils.isEmpty(routeItem.getRank_road())) {
            rateRoad.setProgress(Integer.parseInt(routeItem.getRank_road()));
        }

        return convertView;
    }

    public static class PlaceItem {
        private String placeName;
        private String placeDistance;
        private String placeRate;

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getPlaceDistance() {
            return placeDistance;
        }

        public void setPlaceDistance(String placeDistance) {
            this.placeDistance = placeDistance;
        }

        public String getPlaceRate() {
            return placeRate;
        }

        public void setPlaceRate(String placeRate) {
            this.placeRate = placeRate;
        }
    }

}
