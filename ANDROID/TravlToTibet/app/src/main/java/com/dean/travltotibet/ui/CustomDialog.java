package com.dean.travltotibet.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 10/19/15.
 */
public class CustomDialog extends Dialog {

    private TextView mTitle;
    private ViewGroup mContentView;
    private ViewGroup mTitleContent;

    private Context mContext;

    public CustomDialog(Context context) {
        super(context, R.style.Transparent_Dialog);
        mContext = context;

        initDialogView();
    }

    private void initDialogView() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.theme_dialog_custom_layout, null);
        mContentView = (ViewGroup) root.findViewById(R.id.dialog_content);
        mTitleContent = (ViewGroup) root.findViewById(R.id.title_content);

        mTitle = (TextView) root.findViewById(R.id.dialog_title);
        setContentView(root);
    }

    public void showTitleContent() {
        mTitleContent.setVisibility(View.VISIBLE);
    }

    public void hideTitleContent() {
        mTitleContent.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setCustomContentView(View view) {
        mContentView.addView(view);
    }

}
