package com.dean.travltotibet.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.ImagePickerActivity;
import com.dean.travltotibet.activity.TeamCreateRequestActivity;
import com.dean.travltotibet.activity.TeamShowRequestDetailActivity;
import com.dean.travltotibet.adapter.ImagePickAdapter;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.dialog.TeamMakeContactDialog;
import com.dean.travltotibet.dialog.TeamMakeDateDialog;
import com.dean.travltotibet.dialog.TeamMakeDestinationDialog;
import com.dean.travltotibet.dialog.TeamMakeTravelTypeDialog;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.ui.HorizontalItemDecoration;
import com.dean.travltotibet.ui.SpaceItemDecoration;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.Flag;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoadingManager;
import com.dean.travltotibet.util.ScreenUtil;
import com.dean.travltotibet.util.SystemUtil;
import com.dean.travltotibet.util.VolleyImageUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by DeanGuo on 2/23/16.
 */
public class TeamCreateUpdateRequestFragment extends BaseRefreshFragment implements AndroidImagePicker.OnPictureTakeCompleteListener,AndroidImagePicker.OnImagePickCompleteListener{

    private View root;

    private TeamCreateRequestActivity mActivity;

    private final static int TEXT_MAX_LIMIT = 666;

    private final static int TEXT_MIN_LIMIT = 10;

    private int PASS_DATE = 1 << 0; // 0

    private int PASS_DESTINATION = 1 << 1; // 10

    private int PASS_TYPE = 1 << 2; // 100

    private int PASS_CONTACT = 1 << 3; // 1000

    private int PASS_CONTENT = 1 << 4; // 10000

    private Flag filed = new Flag();

    private TeamRequest teamRequest;

    EditText contentEdit;

    private boolean isUpdate = false;

    private ImagePickAdapter imagePickAdapter;

    private LoadingManager loadingManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.team_create_update_request_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (TeamCreateRequestActivity) this.getActivity();
        teamRequest = mActivity.getTeamRequest();
        loadingManager = new LoadingManager(getActivity());

        initTravelDestinationContent();
        initTravelTypeContent();
        initTravelDateContent();

