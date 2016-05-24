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
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.PicassoTools;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 3//16.
 */
public class QAListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<QARequest> mData;

    public QAListAdapter(Context mContext) {
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

        final QAViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.q_a_list_item, parent, false);
            holder = new QAViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (QAViewHolder) convertView.getTag();
        }

        final QARequest request = mData.get(position);

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

        // title
        holder.mContentTitle.setText(request.getTitle());

        // user name
        holder.mUserName.setText(request.getUserName());
        if (UserInfo.MALE.equals(request.getUserGender())) {
            holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
        } else {
            holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
        }

        // user icon
        if (!TextUtils.isEmpty(request.getUserIcon())) {
            PicassoTools.getPicasso()
                    .load(request.getUserIcon())
                    .resizeDimen(R.dimen.profile_icon_size, R.dimen.profile_icon_size)
                    .centerInside()
                    .error(R.drawable.gray_profile)
                    .config(Bitmap.Config.RGB_565)
                    .into(holder.mUserIcon);
        } else {
            holder.mUserIcon.setImageResource(R.drawable.gray_profile);
        }

        holder.mWatch.setText(request.getWatch().intValue() + "");

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, QAShowRequestDetailActivity.class);
                intent.putExtra(IntentExtra.INTENT_QA_REQUEST, request);
                intent.putExtra(IntentExtra.INTENT_IS_PERSONAL, isPersonal);
                ((Activity)mContext).startActivityForResult(intent, BaseActivity.UPDATE_REQUEST);
            }
        });

        return convertView;
    }

    public void setData(ArrayList<QARequest> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<QARequest> addDatas) {
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

    public class QAViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView mContentTitle;

        private TextView mUserName;
        private CircleImageView mUserIcon;

        private TextView mWatch;
        private View mWarningView;
        private TextView mWarningText;

        public QAViewHolder(View itemView) {
            mContentTitle = (TextView) itemView.findViewById(R.id.message_title);

            mUserName = (TextView) itemView.findViewById(R.id.user_name);
//            mUserGender = itemView.findViewById(R.id.user_gender);
            mUserIcon = (CircleImageView) itemView.findViewById(R.id.user_icon);

//            mPublishTime = (TextView) itemView.findViewById(R.id.publish_time);
            mWatch = (TextView) itemView.findViewById(R.id.watch);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);

            mWarningView = itemView.findViewById(R.id.warning_view);
            mWarningText = (TextView) itemView.findViewById(R.id.warning_text);
        }
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

}
