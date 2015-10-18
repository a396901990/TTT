package com.dean.travltotibet.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 10/19/15.
 */
public class NormalDialog extends Dialog {

    private TextView mTitle;
    private TextView mContentMsg;
    private TextView mOKBtn;
    private TextView mCancelBtn;

    private Context mContext;

    public NormalDialog(Context context) {
        super(context);
    }

    public NormalDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;

        initDialogView();
    }

    protected NormalDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initDialogView() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.theme_dialog_normal_layout, null);
        mTitle = (TextView) root.findViewById(R.id.dialog_title);
        mContentMsg = (TextView) root.findViewById(R.id.dialog_content_msg);
        mCancelBtn = (TextView) root.findViewById(R.id.dialog_cancel_btn);
        mOKBtn = (TextView) root.findViewById(R.id.dialog_ok_btn);

        setContentView(root);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setMsg(CharSequence msg) {
        mContentMsg.setText(msg);
    }

    public void setCancelListener(View.OnClickListener listener) {
        mCancelBtn.setOnClickListener(listener);
    }

    public void setOKListener(View.OnClickListener listener) {
        mOKBtn.setOnClickListener(listener);
    }

}
