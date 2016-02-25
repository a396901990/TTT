package com.dean.travltotibet.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.InfoType;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 2/24/16.
 */
public class HotDestinationAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<String> mData;

    public HotDestinationAdapter(Context mContext) {
        this.mContext = mContext;
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
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.hot_destination_item_view, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.route_name);
        String routeName = (String) getItem(position);
        textView.setText(routeName);

        return convertView;
    }

    public void setData(ArrayList<String> mData) {
        this.mData = mData;
        notifyDataSetInvalidated();
    }
}
