package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dean.travltotibet.R;
import com.dean.travltotibet.util.IntentExtra;

/**
 * Created by DeanGuo on 10/22/15.
 */
public class FeedbackFragment extends Fragment {

    private View root;

    private EditText email;

    private EditText phone;

    private EditText note;

    private boolean isEnable = false;

    private ProgressDialog mProgressDialog;

    private static final int ENABLE_ALPHA = 255;

    private static final int DISABLE_ALPHA = (int) (ENABLE_ALPHA * 0.3);

    public FeedbackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.feed_back_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initView();
    }

    private void initView() {
        phone = (EditText) root.findViewById(R.id.phone_number);
        // set all caps filter for phone
        phone.setFilters(new InputFilter[]
                { new InputFilter.LengthFilter(11) });

        email = (EditText) root.findViewById(R.id.email_address);

        note = (EditText) root.findViewById(R.id.note);
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
        // show loading progress bar
        showLoading(R.string.submit_feedback);

        Bundle bundle = new Bundle();
        bundle.putString(IntentExtra.INTENT_FEEDBACK_PHONE_NUMBER, phone.getText().toString());
        bundle.putString(IntentExtra.INTENT_FEEDBACK_EMAIL_ADDRESS, email.getText().toString());
        bundle.putString(IntentExtra.INTENT_FEEDBACK_NOTE, note.getText().toString());

//        提交反馈数据到服务器，成功关闭进度条，现实成功
//        失败现实失败对话框
//        参考addnotefragment
    }

    protected void showLoading(final int argResourceId) {
        if (mProgressDialog == null || !mProgressDialog.isShowing()) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage(getString(argResourceId));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    protected void dismissLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
