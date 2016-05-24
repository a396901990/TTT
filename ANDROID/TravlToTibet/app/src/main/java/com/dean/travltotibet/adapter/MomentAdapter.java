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

import com.a.a.V;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.activity.MomentDetailActivity;
import com.dean.travltotibet.model.ImageFile;
import com.dean.travltotibet.model.Moment;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.ui.like.LikeButton;
import com.dean.travltotibet.ui.like.OnLikeListener;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.PicassoTools;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;

import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeanGuo on 5/24/16.
 */
public class MomentAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Moment> mData;

    public MomentAdapter(Context mContext) {
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

        final MomentViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.moment_list_item, parent, false);
            holder = new MomentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MomentViewHolder) convertView.getTag();
        }

        final Moment moment = mData.get(position);

        // 审核未通过和等待审核需要显示提示框
        if (TeamRequest.NO_PASS_STATUS.equals(moment.getStatus())) {
            holder.mWarningView.setVisibility(View.VISIBLE);
            holder.mWarningText.setText(mContext.getString(R.string.warning_no_pass));
        } else if (TeamRequest.WAIT_STATUS.equals(moment.getStatus())) {
            holder.mWarningView.setVisibility(View.VISIBLE);
            holder.mWarningText.setText(mContext.getString(R.string.warning_wait_pass));
        } else {
            holder.mWarningView.setVisibility(View.GONE);
        }

        // content
        if (!TextUtils.isEmpty(moment.getContent())) {
            holder.mContentText.setVisibility(View.VISIBLE);
            holder.mContentText.setText(moment.getContent());
        } else {
            holder.mContentText.setVisibility(View.GONE);
        }

        // user name
        holder.mUserName.setText(moment.getUserName());
        if (UserInfo.MALE.equals(moment.getUserGender())) {
            holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.colorPrimary));
        } else {
            holder.mUserName.setTextColor(TTTApplication.getMyColor(R.color.light_red));
        }

        // publish time
        String createTime = DateUtil.getTimeGap(moment.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        holder.mPublishTime.setText(createTime);

        // user icon
        if (!TextUtils.isEmpty(moment.getUserIcon())) {
            PicassoTools.getPicasso()
                    .load(moment.getUserIcon())
                    .resizeDimen(R.dimen.profile_icon_size, R.dimen.profile_icon_size)
                    .centerInside()
                    .error(R.drawable.gray_profile)
                    .config(Bitmap.Config.RGB_565)
                    .into(holder.mUserIcon);
        } else {
            holder.mUserIcon.setImageResource(R.drawable.gray_profile);
        }

        // share image
        holder.mShareImage.setVisibility(View.GONE);
        ImageFile imageFile = moment.getImageFile();
        if (imageFile != null) {
            holder.mShareImage.setVisibility(View.VISIBLE);
            PicassoTools.getPicasso()
                    .load(imageFile.getImage1().getFileUrl(mContext))
                    .placeholder(R.color.less_light_gray)
                    .error(R.color.light_gray)
                    .into(holder.mShareImage);
        }

        if (moment.getWatch() != null) {
            holder.mWatch.setText(moment.getWatch().intValue() + "");
        }

        // location
        if (!TextUtils.isEmpty(moment.getLocation())) {
            holder.mLocationText.setVisibility(View.VISIBLE);
            holder.mLocationText.setText(moment.getLocation());
        } else {
            holder.mLocationText.setVisibility(View.GONE);
        }

        // comment btn
        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, MomentDetailActivity.class);
                intent.putExtra(IntentExtra.INTENT_MOMENT, moment);
                intent.putExtra(IntentExtra.INTENT_IS_PERSONAL, isPersonal);
                ((Activity)mContext).startActivityForResult(intent, BaseActivity.UPDATE_REQUEST);
            }
        });

        // like
        changeLikeStatus(moment, holder); // 更新收藏按钮状态
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeAction(moment, mContext, holder);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                unlikeAction(moment, mContext, holder);
            }
        });

        // 点击跳转
        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, MomentDetailActivity.class);
                intent.putExtra(IntentExtra.INTENT_MOMENT, moment);
                intent.putExtra(IntentExtra.INTENT_IS_PERSONAL, isPersonal);
                ((Activity)mContext).startActivityForResult(intent, BaseActivity.UPDATE_REQUEST);
            }
        });

        // 个人观看不需要显示评论和点赞
        if (isPersonal) {
            holder.bottomView.setVisibility(View.GONE);
        } else {
            holder.bottomView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private void changeLikeStatus(final Moment moment, final MomentViewHolder holder) {

        final SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        String objectId = sharedPreferences.getString(moment.getObjectId(), "");

        if (TextUtils.isEmpty(objectId)) {
            holder.likeButton.setLiked(false);
        } else {
            holder.likeButton.setLiked(true);
        }
    }

    private void updateWatch(Context mContext, final Moment moment, final MomentViewHolder holder, final int number) {
        try {
            final int watch = moment.getWatch().intValue();
            moment.increment("watch", number);
            moment.update(mContext, new UpdateListener() {
                @Override
                public void onSuccess() {
                    moment.setWatch(watch + number);
                    if (holder != null) {
                        holder.mWatch.setText(String.valueOf(watch + number));
                    }
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } catch (Exception e) {
            // finish();
        }
    }

    private void likeAction(final Moment moment, final Context context, final MomentViewHolder holder) {

        final int watch = moment.getWatch().intValue();

        moment.increment("watch");
        moment.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });

        moment.setWatch(watch + 1);
        holder.mWatch.setText(String.valueOf(moment.getWatch()));

        SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        sharedPreferences.edit().putString(moment.getObjectId(), moment.getObjectId()).commit();
    }

    private void unlikeAction(final Moment moment, final Context context, final MomentViewHolder holder) {
        final int watch = moment.getWatch().intValue();

        moment.increment("watch", -1);
        moment.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });

        moment.setWatch(watch - 1);
        holder.mWatch.setText(String.valueOf(moment.getWatch()));

        SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        sharedPreferences.edit().remove(moment.getObjectId()).commit();
    }

    public void setData(ArrayList<Moment> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<Moment> addDatas) {
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

    public class MomentViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView mContentText;
        private TextView mLocationText;
        private TextView mPublishTime;
        private ImageView mShareImage;

        private TextView mUserName;
        private CircleImageView mUserIcon;

        private TextView mWatch;
        private View mWarningView;
        private TextView mWarningText;

        private LikeButton likeButton;
        private View commentBtn;
        private View bottomView;

        public MomentViewHolder(View itemView) {
            mContentText = (TextView) itemView.findViewById(R.id.content_text);
            mLocationText = (TextView) itemView.findViewById(R.id.location_text);
            mPublishTime = (TextView) itemView.findViewById(R.id.publish_time);
            mShareImage = (ImageView) itemView.findViewById(R.id.share_image);

            likeButton = (LikeButton) itemView.findViewById(R.id.like_button);
            commentBtn = itemView.findViewById(R.id.comment_btn);

            mUserName = (TextView) itemView.findViewById(R.id.user_name);
//            mUserGender = itemView.findViewById(R.id.user_gender);
            mUserIcon = (CircleImageView) itemView.findViewById(R.id.user_icon);

            mWatch = (TextView) itemView.findViewById(R.id.watch);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);

            mWarningView = itemView.findViewById(R.id.warning_view);
            mWarningText = (TextView) itemView.findViewById(R.id.warning_text);

            bottomView = itemView.findViewById(R.id.bottom_content_view);
        }
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

}
