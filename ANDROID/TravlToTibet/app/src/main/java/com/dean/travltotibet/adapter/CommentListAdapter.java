package com.dean.travltotibet.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;
import com.dean.travltotibet.util.PicassoTools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class CommentListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Comment> mData = new ArrayList<>();

    public CommentListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
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

        final CommentViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
            holder = new CommentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CommentViewHolder) convertView.getTag();
        }

        Comment comment = mData.get(position);


        // profile Text
        holder.profileName.setText(comment.getUser_name());

        // profile Image
        if (!TextUtils.isEmpty(comment.getUser_icon())) {
            PicassoTools.getPicasso().load(comment.getUser_icon()).error(R.drawable.gray_profile).into(holder.profileImage);
        } else {
            holder.profileImage.setImageResource(R.drawable.gray_profile);
        }

        // 评论时间
        if (!TextUtils.isEmpty(comment.getCreatedAt())) {
            Date date = DateUtil.parse(comment.getCreatedAt(), Constants.YYYY_MM_DD_HH_MM_SS);
            String time = DateUtil.formatDate(date, Constants.YYYY_MM_DD);
            if (!TextUtils.isEmpty(time)) {
                holder.commentDate.setText(time);
            }
        }

        // 评论
        if (!TextUtils.isEmpty(comment.getComment())) {
            holder.commentText.setText(comment.getComment());
        }

        // 星
        holder.ratingBar.setRating(Float.parseFloat(comment.getRating()));

        return convertView;
    }

    public void setData(ArrayList<Comment> data) {
        this.mData = data;
        notifyDataSetChanged();
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

    public class CommentViewHolder {
        private ImageView profileImage;
        private TextView profileName;
        private TextView commentDate;
        private TextView commentText;
        private RatingBar ratingBar;

        public CommentViewHolder(View itemView) {
            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            profileName = (TextView) itemView.findViewById(R.id.profile_text);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_view);
        }
    }

}
