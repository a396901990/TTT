package com.dean.travltotibet.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.dean.travltotibet.R;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class CommentListAdapter extends BaseAdapter {

    private Context mContext;

    RequestQueue mQueue;

    private ArrayList<Comment> mData = new ArrayList<>();

    public CommentListAdapter(Context mContext) {
        this.mContext = mContext;
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

        // 星
        holder.ratingBar.setRating(Float.parseFloat(comment.getRating()));

        return convertView;
    }

    public void setData(ArrayList<Comment> data) {
        this.mData = data;
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
