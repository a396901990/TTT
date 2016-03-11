package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ArticleCommentActivity;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 3//16.
 */
public class ArticleListAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Article> mData;

    public ArticleListAdapter(Context mContext) {
        this.mContext = mContext;
    }

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

        final ArticleViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
            holder = new ArticleViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ArticleViewHolder) convertView.getTag();
        }

        final Article article = mData.get(position);

        holder.mTitle.setText(article.getTitle());

        holder.mWatch.setText(article.getWatch() + "");
        holder.mLike.setText(article.getLike() + "");

        // 背景
        Picasso.with(mContext).load(article.getTitleImage()).error(R.color.light_gray).into(holder.mBackgroundView);

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                // 跳转到RouteActivity
                Intent intent = new Intent(mContext, ArticleCommentActivity.class);
                intent.putExtra(IntentExtra.INTENT_ARTICLE, article);
                intent.putExtra(IntentExtra.INTENT_ARTICLE_FROM, ArticleCommentActivity.FROM_HOME);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    public void setData(ArrayList<Article> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<Article> addDatas) {
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

    public static class ArticleViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView mTitle;
        private TextView mWatch;
        private TextView mLike;
        private ImageView mBackgroundView;

        public ArticleViewHolder(View itemView) {
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mWatch = (TextView) itemView.findViewById(R.id.watch);
            mLike = (TextView) itemView.findViewById(R.id.like);
            mBackgroundView = (ImageView) itemView.findViewById(R.id.background_image);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

}
