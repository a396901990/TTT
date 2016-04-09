package com.dean.travltotibet.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.ImagePickerActivity;
import com.dean.travltotibet.activity.RoadInfoCreateActivity;
import com.dean.travltotibet.adapter.ImagePickAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.model.ImageFile;
import com.dean.travltotibet.model.RoadInfo;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.HorizontalItemDecoration;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoadingManager;
import com.dean.travltotibet.util.ScreenUtil;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by DeanGuo on 2/23/16.
 */
public class RoadInfoCreateFragment extends BaseRefreshFragment implements AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener {

    private View root;

    private RoadInfoCreateActivity mActivity;

    private final static int TEXT_MAX_LIMIT = 140;

    private final static int TEXT_MIN_LIMIT = 6;

    private RoadInfo roadInfo;

    EditText contentEdit;

    private ImagePickAdapter imagePickAdapter;

    private LoadingManager loadingManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.road_info_create_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (RoadInfoCreateActivity) this.getActivity();
        roadInfo = new RoadInfo();
        loadingManager = new LoadingManager(getActivity());

        initContentContent();
        initImagePickerContent();
    }

    private void initImagePickerContent() {
        // 设置提示
        TextView imageHint = (TextView) root.findViewById(R.id.image_hint);
        imageHint.setVisibility(View.GONE);

        TextView imageTitle = (TextView) root.findViewById(R.id.image_hint);
        imageTitle.setText("路况照片");

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.picker_image_list_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new HorizontalItemDecoration(ScreenUtil.dip2px(getActivity(), 2)));
        imagePickAdapter = new ImagePickAdapter(getActivity());
        imagePickAdapter.setAddImageListener(new ImagePickAdapter.AddImageListener() {
            @Override
            public void onAddImage() {

                // 6.0 检查存储运行权限
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    goToPicker();
                }
            }
        });
        recyclerView.setAdapter(imagePickAdapter);
    }

    @Override
    public void onPictureTakeComplete(String picturePath) {
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add(picturePath);
        imagePickAdapter.clearData();
        imagePickAdapter.addData(urlList);
    }

    @Override
    public void onImagePickComplete(List<ImageItem> items) {
        List<ImageItem> imageList = AndroidImagePicker.getInstance().getSelectedImages();
        ArrayList<String> urlList = new ArrayList<>();
        for (ImageItem i : imageList) {
            urlList.add(i.path);
        }
        imagePickAdapter.addData(urlList);
    }

    public void goToPicker() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(IntentExtra.INTENT_IMAGE_SELECTED, imagePickAdapter.getData().size());
        startActivity(intent);
    }

    // 内容
    private void initContentContent() {
        contentEdit = (EditText) root.findViewById(R.id.content_edit_text);
        contentEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(TEXT_MAX_LIMIT)});
        contentEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTextLimitHint();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void updateTextLimitHint() {
        TextView textLimitHint = (TextView) root.findViewById(R.id.text_limit_hint);
        String hint = String.format(Constants.TEAM_REQUEST_CONTENT_LIMIT_HINT, contentEdit.getText().length(), TEXT_MAX_LIMIT);
        textLimitHint.setText(hint);
    }


    // 提交请求
    public void commitRequest() {
        // set content value
        String contentString = contentEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(contentString) && contentString.length() >= TEXT_MIN_LIMIT) {
            roadInfo.setContent(contentEdit.getText().toString().trim());

            toDo(PREPARE_LOADING, 0);
        } else {
            Toast.makeText(getActivity(), "内容不得少于10个字", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void prepareLoading() {
        super.prepareLoading();

        // show loading dialog
        loadingManager.showLoading();

        // save上传图片后操作
        if (imagePickAdapter.getData() == null || imagePickAdapter.getData().size() == 0) {
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
            roadInfo.setUser(TTTApplication.getUserInfo());
        }
        roadInfo.setStatus(RoadInfo.WAIT_STATUS);
        roadInfo.setRoute(mActivity.getRouteName());
        roadInfo.setTitle("");
        roadInfo.setWatch(0);
        roadInfo.setComment(0);
        roadInfo.setPriority(RoadInfo.NORMAL);
        roadInfo.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                if (mActivity == null) {
                    return;
                }
                toDo(LOADING_SUCCESS, 0);
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
        String[] imgs = ImageFile.getCompressUrl(imagePickAdapter.getData());

        // 批量上传图片up
        Bmob.uploadBatch(getActivity(), imgs, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {

                // 全部上传完毕， 大图+缩略图
                if (imagePickAdapter.getData().size() * 2 == files.size()) {
                    setTeamRequestImageFile(files);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // 删除temp文件
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
                roadInfo.setImageFile(imageFile);
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
