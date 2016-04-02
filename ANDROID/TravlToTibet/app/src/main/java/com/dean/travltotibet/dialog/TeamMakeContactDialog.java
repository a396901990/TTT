package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.dean.travltotibet.R;
import com.dean.travltotibet.util.ScreenUtil;

/**
 * Created by DeanGuo on 3/13/16.
 * 选择旅行类型
 */
public class TeamMakeContactDialog extends DialogFragment {

    private View contentLayout;

    private EditText contactText;

    private ContactCallback contactCallback;

    private final static int PHONE_LIMIT = 11;

    private final static int QQ_LIMIT = 11;

    private final static int WECHAT_LIMIT = 20;

    public final static int PHONE = 0;

    public final static int QQ = 1;

    public final static int WECHAT = 2;

    private int contactType;

    public static interface ContactCallback {
        public void contactChanged(String contact);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.team_create_contact_dialog_view, null);

        bottomView();
        initContact();
        return contentLayout;
    }

    private void initContact() {
        contactText = (EditText) contentLayout.findViewById(R.id.contact_edit_text);
        contactText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        switch (contactType) {
            case PHONE:
                contactText.setHint("请输入您的手机号");
                contactText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PHONE_LIMIT)});
                contactText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case QQ:
                contactText.setHint("请输入您的QQ号");
                contactText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(QQ_LIMIT)});
                contactText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case WECHAT:
                contactText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(WECHAT_LIMIT)});
                contactText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                contactText.setHint("请输入您的微信号");
                break;
        }
        contactText.requestFocus();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ScreenUtil.dip2px(getActivity(), 280), WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void bottomView() {
        View okBtn = contentLayout.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }

    private void commit() {
        if (contactCallback != null) {
            contactCallback.contactChanged(contactText.getText().toString().trim());
            dismiss();
        }
    }

    public void setContactCallback(ContactCallback contactCallback) {
        this.contactCallback = contactCallback;
    }

    public void setContactType(int contactType) {
        this.contactType = contactType;
    }

}