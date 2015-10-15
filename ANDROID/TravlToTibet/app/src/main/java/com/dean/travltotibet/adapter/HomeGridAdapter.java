package com.dean.travltotibet.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.ui.SquareImageView;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 10/15/15.
 */
public class HomeGridAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Route> mData;

    private NetworkImageView backgroundView;

    private TextView frontTitle;

    private RequestQueue mQueue;

    private ImageLoader imageLoader;

    public HomeGridAdapter(Context mContext) {
        this.mContext = mContext;
        mQueue = Volley.newRequestQueue(mContext);

        imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });

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
            convertView = layoutInflater.inflate(R.layout.home_grid_view_item, null);
        }

        backgroundView = (NetworkImageView) convertView.findViewById(R.id.background_view);
        frontTitle = (TextView) convertView.findViewById(R.id.front_title);

        backgroundView.setDefaultImageResId(R.color.light_gray);
        backgroundView.setErrorImageResId(R.color.gray);
        backgroundView.setImageUrl(mData.get(position).getPic_url(), imageLoader);
        frontTitle.setText(mData.get(position).getName());
        return convertView;
    }

    public void setData(ArrayList<Route> mData) {
        this.mData = mData;
        notifyDataSetInvalidated();
    }
}
