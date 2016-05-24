package com.dean.travltotibet.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a.a.V;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.ImagePickerActivity;
import com.dean.travltotibet.activity.MomentCreateActivity;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.model.ImageFile;
import com.dean.travltotibet.model.Moment;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoadingManager;
import com.dean.travltotibet.util.PicassoTools;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.helper.PermissionListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by DeanGuo on 5/23/16.
 */
public class MomentCreateFragment extends BaseRefreshFragment implements AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener {

    private View root;

    private MomentCreateActivity mActivity;

    private final static int TEXT_MAX_LIMIT = 140;

    private final static int TEXT_MIN_LIMIT = 0;

    private Moment moment;

    private EditText contentEdit;

    private LoadingManager loadingManager;

    private ArrayList<String> imagePaths = new ArrayList<>();

    private View addBtn, delBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.moment_create_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (MomentCreateActivity) this.getActivity();
        moment = new Moment();
        loadingManager = new LoadingManager(getActivity());

        initContentContent();
        updateImageContent();
//        actionPickImage();
    }

    @Override
    public void onImagePickComplete(List<ImageItem> items) {
        imagePaths.clear();

        List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
        for (ImageItem i : imageList) {
            imagePaths.add(i.path);
        }

        updateImageContent();
    }

    @Override
    public void onPictureTakeComplete(String picturePath) {
        imagePaths.clear();
        imagePaths.add(picturePath);

        updateImageContent();
    }

    public void updateImageContent() {
        ImageView shareImage = (ImageView) root.findViewById(R.id.share_image);
        if (imagePaths != null && imagePaths.size() > 0) {
            PicassoTools.getPicasso()
                    .load(new File(imagePaths.get(0)))
                    .error(R.color.light_gray)
                    .into(shareImage);

            addBtn.setVisibility(View.GONE);
            shareImage.setVisibility(View.VISIBLE);
            delBtn.setVisibility(View.VISIBLE);
        } else {
            shareImage.setVisibility(View.GONE);
            addBtn.setVisibility(View.VISIBLE);
            delBtn.setVisibility(View.GONE);
        }
    }

    public void goToPicker() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(IntentExtra.INTENT_IMAGE_SELECTED, 0);
        intent.putExtra(IntentExtra.INTENT_IMAGE_LIMIT, 1);
        startActivity(intent);
    }

    // 内容
    private void initContentContent() {
        contentEdit = (EditText) root.findViewById(R.id.content_edit_text);
        contentEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(TEXT_MAX_LIMIT)});

        addBtn = root.findViewById(R.id.add_btn);
        delBtn = root.findViewById(R.id.del_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionPickImage();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePaths.clear();
                updateImageContent();
            }
        });
    }

    public void actionPickImage() {
        // 6.0 检查存储运行权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mActivity.getPermissionManager()
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .setPermissionsListener(new PermissionListener() {
                        @Override
                        public void onGranted() {
                            goToPicker();
                        }

                        @Override
                        public void onDenied() {

                        }

                        @Override
                        public void onShowRationale(String[] strings) {
                            Snackbar.make(root, "需要内存卡读取权限、摄像头权限来访问照片", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //必须调用该`setIsPositive(true)`方法
                                            mActivity.getPermissionManager().setIsPositive(true);
                                            mActivity.getPermissionManager().request();
                                        }
                                    }).show();
                        }
                    }).request();
        } else {
            goToPicker();
        }
    }

    // 提交请求
    public void commitRequest() {
        // set content value
        String contentString = contentEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(contentString) && contentString.length() >= TEXT_MIN_LIMIT) {
            moment.setContent(contentString);
        }
        toDo(PREPARE_LOADING, 0);
    }

    @Override
    public void prepareLoading() {
        super.prepareLoading();

        // show loading searchDialog
        loadingManager.showLoading();

        // save上传图片后操作
        if (imagePaths.size() == 0) {
            toDo(ON_LOADING, 0);
        } else {
            uploadImage();
        }
    }

    @Override
    public void onLoading() {
        super.onLoading();
        saveAction();
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();

        loadingManager.loadingSuccess(new LoadingManager.LoadingSuccessCallBack() {
            @Override
            public void afterLoadingSuccess() {
                getActivity().setResult(getActivity().RESULT_OK);
                getActivity().finish();
            }
        });
    }

    @Override
    public void LoadingError() {
        super.LoadingError();

        loadingManager.loadingFailed(new LoadingManager.LoadingFailedCallBack() {
            @Override
            public void afterLoadingFailed() {
            }
        });
    }

    public void saveAction() {

        if (TTTApplication.getUserInfo() == null) {
            toDo(LOADING_ERROR, 0);
        }

        if (TTTApplication.getUserInfo() != null) {
            UserInfo userInfo = TTTApplication.getUserInfo();
            moment.setUser(userInfo);
            moment.setUserName(userInfo.getUserName());
            moment.setUserGender(userInfo.getUserGender());
            moment.setUserIcon(userInfo.getUserIcon());
        }
        moment.setStatus(Moment.PASS_STATUS);
        moment.setWatch(0);
        moment.setLike(0);
        moment.setComment(0);
        moment.setLocation("中国 西藏 布达拉宫");
        moment.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                if (mActivity == null) {
                    return;
                }

                // 存入user中
                UserInfo userInfo = TTTApplication.getUserInfo();
                BmobRelation momentRelation = new BmobRelation();
                momentRelation.add(moment);
                userInfo.setMoment(momentRelation);
                userInfo.update(getActivity(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        toDo(LOADING_SUCCESS, 0);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toDo(LOADING_SUCCESS, 0);
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                if (mActivity == null) {
                    return;
                }
                toDo(LOADING_ERROR, 0);
            }
        });
    }

    /**
     * 权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            goToPicker();
        } else {
        }
    }

    /**
     * 上传图片
     */
    private void uploadImage() {

        // 获取压缩后的图片，原图/缩略图
        String[] imgs = ImageFile.getCompressUrl(imagePaths);

        // 批量上传图片up
        BmobFile.uploadBatch(getActivity(), imgs, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {

                // 全部上传完毕， 大图+缩略图
                if (imagePaths.size() * 2 == files.size()) {
                    setTeamRequestImageFile(files);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // 删除temp文件
                Log.e("uploadImage：", errormsg);
                ImageFile.delTempFile();
                toDo(LOADING_ERROR, 0);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
//                Log.e("onProgress: ", "  curIndex"+curIndex+"   curPercent"+curPercent+"  total"+total+"  totalPercent"+totalPercent);
            }
        });
    }

    private void setTeamRequestImageFile(List<BmobFile> files) {
        final ImageFile imageFile = new ImageFile();

        // setup ImageFile
        imageFile.setFiles(files);

        // save
        imageFile.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                moment.setImageFile(imageFile);
                ImageFile.delTempFile();
                toDo(ON_LOADING, 0);
            }

            @Override
            public void onFailure(int i, String s) {
                toDo(LOADING_ERROR, 0);
            }
        });
    }

    @Override
    public void onResume() {
        if (getActivity() != null) {
            AndroidImagePicker.getInstance().setOnPictureTakeCompleteListener(this);
            AndroidImagePicker.getInstance().setOnImagePickCompleteListener(this);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (getActivity() != null) {
            AndroidImagePicker.getInstance().deleteOnImagePickCompleteListener(this);
            AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);
        }
        super.onDestroy();
    }

}
