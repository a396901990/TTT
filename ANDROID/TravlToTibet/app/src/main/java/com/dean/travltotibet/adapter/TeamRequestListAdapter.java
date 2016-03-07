package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
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
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;
import java.util.Date;

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

        // title
        holder.mTitle.setText(request.getTitle());
        // destination
        holder.mDestinationName.setText(String.format(Constants.TEAM_REQUEST_TITLE, request.getDestination(), request.getType()));
        // date
        holder.mDate.setText(request.getDate());
        // publish time
        String createTime = DateUtil.getTimeGap(request.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        holder.mTime.setText(createTime);
        // user name
        holder.mUser.setText(request.getUserName());
        if (UserInfo.MALE.equals(request.getUserGender())) {
            holder.mUser.setTextColor(TTTApplication.getMyColor(R.color.colorPrimaryDark));
        } else {
            holder.mUser.setTextColor(TTTApplication.getMyColor(R.color.light_red));
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
        private TextView mTitle;
        private TextView mDestinationName;
        private TextView mUser;
        private CircleImageView mUserIcon;
        private TextView mTime;
        private TextView mDate;
        private TextView mWatch;
        private View mWarningView;

        public TeamRequestViewHolder(View itemView) {
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDestinationName = (TextView) itemView.findViewById(R.id.destination_name);
            mDate = (TextView) itemView.findViewById(R.id.plan_date);
            mUser = (TextView) itemView.findViewById(R.id.user_time);
            mUserIcon = (CircleImageView) itemView.findViewById(R.id.user_icon);
            mTime = (TextView) itemView.findViewById(R.id.publish_time);
            mWatch = (TextView) itemView.findViewById(R.id.watch);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);

            mWarningView = itemView.findViewById(R.id.warning_view);
        }
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

}
