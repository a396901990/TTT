package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.TeamShowRequestActivity;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 3//16.
 */
public class TeamRequestListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<TeamRequest> mData;

    private RequestQueue mQueue;

    public TeamRequestListAdapter(Context mContext) {
        this.mContext = mContext;
        mQueue = Volley.newRequestQueue(mContext);
    }

    private boolean isPersonal = false;

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
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

        final TeamRequestViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_show_request_list_item, parent, false);
            holder = new TeamRequestViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (TeamRequestViewHolder) convertView.getTag();
        }

        final TeamRequest request = mData.get(position);

        // 是否通过审核显示警告视图
        if (request.isPass) {
            holder.mWarningView.setVisibility(View.GONE);
        } else {
            holder.mWarningView.setVisibility(View.VISIBLE);
        }

        // content
        holder.mContentText.setText(request.getContent());

        // destination
        holder.mDestinationName.setText(request.getDestination());
        // type
        holder.mTypeName.setText(request.getType());
        // date
        holder.mDateName.setText(request.getDate());

        // publish time
        String createTime = DateUtil.getTimeGap(request.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        holder.mPublishTime.setText(createTime);
        // user name
        holder.mUserName.setText(request.getUserName());
        if (UserInfo.MALE.equals(request.getUserGender())) {
            holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
            holder.mUserGender.setBackgroundResource(R.drawable.male_gender_view);
        } else {
            holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
            holder.mUserGender.setBackgroundResource(R.drawable.female_gender_view);
        }
        // user icon
        if (!TextUtils.isEmpty(request.getUserIcon())) {
            ImageRequest imageRequest = new ImageRequest(
                    request.getUserIcon(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder.mUserIcon.setImageBitmap(response);
                        }
                    }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    holder.mUserIcon.setImageResource(R.drawable.gray_profile);
                }
            });
            mQueue.add(imageRequest);
        } else {
            holder.mUserIcon.setImageResource(R.drawable.gray_profile);
        }

//        holder.mWatch.setText(request.getWatch()+"");

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, TeamShowRequestActivity.class);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, request);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, isPersonal);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public void setData(ArrayList<TeamRequest> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mData == null) {
            return;
        } else {
            mData = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    public class TeamRequestViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView mContentText;

        private TextView mDestinationName;
        private TextView mTypeName;
        private TextView mDateName;

        private TextView mUserName;
        private View mUserGender;
        private CircleImageView mUserIcon;
        private TextView mPublishTime;

        private TextView mWatch;
        private View mWarningView;

        public TeamRequestViewHolder(View itemView) {
            mContentText = (TextView) itemView.findViewById(R.id.content_text);
            mDestinationName = (TextView) itemView.findViewById(R.id.destination_name);
            mDateName = (TextView) itemView.findViewById(R.id.date_name);
            mTypeName = (TextView) itemView.findViewById(R.id.type_name);

            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mUserGender = itemView.findViewById(R.id.user_gender);
            mUserIcon = (CircleImageView) itemView.findViewById(R.id.user_icon);

            mPublishTime = (TextView) itemView.findViewById(R.id.publish_time);
            mWatch = (TextView) itemView.findViewById(R.id.watch);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);

            mWarningView = itemView.findViewById(R.id.warning_view);
        }
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

}
