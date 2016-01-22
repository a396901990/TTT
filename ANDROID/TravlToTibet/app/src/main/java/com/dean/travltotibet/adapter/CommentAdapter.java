package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dean.greendao.RecentRoute;
import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.model.Comment;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 1/22/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.RecentViewHolder> {

    private Context mContext;

    private ArrayList<Comment> mData;

    public CommentAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentViewHolder holder, int position) {

        final Comment comment = mData.get(position);
        // profile Image
        //holder.profileImage.setImageDrawable(null);

        // profile Text
        holder.profileName.setText(comment.getUser_name());

        // 评论时间
        holder.commentDate.setText(comment.getComment_time());

        // 评论
        holder.commentText.setText(comment.getComment());

        // 星
        holder.ratingBar.setRating(Float.parseFloat(comment.getRating()));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<Comment> data) {
        this.mData = data;
        this.notifyItemRangeInserted(0, mData.size() - 1);
    }

    public void clearData() {
        int size = this.mData.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mData.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }


    public static class RecentViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;
        private TextView profileName;
        private TextView commentDate;
        private TextView commentText;
        private RatingBar ratingBar;

        public RecentViewHolder(View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            profileName = (TextView) itemView.findViewById(R.id.profile_text);
            commentDate = (TextView) itemView.findViewById(R.id.comment_date);
            commentText = (TextView) itemView.findViewById(R.id.comment_text);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_view);
        }
    }
}
