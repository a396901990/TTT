package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.activity.QAShowRequestDetailActivity;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.model.UserMessage;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.PicassoTools;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 5/11/16.
 */
public class MessageListAdapter extends BaseAdapter {


    private Context mContext;

    private ArrayList<UserMessage> mData;

    public MessageListAdapter(Context mContext) {
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

        final UserMessageViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message_list_item, parent, false);
            holder = new UserMessageViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (UserMessageViewHolder) convertView.getTag();
        }

        final UserMessage message = mData.get(position);

        UserInfo sendUser = message.getSendUser();
        if (sendUser != null) {
            // user name
            holder.mUserName.setText(sendUser.getUserName());
            if (UserInfo.MALE.equals(sendUser.getUserGender())) {
                holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
            } else {
                holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
            }

            // user icon
            if (!TextUtils.isEmpty(sendUser.getUserIcon())) {
                PicassoTools.getPicasso()
                        .load(sendUser.getUserIcon())
                        .resizeDimen(R.dimen.profile_icon_size, R.dimen.profile_icon_size)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .centerInside()
                        .error(R.drawable.gray_profile)
                        .config(Bitmap.Config.RGB_565)
                        .into(holder.mUserIcon);
            } else {
                holder.mUserIcon.setImageResource(R.drawable.gray_profile);
            }
        }

        // message content
        holder.mMessageContent.setText(message.getMessage());

        // message title
        holder.mMessageTitle.setText(message.getMessageTitle());

        // publish time
        String createTime = DateUtil.getTimeGap(message.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        holder.mPublishTime.setText(createTime);

        // 未读白色，读了灰色
        if (message.getStatus() != null && message.getStatus().equals(UserMessage.UNREAD_STATUS)) {
            holder.rippleLayout.setBackgroundColor(TTTApplication.getMyColor(R.color.white));
        } else {
            holder.rippleLayout.setBackgroundColor(TTTApplication.getMyColor(R.color.less_gray_background));
        }

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }

                if (message.getType().equals(UserMessage.TEAM_REQUEST_TYPE)) {
                    openTeamRequest(message.getTypeObjectId());
                } else if (message.getType().equals(UserMessage.QA_REQUEST_TYPE)) {
                    openQARequest(message.getTypeObjectId());
                }

                message.setStatus(UserMessage.READ_STATUS);
                message.update(mContext);
            }
        });

        return convertView;
    }

    private void openQARequest(String id) {
        // 获取TeamRequset对象
        BmobQuery<QARequest> query = new BmobQuery<>();
        query.getObject(mContext, id, new GetListener<QARequest>() {
            @Override
            public void onSuccess(QARequest qaRequest) {
                Intent intent = new Intent(mContext, QAShowRequestDetailActivity.class);
                intent.putExtra(IntentExtra.INTENT_QA_REQUEST, qaRequest);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, isPersonal);
                ((Activity) mContext).startActivityForResult(intent, BaseActivity.UPDATE_REQUEST);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    private void openTeamRequest(String id) {
        // 获取TeamRequset对象
        BmobQuery<TeamRequest> query = new BmobQuery<>();
        query.getObject(mContext, id, new GetListener<TeamRequest>() {
            @Override
            public void onSuccess(TeamRequest teamRequest) {
                Intent intent = new Intent(mContext, TeamShowRequestDetailActivity.class);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, teamRequest);
                intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, isPersonal);
                ((Activity)mContext).startActivityForResult(intent, BaseActivity.UPDATE_REQUEST);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    public void setData(ArrayList<UserMessage> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<UserMessage> addDatas) {
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

    public class UserMessageViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView mMessageContent;
        private TextView mMessageTitle;

        private TextView mUserName;
        private CircleImageView mUserIcon;

        private TextView mPublishTime;

        public UserMessageViewHolder(View itemView) {
            mMessageContent = (TextView) itemView.findViewById(R.id.message_text);
            mMessageTitle = (TextView) itemView.findViewById(R.id.message_title);

            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mUserIcon = (CircleImageView) itemView.findViewById(R.id.user_icon);

            mPublishTime = (TextView) itemView.findViewById(R.id.publish_time);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

}
