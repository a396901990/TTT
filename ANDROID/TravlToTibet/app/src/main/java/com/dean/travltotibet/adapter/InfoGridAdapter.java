package com.dean.travltotibet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.SquareImageView;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/28/15.
 */
public class InfoGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<SquareImageView> mData;

    public InfoGridAdapter(Context mContext) {
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
        View v = convertView;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.grid_item, null);
        }

        return v;
    }

    public void setData(ArrayList<SquareImageView> mData) {
        this.mData = mData;
        notifyDataSetInvalidated();
    }
}
