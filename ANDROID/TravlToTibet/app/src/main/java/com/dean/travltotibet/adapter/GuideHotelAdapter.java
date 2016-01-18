package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.greendao.Hotel;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.HotelDetailDialog;
import com.dean.travltotibet.ui.AnimatedExpandableListView;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 12/5/15.
 */
public class GuideHotelAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    private Activity mActivity;
    private ArrayList<PlaceHotel> mData;

    public GuideHotelAdapter(Context context) {
        mActivity = (Activity) context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<PlaceHotel> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public Hotel getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).hotels.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
        HotelChildHolder holder;
        if (convertView == null) {
            holder = new HotelChildHolder();
            convertView = inflater.inflate(R.layout.route_guide_hotel_detail_list_item, parent, false);

            holder.hotelName = (TextView) convertView.findViewById(R.id.hotel_name);
            holder.hotelType = (TextView) convertView.findViewById(R.id.hotel_type);
            holder.rippleLayout = (MaterialRippleLayout) convertView.findViewById(R.id.ripple_view);

            convertView.setTag(holder);
        } else {
            holder = (HotelChildHolder) convertView.getTag();
        }

        /** set data */
        final Hotel hotel = getChild(groupPosition, childPosition);
        if (hotel != null) {
            holder.hotelName.setText(hotel.getHotel_name());
            holder.hotelType.setText(hotel.getHotel_type());
        }


        // 设置按键监听
        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new HotelDetailDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentExtra.INTENT_HOTEL, hotel);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(mActivity.getFragmentManager(), HotelDetailDialog.class.getName());
            }
        });

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return mData.get(groupPosition).hotels.size();
    }

    @Override
    public PlaceHotel getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final HotelGroupHolder holder;

        if (convertView == null) {
            holder = new HotelGroupHolder();
            convertView = inflater.inflate(R.layout.route_guide_hotel_header_list_item, parent,
                    false);
            holder.headerTitle = (TextView) convertView.findViewById(R.id.header_title);
            holder.toggleButton = (ImageView) convertView.findViewById(R.id.expand_collapse);
            convertView.setTag(holder);
        } else {
            holder = (HotelGroupHolder) convertView.getTag();
        }

        // 设置标题
        PlaceHotel item = getGroup(groupPosition);
        holder.headerTitle.setText(item.title);

        // 设置标题按钮
        if (isExpanded) {
            holder.toggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_remove_circle_outline, TTTApplication.getMyColor(R.color.colorPrimary)));
        } else {
            holder.toggleButton.setImageDrawable(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_add_circle, TTTApplication.getMyColor(R.color.colorPrimary)));
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    public static class HotelGroupHolder {
        TextView headerTitle;
        ImageView toggleButton;
    }

    private static class HotelChildHolder {
        TextView hotelName;
        TextView hotelType;
        MaterialRippleLayout rippleLayout;
    }

    public static class PlaceHotel {
        String title;
        List<Hotel> hotels = new ArrayList<Hotel>();

        public PlaceHotel(String title, List<Hotel> hotels) {
            this.title = title;
            this.hotels = hotels;
        }
    }
}