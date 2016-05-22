package com.dean.travltotibet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ArticleActivity;
import com.dean.travltotibet.activity.BaseActivity;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.PicassoTools;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 2/17/16.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context mContext;

    private ArrayList<Article> mData;

    public ArticleAdapter(Context mContext) {
        this.mContext = mContext;
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

        holder.mWatch.setText(article.getWatch().intValue() + "");
        holder.mLike.setText(article.getLike().intValue() + "");

        if (!TextUtils.isEmpty(article.getTitleImage())) {
            PicassoTools.getPicasso()
                    .load(article.getTitleImage())
                    .resizeDimen(R.dimen.home_recent_item_height, R.dimen.home_recent_item_height)
                    .error(R.color.light_gray)
                    .centerInside()
                    .into(holder.mBackgroundView);
        }

        holder.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ScreenUtil.isFastClick()) {
                    return;
                }
                Intent intent = new Intent(mContext, ArticleActivity.class);
                intent.putExtra(IntentExtra.INTENT_ARTICLE, article);
                intent.putExtra(IntentExtra.INTENT_LAUNCH_FROM, BaseActivity.FROM_HOME);
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

    public void addData(ArrayList<Article> addDatas) {
        for (Article article : addDatas) {
            mData.add(article);
        }
//        this.notifyItemRangeInserted(0, mData.size() - 1);
    }

    public void clearData() {
        if (mData == null) {
            return;
        }
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
        private ImageView mBackgroundView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mWatch = (TextView) itemView.findViewById(R.id.watch);
            mLike = (TextView) itemView.findViewById(R.id.like);
            mBackgroundView = (ImageView) itemView.findViewById(R.id.background_image);
            rippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_view);
        }
    }

}
