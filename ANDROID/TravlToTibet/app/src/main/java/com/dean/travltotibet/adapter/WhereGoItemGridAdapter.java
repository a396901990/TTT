package com.dean.travltotibet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dean.greendao.PrepareDetail;
import com.dean.travltotibet.R;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 4/10/15.
 */
public class WhereGoItemGridAdapter extends BaseAdapter {

    private ArrayList<PlaceItem> mData;
    private Context mContext;

    public WhereGoItemGridAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public void setData(ArrayList<PlaceItem> data) {
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

        PlaceItem placeItem = mData.get(position);
        String title = placeItem.getPlaceName();
        String distance = placeItem.getPlaceDistance();

        placeTitle.setText(title);
        placeDistance.setText(distance);

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
