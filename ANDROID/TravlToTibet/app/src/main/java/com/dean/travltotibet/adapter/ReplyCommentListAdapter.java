package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.dialog.CommentPopupDialog;
import com.dean.travltotibet.model.Comment;
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
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 2/19/16.
 */
public class ReplyCommentListAdapter extends BaseAdapter {

    Context mContext;

    String mCommentType;

    private ArrayList<Comment> mData = new ArrayList<>();

    public ReplyCommentListAdapter(Context context) {
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

        final CommentViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_comment_list_item, parent, false);
            holder = new CommentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CommentViewHolder) convertView.getTag();
        }

        final Comment comment = mData.get(position);

        // profile Text old/new logic
        if (comment.getUser() != null && !TextUtils.isEmpty(comment.getUser().getUserName())) {
            holder.profileName.setText(comment.getUser().getUserName());
        } else {
            holder.profileName.setText(comment.getUser_name());
        }
        // profile Image old/new logic
        if (comment.getUser() != null && !TextUtils.isEmpty(comment.getUser().getUserIcon())) {
            PicassoTools.getPicasso()
                    .load(comment.getUser().getUserIcon())
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resizeDimen(R.dimen.image_pick_height, R.dimen.image_pick_height)
                    .centerInside()
                    .error(R.drawable.gray_profile)
                    .config(Bitmap.Config.RGB_565)
                    .into(holder.profileImage);
        } else {
            if (!TextUtils.isEmpty(comment.getUser_icon())) {
                PicassoTools.getPicasso()
                        .load(comment.getUser_icon())
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .resizeDimen(R.dimen.image_pick_height, R.dimen.image_pick_height)
                        .centerInside()
                        .error(R.drawable.gray_profile)
                        .config(Bitmap.Config.RGB_565)
                        .into(holder.profileImage);
            } else {
                holder.profileImage.setImageResource(R.drawable.gray_profile);
            }
        }

        // 评论时间
        String time = DateUtil.getTimeGap(comment.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        holder.commentDate.setText(time);

        // 评论
        setCommentView(holder, comment);

        // like
        holder.likeText.setText(String.valueOf(comment.getLike()));
        changeLikeStatus(comment, holder);
        holder.likeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeAction(comment, parent.getContext(), holder);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                DialogFragment dialogFragment = new CommentPopupDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentExtra.INTENT_COMMENT, comment);
                bundle.putString(IntentExtra.INTENT_COMMENT_TYPE, mCommentType);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(((Activity)mContext).getFragmentManager(), CommentPopupDialog.class.getName());
            }
        });

        return convertView;
    }

    private void setCommentView(final CommentViewHolder holder, final Comment comment) {
        if (comment.getCommentQuote() != null) {
            holder.replyContent.setVisibility(View.VISIBLE);

            holder.replyUserName.setText(comment.getCommentQuote().getUser_name());
            holder.replyComment.setText(comment.getCommentQuote().getComment());
            holder.commentText.setText(comment.getComment());
        } else {
            holder.replyContent.setVisibility(View.GONE);
            holder.commentText.setText(comment.getComment());
        }
    }

    private void changeLikeStatus(final Comment comment, final CommentViewHolder holder) {

        final SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        String objectId = sharedPreferences.getString(comment.getObjectId(), "");

        if (TextUtils.isEmpty(objectId)) {
            holder.likeContent.setBackgroundResource(R.drawable.border_light_gray);
        } else {
            holder.likeContent.setBackgroundResource(R.drawable.border_red);
        }
    }

    public void setData(ArrayList<Comment> data) {
        if (data == null) {
            return;
        }
        this.mData = data;
        notifyDataSetChanged();
    }

    private void likeAction(final Comment comment, final Context context, final CommentViewHolder holder) {

        final SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        String objectId = sharedPreferences.getString(comment.getObjectId(), "");

        if (TextUtils.isEmpty(objectId)) {
            comment.increment("like");
            comment.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {
                    comment.setLike(comment.getLike() + 1);
                    holder.likeText.setText(String.valueOf(comment.getLike()));
                    holder.likeContent.setBackgroundResource(R.drawable.border_red);
                    sharedPreferences.edit().putString(comment.getObjectId(), comment.getObjectId()).commit();
                }

                @Override
                public void onFailure(int i, String s) {
                }
            });
        }
    }

    public void addData(ArrayList<Comment> addDatas) {
        if (addDatas == null) {
            return;
        }
        mData.addAll(addDatas);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData = new ArrayList<Comment>();
        notifyDataSetChanged();
    }

    private class CommentViewHolder {
        private ImageView profileImage;
        private TextView profileName;
        private TextView commentDate;
        private TextView commentText;
        private TextView likeText;

        private LinearLayout likeContent;


        private TextView replyComment;
        private TextView replyUserName;
        private LinearLayout replyContent;

        public CommentViewHolder(View itemView) {
            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            profileName = (TextView) itemView.findViewById(R.id.profile_text);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            likeText = (TextView) itemView.findViewById(R.id.like_text);
            likeContent = (LinearLayout) itemView.findViewById(R.id.like_content);

            replyComment = (TextView) itemView.findViewById(R.id.reply_comment_text);
            replyUserName = (TextView) itemView.findViewById(R.id.reply_comment_username);
            replyContent = (LinearLayout) itemView.findViewById(R.id.reply_content);
        }
    }

    public String getCommentType() {
        return mCommentType;
    }

    public void setCommentType(String mCommentType) {
        this.mCommentType = mCommentType;
    }

}
