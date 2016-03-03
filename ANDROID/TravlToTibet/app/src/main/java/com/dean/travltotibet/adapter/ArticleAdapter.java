package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ArticleCommentActivity;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 2/17/16.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context mContext;

    private ArrayList<Article> mData;

    private RequestQueue mQueue;

    private ImageLoader imageLoader;

    private Activity mActivity;

    public ArticleAdapter(Context mContext) {
        this.mContext = mContext;
        mActivity = (Activity) mContext;

        mQueue = Volley.newRequestQueue(mContext);
        imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, int position) {

        final Article article = mData.get(position);

        holder.mTitle.setText(article.getTitle());

        holder.mWatch.setText(article.getWatch()+"");
        holder.mLike.setText(article.getLike()+"");

        holder.mBackgroundView.setImageUrl(article.getTitleImage(), imageLoader);

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

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<Article> data) {
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


    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        private MaterialRippleLayout rippleLayout;
        private TextView mTitle;
        private TextView mWatch;
        private TextView mLike;
        private NetworkImageView mBackgroundView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mWatch = (TextView) itemView.findViewById(R.id.watch);
            mLike = (TextView) itemView.findViewById(R.id.like);
            mBackgroundView = (NetworkImageView) itemView.findViewById(R.id.background_image);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

}
