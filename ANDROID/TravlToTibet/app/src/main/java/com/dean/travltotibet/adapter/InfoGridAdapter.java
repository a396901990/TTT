package com.dean.travltotibet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.dean.travltotibet.R;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.ui.SquareImageView;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/28/15.
 */
public class InfoGridAdapter extends BaseAdapter {

    private Context mContext;

    private InfoType[] mData;

    public InfoGridAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SquareImageView content = new SquareImageView(mContext);
        content.setImageSrc(InfoType.INFO_IMAGE.get(getItem(position)));
        content.setLabelText(InfoType.INFO_TEXT.get(getItem(position)));
        content.setBackgroundResource(InfoType.INFO_COLOR.get(getItem(position)));
        //content.setBackgroundResource(R.color.light_blue);
        content.setClickable(false);
        content.setFocusable(false);
        content.setFocusableInTouchMode(false);
        return content;
    }

    public void setData(InfoType[] mData) {
        this.mData = mData;
        notifyDataSetInvalidated();
    }
}
