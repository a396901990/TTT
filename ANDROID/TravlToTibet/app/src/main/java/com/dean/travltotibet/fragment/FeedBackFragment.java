package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dean.travltotibet.R;
import com.dean.travltotibet.model.FeedBack;
import com.dean.travltotibet.util.LoadingManager;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DeanGuo on 10/22/15.
 */
public class FeedbackFragment extends Fragment {

    private View root;

    private EditText email;

    private EditText phone;

    private EditText note;

    private boolean isEnable = false;

    private static final int ENABLE_ALPHA = 255;

    private static final int DISABLE_ALPHA = (int) (ENABLE_ALPHA * 0.3);

    private LoadingManager mLoadingManager;

    public FeedbackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.feed_back_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        mLoadingManager = new LoadingManager(getActivity());

        initView();
    }

    private void initView() {
        // 电话
        phone = (EditText) root.findViewById(R.id.phone_number);
        phone.setInputType(InputType.TYPE_CLASS_NUMBER);

        // 邮件
        email = (EditText) root.findViewById(R.id.email_address);
        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        // 反馈
        note = (EditText) root.findViewById(R.id.comment_view);
        note.requestFocus();
        note.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    isEnable = true;
                    getActivity().invalidateOptionsMenu();
                } else {
                    isEnable = false;
                    getActivity().invalidateOptionsMenu();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_submit);

        // set menu icon enable status and opacity
        if (isEnable) {
            item.getIcon().setAlpha(ENABLE_ALPHA);
            item.setEnabled(true);
        } else {
            item.getIcon().setAlpha(DISABLE_ALPHA);
            item.setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // 提交按钮
        if (id == R.id.action_submit) {
            commitNote();
            return true;
        }
        // 结束
        else if (id == android.R.id.home) {
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void commitNote() {

        mLoadingManager.showLoading();

        FeedBack feedBack = new FeedBack();
        feedBack.setFeedback(note.getText().toString());
        feedBack.setPhone(phone.getText().toString());
        feedBack.setEmail(email.getText().toString());

        feedBack.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                mLoadingManager.loadingSuccess(new LoadingManager.LoadingSuccessCallBack() {
                    @Override
                    public void afterLoadingSuccess() {
                        clear();
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                mLoadingManager.loadingFailed(null);
            }
        });

    }

    public void clear() {
        note.setText("");
        phone.setText("");
        email.setText("");
    }

}
