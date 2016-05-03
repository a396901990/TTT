package com.dean.travltotibet.fragment;

import android.os.Bundle;
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
import com.dean.travltotibet.activity.QACreateActivity;
import com.dean.travltotibet.base.BaseRefreshFragment;
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.Flag;
import com.dean.travltotibet.util.LoadingManager;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DeanGuo on 2/23/16.
 */
public class QACreateUpdateFragment extends BaseRefreshFragment {

    private View root;

    private QACreateActivity mActivity;

    private final static int TEXT_TITLE_MAX_LIMIT = 50;

    private final static int TEXT_TITLE_MIN_LIMIT = 6;

    private final static int TEXT_CONTENT_MAX_LIMIT = 666;

    private final static int TEXT_CONTENT_MIN_LIMIT = 10;

    private int PASS_TITLE = 1 << 0; // 0


    private int PASS_CONTENT = 1 << 1; // 1

    private Flag filed = new Flag();

    private QARequest qaRequest;

    EditText titleEdit, contentEdit;

    private boolean isUpdate = false;

    private LoadingManager loadingManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.q_a_create_update_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (QACreateActivity) this.getActivity();
        qaRequest = mActivity.getQaRequest();
        loadingManager = new LoadingManager(getActivity());

        initTitleContent();
        initContentContent();
        filedItem();
    }

    /**
     * 如果是修改操作，则填充数据
     */
    private void filedItem() {
        if (qaRequest != null) {
            isUpdate = true;

            // title
            if (!TextUtils.isEmpty(qaRequest.getTitle())) {
                contentEdit.setText(qaRequest.getTitle());
            }
            // content
            if (!TextUtils.isEmpty(qaRequest.getContent())) {
                titleEdit.setText(qaRequest.getContent());
            }
        } else {
            qaRequest = new QARequest();
        }
    }

    // 标题
    private void initTitleContent() {
        titleEdit = (EditText) root.findViewById(R.id.title_edit_text);
        titleEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(TEXT_CONTENT_MAX_LIMIT)});
        titleEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTitleTextLimitHint();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // 内容
    private void initContentContent() {
        contentEdit = (EditText) root.findViewById(R.id.content_edit_text);
        contentEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(TEXT_CONTENT_MAX_LIMIT)});
        contentEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateContentTextLimitHint();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void updateContentTextLimitHint() {
        TextView textLimitHint = (TextView) root.findViewById(R.id.text_content_limit_hint);
        String hint = String.format(Constants.TEAM_REQUEST_CONTENT_LIMIT_HINT, contentEdit.getText().length(), TEXT_CONTENT_MAX_LIMIT);
        textLimitHint.setText(hint);
    }

    private void updateTitleTextLimitHint() {
        TextView textLimitHint = (TextView) root.findViewById(R.id.text_title_limit_hint);
        String hint = String.format(Constants.TEAM_REQUEST_CONTENT_LIMIT_HINT, titleEdit.getText().length(), TEXT_TITLE_MAX_LIMIT);
        textLimitHint.setText(hint);
    }

    // 提交请求
    public void commitRequest() {
        // set content value
        String contentString = contentEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(contentString) && contentString.length() >= TEXT_CONTENT_MIN_LIMIT) {
            filed.set(PASS_CONTENT);
            qaRequest.setContent(contentString);
        } else {
            filed.clear(PASS_CONTENT);
        }

        // set title value
        String titleString = titleEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(titleString) && titleString.length() >= TEXT_TITLE_MIN_LIMIT) {
            filed.set(PASS_TITLE);
            qaRequest.setTitle(titleString);
        } else {
            filed.clear(PASS_TITLE);
        }

        if (checkIsOk()) {
            toDo(PREPARE_LOADING, 0);
        }
    }

    @Override
    public void prepareLoading() {
        super.prepareLoading();

        // show loading searchDialog
        loadingManager.showLoading();
        toDo(ON_LOADING, 0);
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

    private void saveOrUpdate() {
        if (isUpdate) {
            updateAction();
        } else {
            saveAction();
        }
    }

    public void updateAction() {
        // 如果之前已经通过，则这次也通过
        if (TeamRequest.PASS_STATUS.equals(qaRequest.getStatus())) {
            qaRequest.setStatus(QARequest.PASS_STATUS);
        }
        // 如果之前是审核或者未通过，这次是审核
        else {
            qaRequest.setStatus(QARequest.WAIT_STATUS);
        }
        qaRequest.update(getActivity(), new UpdateListener() {
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

        qaRequest.setUser(TTTApplication.getUserInfo());
        qaRequest.setUserName(TTTApplication.getUserInfo().getUserName());
        qaRequest.setUserIcon(TTTApplication.getUserInfo().getUserIcon());
        qaRequest.setUserGender(TTTApplication.getUserInfo().getUserGender());
        qaRequest.setComments(0);
        qaRequest.setWatch(0);
        qaRequest.setStatus(QARequest.PASS_STATUS);

        qaRequest.save(getActivity(), new SaveListener() {
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

    private boolean checkIsOk() {

        if (!filed.isSet(PASS_TITLE)) {
            Toast.makeText(getActivity(), "标题不得少于10个字", Toast.LENGTH_SHORT).show();
        } else if (!filed.isSet(PASS_CONTENT)) {
            Toast.makeText(getActivity(), "内容不得少于10个字", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

}
