package com.dean.travltotibet.ui;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 10/19/15.
 */
public class CustomDialog extends Dialog {

    private TextView mTitle;
    private ViewGroup mContentView;
    private ImageView mCloseBtn;

    private Context mContext;

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;

        initDialogView();
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initDialogView() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.theme_dialog_layout, null);
        mContentView = (ViewGroup) root.findViewById(R.id.dialog_content);

        mTitle = (TextView) root.findViewById(R.id.dialog_title);
        mCloseBtn = (ImageView) root.findViewById(R.id.dialog_close);
        setContentView(root);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setCloseListener(View.OnClickListener listener) {
        mCloseBtn.setOnClickListener(listener);
    }

    public void setCustomContentView(View view) {
        mContentView.addView(view);
    }

}
