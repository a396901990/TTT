package com.dean.travltotibet.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.dean.travltotibet.R;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.ui.SquareImageView;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/28/15.
 */
public class InfoGridAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<InfoType> mData;

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
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.info_prepare_grid_view, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.info_image);
        TextView textView = (TextView) convertView.findViewById(R.id.info_text);

        imageView.setImageResource(InfoType.INFO_IMAGE.get(getItem(position)));
        textView.setText(InfoType.INFO_TEXT.get(getItem(position)));
        return convertView;
    }

    public void setData(ArrayList<InfoType> mData) {
        this.mData = mData;
        notifyDataSetInvalidated();
    }
}
