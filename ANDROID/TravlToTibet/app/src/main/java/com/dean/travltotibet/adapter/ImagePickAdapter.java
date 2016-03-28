package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AroundHotelActivity;
import com.dean.travltotibet.activity.AroundScenicActivity;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.model.Article;
import com.dean.travltotibet.model.GalleryInfo;
import com.dean.travltotibet.ui.MaterialRippleLayout;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 12/17/15.
 */
public class ImagePickAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ADD_VIEW = 0;

    public static final int TYPE_SHOW_VIEW = 1;

    public static final int TYPE_SHOW_LIMIT = 3;

    private ArrayList<String> mData = new ArrayList<>();

    private Context mContext;

    private AddImageListener addImageListener;

    private boolean isOnlyShow = false;

    public ImagePickAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public static interface AddImageListener {
        public void onAddImage();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_add_pick_list_item_view, parent, false);
            return new ImageAddViewHolder(view);
        } else if (viewType == TYPE_SHOW_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_show_pick_list_item_view, parent, false);
            return new ImagePickViewHolder(view);
        }

        return this.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // add button
        if (TYPE_ADD_VIEW == getItemViewType(position)) {
            ((ImageAddViewHolder) holder).addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (addImageListener != null) {
                        addImageListener.onAddImage();
                    }
                }
            });
        }

        // show image
        if (TYPE_SHOW_VIEW == getItemViewType(position)) {

            String url = null;
            if (mData != null && mData.size() != 0) {
                url = mData.get(position);
            }

            // 图片url
            if (!TextUtils.isEmpty(url)) {
                if (isOnlyShow) {
                    Picasso.with(mContext)
                            .load(url)
                            .resizeDimen(R.dimen.image_pick_height, R.dimen.image_pick_height)
                            .error(R.color.light_gray)
                            .centerInside()
                            .into(((ImagePickViewHolder) holder).urlPic);
                } else {
                    Picasso.with(mContext)
                            .load(new File(url))
                            .resizeDimen(R.dimen.image_pick_height, R.dimen.image_pick_height)
                            .error(R.color.light_gray)
                            .centerInside()
                            .into(((ImagePickViewHolder) holder).urlPic);

                    ((ImagePickViewHolder) holder).urlPic.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            deleteImage(position);
                            return false;
                        }
                    });
                }
            }

        }
    }

    private void deleteImage(final int position) {
        new MaterialDialog.Builder((Activity) mContext)
                .title(mContext.getString(R.string.delete_image_title))
                .content(mContext.getString(R.string.delete_image_msg))
                .positiveText(mContext.getString(R.string.ok_btn))
                .negativeText(mContext.getString(R.string.cancel_btn))
                .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        mData.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mData.size());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    @Override
    public int getItemViewType(int position) {
        int count = getItemCount();
        int size = mData != null ? mData.size() : 0;

        int type = TYPE_ADD_VIEW;

        if (isOnlyShow) {
            type = TYPE_SHOW_VIEW;
        } else {
            // 没选全，则有添加按钮
            if (size < count) {
                if (position < size) {
                    type = TYPE_SHOW_VIEW;
                } else {
                    type = TYPE_ADD_VIEW;
                }
            }
            // 都选了
            else if (size == count) {
                type = TYPE_SHOW_VIEW;
            }
        }
        return type;
    }

    public void addData(ArrayList<String> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        for (String d : data) {
            if (!TextUtils.isEmpty(d)) {
                mData.add(d);
            }
        }
        notifyDataSetChanged();
//        this.notifyItemRangeInserted(0, mData.size());
    }

    public void setData(ArrayList<String> data) {
        this.mData = data;
        this.notifyItemRangeInserted(0, mData.size() - 1);
    }

    public void clearData() {
        if (mData == null) {
            return;
        } else {
            mData = new ArrayList<>();
        }
        this.notifyItemRangeRemoved(0, mData.size());
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (isOnlyShow) {
            count = mData == null ? 0 : mData.size();
        } else {
            if (mData == null) {
                count = 1;
            } else if (mData.size() < TYPE_SHOW_LIMIT) {
                count = mData.size() + 1;
            } else if (mData.size() == TYPE_SHOW_LIMIT) {
                count = TYPE_SHOW_LIMIT;
            }
        }
        return count;
    }

    public static class ImagePickViewHolder extends RecyclerView.ViewHolder {

        private ImageView urlPic;

        public ImagePickViewHolder(View itemView) {
            super(itemView);
            urlPic = (ImageView) itemView.findViewById(R.id.pick_pic);
        }
    }

    public static class ImageAddViewHolder extends RecyclerView.ViewHolder {

        private View addView;

        public ImageAddViewHolder(View itemView) {
            super(itemView);
            addView = itemView.findViewById(R.id.add_pick_item);
        }
    }

    public AddImageListener getAddImageListener() {
        return addImageListener;
    }

    public void setAddImageListener(AddImageListener addImageListener) {
        this.addImageListener = addImageListener;
    }

    public ArrayList<String> getData() {
        return mData;
    }

    public boolean isOnlyShow() {
        return isOnlyShow;
    }

    public void setIsOnlyShow(boolean isOnlyShow) {
        this.isOnlyShow = isOnlyShow;
    }
}
