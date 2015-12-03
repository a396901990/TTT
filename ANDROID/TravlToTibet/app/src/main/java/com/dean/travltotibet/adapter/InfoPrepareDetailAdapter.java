package com.dean.travltotibet.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dean.greendao.PrepareDetail;
import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.MaterialRippleLayout;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 4/10/15.
 */
public class InfoPrepareDetailAdapter extends BaseAdapter {

    private ArrayList<PrepareDetail> mData;
    private Context mContext;

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

        final Animation slidedown = AnimationUtils.loadAnimation(mContext, R.anim.slide_down);
        final Animation slideup = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);

        final ViewHolder holder = new ViewHolder(convertView);
        holder.toggleButton.setFocusable(false);
        slidedown.setInterpolator(new BounceInterpolator());

        slideup.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                holder.toggleLayout.setVisibility(View.GONE);
            }
        });


        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub


                if (holder.toggleButton.isChecked()) {
                    holder.toggleLayout.startAnimation(slidedown);
                    holder.toggleLayout.setVisibility(View.VISIBLE);

                } else {

                    holder.toggleLayout.startAnimation(slideup);

                }
            }

        });


        /**
         * ON CLICK LISTENER FOR CHILD
         * **/
        holder.headerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (holder.toggleButton.isChecked()) {
                    holder.toggleButton.setChecked(false);
                } else {
                    holder.toggleButton.setChecked(true);
                }

            }
        });

        TextView detailTitle = (TextView) convertView.findViewById(R.id.detail_title);
        TextView detailSummary = (TextView) convertView.findViewById(R.id.detail_summary);
        TextView detailItem = (TextView) convertView.findViewById(R.id.detail_item);

        PrepareDetail prepareDetail = mData.get(position);
        String title = prepareDetail.getTitle();
        String summary = prepareDetail.getSummary().replace("#", "\n");
        String item = prepareDetail.getDetail().replace("#", "\n");

        detailTitle.setText(title);

        // 如果summary为空则隐藏不显示
        if (TextUtils.isEmpty(summary)) {
            detailSummary.setVisibility(View.GONE);
        } else {
            detailSummary.setVisibility(View.VISIBLE);
            detailSummary.setText(summary);
        }

        detailItem.setText(item);

        return convertView;
    }

    public class ViewHolder {

        TextView detailTitle;
        TextView detailSummary;
        TextView detailItem;

        ToggleButton toggleButton;
        LinearLayout toggleLayout;
        MaterialRippleLayout headerLayout;

        public ViewHolder(View v) {

            this.detailTitle = (TextView) v.findViewById(R.id.detail_title);
            this.detailSummary = (TextView) v.findViewById(R.id.detail_summary);
            this.detailItem = (TextView) v.findViewById(R.id.detail_item);

            this.headerLayout = (MaterialRippleLayout) v.findViewById(R.id.header_layout);
            this.toggleLayout = (LinearLayout) v.findViewById(R.id.toggle_layout);
            this.toggleButton = (ToggleButton) v.findViewById(R.id.expand_collapse);
        }

    }
}
