package com.dean.travltotibet.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.dialog.ImagePreviewDialogFragment;
import com.dean.travltotibet.dialog.TeamMakeTravelTypeDialog;
import com.dean.travltotibet.model.ImageFile;
import com.dean.travltotibet.util.PicassoTools;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DeanGuo on 3/29/16.
 */
public class ImagePickAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ADD_VIEW = 0;

    public static final int TYPE_SHOW_VIEW = 1;

    public static final int TYPE_SHOW_LIMIT = 3;

    private ArrayList<String> mData = new ArrayList<>();

    private Context mContext;

    private AddImageListener addImageListener;

    private ImageFile imageFile;

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

            if (mData == null && mData.size() == 0) {
                return;
            }

            // 显示网络图片
            if (imageFile != null) {
                // 设置缩略图
                BmobFile file = imageFile.getThumbnailFile(position);
                ImageFile.setThumbnailImage(mContext, file, ((ImagePickViewHolder) holder).urlPic);
                // 隐藏删除按钮
                ((ImagePickViewHolder) holder).delPic.setVisibility(View.GONE);
            }
            // 显示添加，从本地加载图片
            else {
                String url = mData.get(position);
                PicassoTools.getPicasso()
                        .load(new File(url))
                        .resizeDimen(R.dimen.image_pick_show_height, R.dimen.image_pick_show_height)
                        .error(R.color.light_gray)
                        .centerInside()
                        .into(((ImagePickViewHolder) holder).urlPic);

                // 长按删除
                ((ImagePickViewHolder) holder).delPic.setVisibility(View.VISIBLE);
                ((ImagePickViewHolder) holder).delPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteImage(position);
                    }
                });
            }

            ((ImagePickViewHolder) holder).urlPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImagePreviewDialogFragment fragment = new ImagePreviewDialogFragment();
                    fragment.setIsURL(imageFile != null);
                    Bundle data = new Bundle();
                    data.putStringArrayList(AndroidImagePicker.KEY_PIC_PATH, mData);
                    data.putInt(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, position);
                    fragment.setArguments(data);
                    fragment.show(((Activity) mContext).getFragmentManager(), TeamMakeTravelTypeDialog.class.getName());
                }
            });

        }
    }

    private void deleteImage(final int position) {
        new MaterialDialog.Builder((Activity) mContext)
                .title(mContext.getString(R.string.delete_image_title))
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

        if (imageFile != null) {
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

        // 网络图片，不显示添加
        if (imageFile != null) {
            count = mData == null ? 0 : mData.size();
        }
        // 本地图片，显示添加
        else {
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
        private ImageView delPic;

        public ImagePickViewHolder(View itemView) {
            super(itemView);
            urlPic = (ImageView) itemView.findViewById(R.id.pick_pic);
            delPic = (ImageView) itemView.findViewById(R.id.del_pic);
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

    public ImageFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(ImageFile imageFile) {
        this.imageFile = imageFile;
    }
}
