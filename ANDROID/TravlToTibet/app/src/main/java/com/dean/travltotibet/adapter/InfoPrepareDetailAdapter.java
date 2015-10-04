package com.dean.travltotibet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dean.greendao.PrepareDetail;
import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 4/10/15.
 */
public class InfoPrepareDetailAdapter extends BaseAdapter {

    private ArrayList<PrepareDetail> mData;
    private Context mContext;

    private TextView detailTitle;
    private TextView detailItem;

    public InfoPrepareDetailAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public void setData(ArrayList<PrepareDetail> data) {
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
            convertView = mInflater.inflate(R.layout.info_prepare_detail_item, null);
        }

        detailTitle = (TextView) convertView.findViewById(R.id.detail_title);
        detailItem = (TextView) convertView.findViewById(R.id.detail_item);

        PrepareDetail prepareDetail = mData.get(position);
        String title = prepareDetail.getTitle();
        String item = prepareDetail.getDetail().replace("#", "\n");

        detailTitle.setText(title);
        detailItem.setText(item);

        return convertView;
    }

}
