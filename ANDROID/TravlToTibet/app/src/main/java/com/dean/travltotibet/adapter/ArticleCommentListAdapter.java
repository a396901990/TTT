package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 2/19/16.
 */
public class ArticleCommentListAdapter extends BaseAdapter {

    RequestQueue mQueue;

    private ArrayList<Comment> mData = new ArrayList<>();

    public ArticleCommentListAdapter(Context mContext) {
        mQueue = Volley.newRequestQueue(mContext);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_comment_list_item, parent, false);
            holder = new CommentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CommentViewHolder) convertView.getTag();
        }

        final Comment comment = mData.get(position);


        // profile Text
        holder.profileName.setText(comment.getUser_name());

        // profile Image
        ImageRequest imageRequest = new ImageRequest(
                comment.getUser_icon(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        holder.profileImage.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                holder.profileImage.setImageResource(R.drawable.gray_profile);
            }
        });
        mQueue.add(imageRequest);

        // 评论时间
        Date date = DateUtil.parse(comment.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
        String time = DateUtil.formatDate(date, Constants.YYYY_MM_DD);
        holder.commentDate.setText(time);

        // 评论
        holder.commentText.setText(comment.getComment());

        // like
        if (comment.getLike() != 0) {
            holder.likeText.setText(comment.getLike() + "");
        } else {
            holder.likeText.setText("");
        }

        holder.likeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeAction(comment, parent.getContext(), holder);
            }
        });

        changeLikeStatus(comment, holder);

        return convertView;
    }

    private void changeLikeStatus(final Comment comment, final CommentViewHolder holder) {

        final SharedPreferences sharedPreferences = TTTApplication.getSharedPreferences();
        String objectId = sharedPreferences.getString(comment.getObjectId(), "");

        if (TextUtils.isEmpty(objectId)) {
            holder.likeIcon.setImageDrawable(TTTApplication.getMyResources().getDrawable(R.drawable.icon_like_gray));
            holder.likeContent.setClickable(true);
        } else {
            holder.likeIcon.setImageDrawable(TTTApplication.getMyResources().getDrawable(R.drawable.icon_like_red));
            holder.likeContent.setClickable(false);
        }
    }

    public void setData(ArrayList<Comment> data) {
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
                    holder.likeText.setText(comment.getLike()+"");
                    holder.likeIcon.setImageDrawable(TTTApplication.getMyResources().getDrawable(R.drawable.icon_like_red));
                    holder.likeContent.setClickable(false);
                    sharedPreferences.edit().putString(comment.getObjectId(), comment.getObjectId()).commit();
                }

                @Override
                public void onFailure(int i, String s) {
                }
            });
        }
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
        private ImageView likeIcon;

        private LinearLayout likeContent;

        public CommentViewHolder(View itemView) {
            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            profileName = (TextView) itemView.findViewById(R.id.profile_text);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            likeText = (TextView) itemView.findViewById(R.id.like_text);
            likeIcon = (ImageView) itemView.findViewById(R.id.like_icon);
            likeContent = (LinearLayout) itemView.findViewById(R.id.like_content);
        }
    }

}