        initContactContent();
        initContentContent();
        initImagePickerContent();
        filedItem();
    }

    private void initImagePickerContent() {
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

    /**
     * 如果是修改操作，则填充数据
     */
    private void filedItem() {
        if (teamRequest != null) {
            isUpdate = true;
            // contact phone,qq,wechat
            setContact(teamRequest.getContactPhone(), TeamMakeContactDialog.PHONE);
            setContact(teamRequest.getContactQQ(), TeamMakeContactDialog.QQ);
            setContact(teamRequest.getContactWeChat(), TeamMakeContactDialog.WECHAT);

            // content
            if (!TextUtils.isEmpty(teamRequest.getContent())) {
                contentEdit.setText(teamRequest.getContent());
            }
            // type
            if (!TextUtils.isEmpty(teamRequest.getType())) {
                setTravelType(teamRequest.getType());
            }
            // date
            if (!TextUtils.isEmpty(teamRequest.getDate())) {
                setTravelDate(teamRequest.getDate());
            }
            // destination
            if (!TextUtils.isEmpty(teamRequest.getDestination())) {
                setTravelDestination(teamRequest.getDestination());
            }
            // image
            imagePickAdapter.addData((ArrayList<String>) teamRequest.getImgUrls());
            imagePickAdapter.setIsOnlyShow(isUpdate);
        } else {
            teamRequest = new TeamRequest();
        }
    }

    // 联系方式
    private void initContactContent() {
        // phone
        View phoneBtn = root.findViewById(R.id.phone_content);
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeContactDialog dialogFragment = new TeamMakeContactDialog();
                dialogFragment.setContactType(TeamMakeContactDialog.PHONE);
                dialogFragment.setContactCallback(new TeamMakeContactDialog.ContactCallback() {
                    @Override
                    public void contactChanged(String contact) {
                        setContact(contact, TeamMakeContactDialog.PHONE);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeContactDialog.class.getName());
            }
        });

        // qq
        View qqBtn = root.findViewById(R.id.qq_content);
        qqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeContactDialog dialogFragment = new TeamMakeContactDialog();
                dialogFragment.setContactType(TeamMakeContactDialog.QQ);
                dialogFragment.setContactCallback(new TeamMakeContactDialog.ContactCallback() {
                    @Override
                    public void contactChanged(String contact) {
                        setContact(contact, TeamMakeContactDialog.QQ);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeContactDialog.class.getName());
            }
        });

        // wechat
        View wechatBtn = root.findViewById(R.id.wechat_content);
        wechatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeContactDialog dialogFragment = new TeamMakeContactDialog();
                dialogFragment.setContactType(TeamMakeContactDialog.WECHAT);
                dialogFragment.setContactCallback(new TeamMakeContactDialog.ContactCallback() {
                    @Override
                    public void contactChanged(String contact) {
                        setContact(contact, TeamMakeContactDialog.WECHAT);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeContactDialog.class.getName());
            }
        });
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

    // 旅行类型
    private void initTravelTypeContent() {
        View typeBtn = root.findViewById(R.id.type_btn);
        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeTravelTypeDialog dialogFragment = new TeamMakeTravelTypeDialog();
                dialogFragment.setTravelTypeCallback(new TeamMakeTravelTypeDialog.TravelTypeCallback() {
                    @Override
                    public void travelTypeChanged(String type) {
                        setTravelType(type);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeTravelTypeDialog.class.getName());
            }
        });
    }

    // 目的地
    private void initTravelDestinationContent() {
        View destinationBtn = root.findViewById(R.id.destination_btn);
        destinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeDestinationDialog dialogFragment = new TeamMakeDestinationDialog();
                dialogFragment.setTravelDestinationCallback(new TeamMakeDestinationDialog.TravelDestinationCallback() {
                    @Override
                    public void travelDestinationChanged(String destination) {
                        setTravelDestination(destination);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeTravelTypeDialog.class.getName());
            }
        });
    }

    // 日期
    private void initTravelDateContent() {
        View dateBtn = root.findViewById(R.id.date_btn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamMakeDateDialog dialogFragment = new TeamMakeDateDialog();
                dialogFragment.setDateCallback(new TeamMakeDateDialog.TravelDateCallback() {
                    @Override
                    public void dateChanged(String date) {
                        setTravelDate(date);
                    }
                });
                dialogFragment.show(getFragmentManager(), TeamMakeDateDialog.class.getName());
            }
        });
    }

    private void setTravelDate(String date) {
        TextView travelDate = (TextView) root.findViewById(R.id.travel_date);
        travelDate.setText(date);
        filed.set(PASS_DATE);
        teamRequest.setDate(date);
    }

    private void setContact(String contact, int contactType) {

        switch (contactType) {
            case TeamMakeContactDialog.PHONE:
                TextView phoneText = (TextView) root.findViewById(R.id.phone_text);
                if (!TextUtils.isEmpty(contact)) {
                    phoneText.setText(contact);
                    teamRequest.setContactPhone(contact);
                } else {
                    phoneText.setText(getString(R.string.setting_phone_text));
                    teamRequest.setContactPhone("");
                }
                break;
            case TeamMakeContactDialog.QQ:
                TextView qqText = (TextView) root.findViewById(R.id.qq_text);
                if (!TextUtils.isEmpty(contact)) {
                    qqText.setText(contact);
                    teamRequest.setContactQQ(contact);
                } else {
                    qqText.setText(getString(R.string.setting_qq_text));
                    teamRequest.setContactQQ("");
                }
                break;
            case TeamMakeContactDialog.WECHAT:
                TextView wechatText = (TextView) root.findViewById(R.id.wechat_text);
                if (!TextUtils.isEmpty(contact)) {
                    wechatText.setText(contact);
                    teamRequest.setContactWeChat(contact);
                } else {
                    wechatText.setText(getString(R.string.setting_wechat_text));
                    teamRequest.setContactWeChat("");
                }
                break;
        }
    }

    private void setTravelType(String type) {
        TextView travelType = (TextView) root.findViewById(R.id.type_text);
        travelType.setText(type);
        filed.set(PASS_TYPE);
        teamRequest.setType(type);
    }

    private void setTravelDestination(String destination) {
        TextView travelDestination = (TextView) root.findViewById(R.id.destination_text);
        travelDestination.setText(destination);
        filed.set(PASS_DESTINATION);
        teamRequest.setDestination(destination);
    }

    // 提交请求
    public void commitRequest() {
        // set content value
        String contentString = contentEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(contentString) && contentString.length() >= TEXT_MIN_LIMIT) {
            filed.set(PASS_CONTENT);
            teamRequest.setContent(contentEdit.getText().toString().trim());
        } else {
            filed.clear(PASS_CONTENT);
        }

        if (checkIsOk()) {
            toDo(PREPARE_LOADING, 0);
        }
    }

    @Override
    public void prepareLoading() {
        super.prepareLoading();

        // show loading dialog
        loadingManager.showLoading();

        // update不需要上传图片，直接操作
        if (isUpdate) {
            toDo(ON_LOADING, 0);
        }
        // save上传图片后操作
        else {
            uploadImage();
        }
    }

    @Override
    public void onLoading() {
        super.onLoading();
        saveOrUpdate();
    }

    @Override
    public void LoadingSuccess() {
        super.LoadingSuccess();

        loadingManager.loadingSuccess(new LoadingManager.LoadingSuccessCallBack() {
            @Override
            public void afterLoadingSuccess() {
                if (isUpdate) {
                    Intent intent = new Intent(getActivity(), TeamShowRequestDetailActivity.class);
                    intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST, teamRequest);
                    intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST_IS_PERSONAL, true);
                    getActivity().startActivity(intent);
                    mActivity.finish();
                } else {
                    mActivity.setResult(mActivity.RESULT_OK);
                    mActivity.finish();
                }
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

    private void saveOrUpdate() {
        if (isUpdate) {
            updateAction();
        } else {
            saveAction();
        }
    }

    public void updateAction() {
        // 如果之前已经通过，则这次也通过
        if (TeamRequest.PASS_STATUS.equals(teamRequest.getStatus())) {
            teamRequest.setStatus(TeamRequest.PASS_STATUS);
        }
        // 如果之前是审核或者未通过，这次是审核
        else {
            teamRequest.setStatus(TeamRequest.WAIT_STATUS);
        }
        teamRequest.update(getActivity(), new UpdateListener() {
            @Override
            public void onSuccess() {
                if (mActivity == null) {
                    return;
                }
                toDo(LOADING_SUCCESS, 0);
            }

            @Override
            public void onFailure(int i, String s) {
                if (mActivity == null) {
                    return;
                }
                toDo(LOADING_ERROR, 0);
            }
        });
    }

    public void saveAction() {

        if (TTTApplication.getUserInfo() == null) {
            toDo(LOADING_ERROR, 0);
        }

        teamRequest.setUser(TTTApplication.getUserInfo());
        teamRequest.setUserId(TTTApplication.getUserInfo().getUserId());
        teamRequest.setUserName(TTTApplication.getUserInfo().getUserName());
        teamRequest.setUserIcon(TTTApplication.getUserInfo().getUserIcon());
        teamRequest.setUserGender(TTTApplication.getUserInfo().getUserGender());
        teamRequest.setComments(0);
        teamRequest.setWatch(0);
        teamRequest.setStatus(TeamRequest.PASS_STATUS);
//        teamRequest.setStatus("Test");

        teamRequest.save(getActivity(), new SaveListener() {
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
        if (imagePickAdapter.getData().size() == 0) {
            return;
        }
        String[] imgs = new String[imagePickAdapter.getData().size()];
        // 创建temp文件
        SystemUtil.createTempFile();
        // 循环压缩图片
        for (int i=0; i < imagePickAdapter.getData().size(); i++) {
            String outFileUrl = SystemUtil.getMyPicPath() + "/" + TTTApplication.getUserInfo().getUserName() + i + ".jpg";
            VolleyImageUtils.compress(imagePickAdapter.getData().get(i), outFileUrl, 600, 800, 100);
            imgs[i] = outFileUrl;
        }

        // 批量上传图片
        Bmob.uploadBatch(getActivity(), imgs, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                // 全部上传完毕
                if (imagePickAdapter.getData().size() == files.size()) {
                    teamRequest.setImgUrls(urls);
                    // 删除temp文件
                    SystemUtil.delTempFile();

                    toDo(ON_LOADING, 0);
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // 删除temp文件
                SystemUtil.delTempFile();
                toDo(LOADING_ERROR, 0);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
//                Log.e("onProgress: ", "  curIndex"+curIndex+"   curPercent"+curPercent+"  total"+total+"  totalPercent"+totalPercent);
            }
        });
    }

    private boolean checkIsOk() {

        // 设置contact是否通过，如果不等于默认值则通过
        TextView phoneText = (TextView) root.findViewById(R.id.phone_text);
        TextView qqText = (TextView) root.findViewById(R.id.qq_text);
        TextView wechatText = (TextView) root.findViewById(R.id.wechat_text);
        if (getString(R.string.setting_phone_text).equals(phoneText.getText()) && getString(R.string.setting_qq_text).equals(qqText.getText()) && getString(R.string.setting_wechat_text).equals(wechatText.getText())) {
            filed.clear(PASS_CONTACT);
        } else {
            filed.set(PASS_CONTACT);
        }

        if (!filed.isSet(PASS_DATE)) {
            Toast.makeText(getActivity(), "请设置出行日期", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_DESTINATION)) {
            Toast.makeText(getActivity(), "请设置目的地", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_TYPE)) {
            Toast.makeText(getActivity(), "请设置旅行方式", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_CONTACT)) {
            Toast.makeText(getActivity(), "请设置至少一种联系方式", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_CONTENT)) {
            Toast.makeText(getActivity(), "内容不得少于10个字", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        AndroidImagePicker.getInstance().setOnPictureTakeCompleteListener(this);
        AndroidImagePicker.getInstance().setOnImagePickCompleteListener(this);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        AndroidImagePicker.getInstance().deleteOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().deleteOnPictureTakeCompleteListener(this);
        super.onDestroy();
    }

}
