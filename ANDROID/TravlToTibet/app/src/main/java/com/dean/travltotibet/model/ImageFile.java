package com.dean.travltotibet.model;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.dialog.ImagePreviewDialogFragment;
import com.dean.travltotibet.dialog.TeamMakeTravelTypeDialog;
import com.dean.travltotibet.util.PicassoTools;
import com.dean.travltotibet.util.SystemUtil;
import com.dean.travltotibet.util.VolleyImageUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by DeanGuo on 12/29/15.
 */
public class ImageFile extends BmobObject {

    public final static String THUMBNAIL = "thumbnail";

    public final static int THUMBNAIL_WIDTH = 140;

    public final static int THUMBNAIL_HEIGHT = 180;

    public final static int IMAGE_WIDTH = 480;

    public final static int IMAGE_HEIGHT = 640;

    private BmobFile image1;

    private BmobFile image2;

    private BmobFile image3;

    private BmobFile thumbnail1;

    private BmobFile thumbnail2;

    private BmobFile thumbnail3;

    public BmobFile getImage1() {
        return image1;
    }

    public void setImage1(BmobFile image1) {
        this.image1 = image1;
    }

    public BmobFile getImage2() {
        return image2;
    }

    public void setImage2(BmobFile image2) {
        this.image2 = image2;
    }

    public BmobFile getImage3() {
        return image3;
    }

    public void setImage3(BmobFile image3) {
        this.image3 = image3;
    }

    public BmobFile getThumbnail1() {
        return thumbnail1;
    }

    public void setThumbnail1(BmobFile thumbnail1) {
        this.thumbnail1 = thumbnail1;
    }

    public BmobFile getThumbnail2() {
        return thumbnail2;
    }

    public void setThumbnail2(BmobFile thumbnail2) {
        this.thumbnail2 = thumbnail2;
    }

    public BmobFile getThumbnail3() {
        return thumbnail3;
    }

    public void setThumbnail3(BmobFile thumbnail3) {
        this.thumbnail3 = thumbnail3;
    }

    public static void setThumbnailImage(final Context mContext, BmobFile file, final ImageView imageView) {
        if (file == null) {
            imageView.setVisibility(View.INVISIBLE);
            return;
        }

        final String url = file.getFileUrl(mContext);
        imageView.setVisibility(View.VISIBLE);
        PicassoTools.getPicasso()
                .load(url)
                .placeholder(R.color.less_light_gray)
                .error(R.color.light_gray)
                .into(imageView);
    }

    public static void setImagePreview(final Context mContext, final ImageFile file, final View imageView, final int position) {
        if (imageView == null) {
            return;
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> urls = file.getImageUrls(mContext);
                ImagePreviewDialogFragment fragment = new ImagePreviewDialogFragment();
                fragment.setIsURL(true);
                Bundle data = new Bundle();
                data.putStringArrayList(AndroidImagePicker.KEY_PIC_PATH, urls);
                data.putInt(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, position);
                fragment.setArguments(data);
                fragment.show(((Activity) mContext).getFragmentManager(), TeamMakeTravelTypeDialog.class.getName());
            }
        });
    }

    public BmobFile getFile(int index) {
        BmobFile file = null;
        switch (index) {
            case 0:
                file = getImage1();
                break;
            case 1:
                file = getThumbnail1();
                break;

            case 2:
                file = getImage2();
                break;
            case 3:
                file = getThumbnail2();
                break;

            case 4:
                file = getImage3();
                break;
            case 5:
                file = getThumbnail3();
                break;

        }
        return file;
    }

    public BmobFile getThumbnailFile(int index) {
        BmobFile file = null;
        switch (index) {
            case 0:
                file = getThumbnail1();
                break;
            case 1:
                file = getThumbnail2();
                break;
            case 2:
                file = getThumbnail3();
                break;
        }
        return file;
    }

    public void setFiles(List<BmobFile> files) {
        for (int i=0; i< files.size();i++) {
            switch (i) {
                case 0:
                    setImage1(files.get(i));
                    break;
                case 1:
                    setThumbnail1(files.get(i));
                    break;

                case 2:
                    setImage2(files.get(i));
                    break;
                case 3:
                    setThumbnail2(files.get(i));
                    break;

                case 4:
                    setImage3(files.get(i));
                    break;
                case 5:
                    setThumbnail3(files.get(i));
                    break;
            }
        }
    }

    public ArrayList<String> getImageUrls(Context context) {
        ArrayList<String> urls = new ArrayList<>();
        if (getImage1() != null) {
            urls.add(getImage1().getFileUrl(context));
        }
        if (getImage2() != null) {
            urls.add(getImage2().getFileUrl(context));
        }
        if (getImage3() != null) {
            urls.add(getImage3().getFileUrl(context));
        }
        return urls;
    }

    public static String getImageName(int index) {
        String imageName;
        int count = index + 1;
        if (TTTApplication.getUserInfo() != null) {
            imageName = TTTApplication.getUserInfo().getUserName() + count + ".jpg";
        } else {
            imageName = "image" + count + ".jpg";
        }
        return getMyPicPath() + "/" + imageName;
    }

    public static String getThumbnailImageName(int index) {
        String imageName;
        int count = index + 1;
        if (TTTApplication.getUserInfo() != null) {
            imageName = TTTApplication.getUserInfo().getUserName() + THUMBNAIL + count + ".jpg";
        } else {
            imageName = "THUMBNAIL" + count + ".jpg";
        }
        return getMyPicPath() + "/" + imageName;
    }

    public void deleteAll(Context context) {
        if (getImage1() != null) {
            delFile(getImage1(), context);
        }
        if (getImage2() != null) {
            delFile(getImage2(), context);
        }
        if (getImage3() != null) {
            delFile(getImage3(), context);
        }
        if (getThumbnail1() != null) {
            delFile(getThumbnail1(), context);
        }
        if (getThumbnail2() != null) {
            delFile(getThumbnail2(), context);
        }
        if (getThumbnail3() != null) {
            delFile(getThumbnail3(), context);
        }
        this.delete(context);
    }

    public void delFile(BmobFile file, Context context) {
        BmobFile mFile = new BmobFile();
        mFile.setUrl(file.getUrl());
        mFile.delete(context, null);
    }

    public static String[] getCompressUrl(ArrayList<String> pickUrls) {
        // 创建temp文件
        List<String> imageFiles = new LinkedList<>();

        // 创建临时图片目录
        createTempFile();
        // 循环压缩图片,大图/缩略图
        for (int i=0; i < pickUrls.size(); i++) {
            String imageUrl = getImageName(i);
            String thumbnailUrl = getThumbnailImageName(i);
            VolleyImageUtils.compress(pickUrls.get(i), imageUrl, IMAGE_WIDTH, IMAGE_HEIGHT, 100);
            VolleyImageUtils.compress(pickUrls.get(i), thumbnailUrl, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, 100);
            imageFiles.add(imageUrl);
            imageFiles.add(thumbnailUrl);
        }

        String[] imgs = new String[imageFiles.size()];
        imageFiles.toArray(imgs);
        return imgs;
    }

    public static void createTempFile() {
        File file=new File(getMyPicPath());
        if(!file.exists())
            file.mkdir();
    }

    public static void delTempFile() {

        File file=new File(getMyPicPath());
        if(file.exists())
            SystemUtil.deleteFile(file);
    }

    public static String getMyPicPath() {
        File sd= Environment.getExternalStorageDirectory();
        return sd.getPath()+"/tanzi";
    }
}
