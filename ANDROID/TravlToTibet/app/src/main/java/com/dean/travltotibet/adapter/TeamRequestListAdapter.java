package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 3//16.
 */
public class TeamRequestListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<TeamRequest> mData;

    public TeamRequestListAdapter(Context mContext) {
        this.mContext = mContext;
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

        // 审核未通过和等待审核需要显示提示框
        if (TeamRequest.NO_PASS_STATUS.equals(request.getStatus())) {
            holder.mWarningView.setVisibility(View.VISIBLE);
            holder.mWarningText.setText(mContext.getString(R.string.warning_no_pass));
        } else if (TeamRequest.WAIT_STATUS.equals(request.getStatus())) {
            holder.mWarningView.setVisibility(View.VISIBLE);
            holder.mWarningText.setText(mContext.getString(R.string.warning_wait_pass));
        } else {
            holder.mWarningView.setVisibility(View.GONE);
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
            Picasso.with(mContext).load(request.getUserIcon()).error(R.drawable.gray_profile).into(holder.mUserIcon);
        } else {
            holder.mUserIcon.setImageResource(R.drawable.gray_profile);
        }

        holder.mWatch.setText(request.getWatch()+"");

        setUpImageContent(holder, position);

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, TeamShowRequestDetailActivity.class);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, request);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, isPersonal);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private void setUpImageContent(TeamRequestViewHolder holder, int position) {
        final TeamRequest request = mData.get(position);
        List<String> imgUrls = request.getImgUrls();
        if (imgUrls != null && imgUrls.size() != 0) {
            for (int i=0; i<imgUrls.size(); i++) {

                ImageView imageView = null;
                View imageContentView = null;

                switch (i) {
                    case 0:
                        imageView = (ImageView) holder.imageContent.findViewById(R.id.image_view_1);
                        imageContentView = holder.imageContent.findViewById(R.id.image_content_1);
                        break;
                    case 1:
                        imageView = (ImageView) holder.imageContent.findViewById(R.id.image_view_2);
                        imageContentView = holder.imageContent.findViewById(R.id.image_content_2);
                        break;
                    case 2:
                        imageView = (ImageView) holder.imageContent.findViewById(R.id.image_view_3);
                        imageContentView = holder.imageContent.findViewById(R.id.image_content_3);
                        break;
                }

                String url = imgUrls.get(i);
                if (!TextUtils.isEmpty(url)) {
                    holder.imageContent.setVisibility(View.VISIBLE);
                    imageContentView.setVisibility(View.VISIBLE);
                    Picasso.with(mContext)
                            .load(url)
                            .resizeDimen(R.dimen.image_pick_height, R.dimen.image_pick_height)
                            .placeholder(R.color.less_light_gray)
                            .centerInside()
                            .error(R.color.light_gray)
                            .into(imageView);
                }
            }
        } else {
            holder.imageContent.setVisibility(View.GONE);
        }
    }

    public void setData(ArrayList<TeamRequest> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<TeamRequest> addDatas) {
        mData.addAll(addDatas);
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
        private TextView mWarningText;

        private View imageContent;

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
            mWarningText = (TextView) itemView.findViewById(R.id.warning_text);

            imageContent = itemView.findViewById(R.id.image_content);
        }
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

}
