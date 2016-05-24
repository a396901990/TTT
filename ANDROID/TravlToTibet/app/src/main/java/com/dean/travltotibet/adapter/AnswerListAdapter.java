package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AnswerDetailActivity;
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.model.AnswerInfo;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.PicassoTools;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.MemoryPolicy;

import java.util.ArrayList;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 2/19/16.
 */
public class AnswerListAdapter extends BaseAdapter {

    Context mContext;

    private ArrayList<AnswerInfo> mData = new ArrayList<>();

    public AnswerListAdapter(Context context) {
        mContext = context;
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
    public View getView(int position, View convertView, final ViewGroup parent) {

        final AnswerViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_list_item, parent, false);
            holder = new AnswerViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AnswerViewHolder) convertView.getTag();
        }

        final AnswerInfo answerInfo = mData.get(position);

        // profile Name
        holder.profileName.setText(answerInfo.getUserName());
        if (UserInfo.MALE.equals(answerInfo.getUserGender())) {
            holder.profileName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
        } else {
            holder.profileName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
        }
//        if (answerInfo.getUser() != null && !TextUtils.isEmpty(answerInfo.getUser().getUserName())) {
//            holder.profileName.setText(answerInfo.getUser().getUserName());
//        }

        // profile Image
//        if (answerInfo.getUser() != null && !TextUtils.isEmpty(answerInfo.getUser().getUserIcon())) {
            PicassoTools.getPicasso()
                    .load(answerInfo.getUserIcon())
                    .resizeDimen(R.dimen.profile_icon_size, R.dimen.profile_icon_size)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .centerInside()
                    .error(R.drawable.gray_profile)
                    .config(Bitmap.Config.RGB_565)
                    .into(holder.profileImage);
//        }

        // 评论时间
        String time = DateUtil.getTimeGap(answerInfo.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        holder.commentDate.setText(time);

        // 内容
        holder.commentText.setText(answerInfo.getContent());

        // like
        holder.likeText.setText(String.valueOf(answerInfo.getLike().intValue()));
        changeLikeStatus(answerInfo, holder);
        holder.likeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeAction(answerInfo, parent.getContext(), holder);
            }
        });

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, AnswerDetailActivity.class);
                intent.putExtra(IntentExtra.INTENT_ANSWER, answerInfo);
                intent.putExtra(IntentExtra.INTENT_IS_PERSONAL, false);
                ((Activity)mContext).startActivityForResult(intent, BaseActivity.UPDATE_REQUEST);
            }
        });
        return convertView;
    }

    public void setData(ArrayList<AnswerInfo> data) {
        if (data == null) {
            return;
        }
        this.mData = data;
        notifyDataSetChanged();
    }

    private void changeLikeStatus(final AnswerInfo answerInfo, final AnswerViewHolder holder) {

        final SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        String objectId = sharedPreferences.getString(answerInfo.getObjectId(), "");

        if (TextUtils.isEmpty(objectId)) {
            holder.likeContent.setBackgroundResource(R.drawable.border_light_gray);
        } else {
            holder.likeContent.setBackgroundResource(R.drawable.border_red);
        }
    }

    private void likeAction(final AnswerInfo answerInfo, final Context context, final AnswerViewHolder holder) {

        final SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        final String objectId = sharedPreferences.getString(answerInfo.getObjectId(), "");

        if (TextUtils.isEmpty(objectId)) {
            answerInfo.increment("like");
            answerInfo.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {

                    answerInfo.setLike(answerInfo.getLike().intValue() + 1);
                    holder.likeText.setText(String.valueOf(answerInfo.getLike().intValue()));
                    holder.likeContent.setBackgroundResource(R.drawable.border_red);
                    sharedPreferences.edit().putString(answerInfo.getObjectId(), answerInfo.getObjectId()).commit();
                }

                @Override
                public void onFailure(int i, String s) {
                }
            });
        }
    }

    public void addData(ArrayList<AnswerInfo> addDatas) {
        if (addDatas == null) {
            return;
        }
        mData.addAll(addDatas);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData = new ArrayList<AnswerInfo>();
        notifyDataSetChanged();
    }

    private class AnswerViewHolder {
        private ImageView profileImage;
        private TextView profileName;
        private TextView commentDate;
        private TextView commentText;
        private TextView likeText;

        private View likeContent;
        private MaterialRippleLayout rippleLayout;

        public AnswerViewHolder(View itemView) {
            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            profileName = (TextView) itemView.findViewById(R.id.profile_text);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            likeText = (TextView) itemView.findViewById(R.id.like_text);
            likeContent = itemView.findViewById(R.id.like_content);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

}
