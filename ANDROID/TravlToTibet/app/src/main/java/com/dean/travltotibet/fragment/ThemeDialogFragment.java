package com.dean.travltotibet.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 10/18/15.
 */
public abstract class ThemeDialogFragment extends DialogFragment {

    private Dialog mDialog;
    private Context mContext;
    private View root;

    private TextView mTitle;
    private ImageView closeIcon;
    private Button okButton;
    private Button cancelButton;

    private LinearLayout mContentView;
    private View mButtonView;

    public ThemeDialogFragment(Context mContext) {
        this.mContext = mContext;
    }

    public void setDialogTitle(String title) {
        mTitle.setText(title);
    }

    public void setDialogContentView(View view) {
        mContentView.addView(view);
    }

    public void setDialogCloseButton(View.OnClickListener onClickListener) {
        closeIcon.setOnClickListener(onClickListener);
    }

    public void setDialogOKButton(String title, DialogInterface.OnClickListener onClickListener) {

    }

    public void setDialogCancelButton(String title, DialogInterface.OnClickListener onClickListener) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new Dialog(mContext, R.style.Transparent_Dialog);
        mDialog.setContentView(R.layout.theme_dialog_layout);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        root = inflater.inflate(R.layout.theme_dialog_layout, null);

        mTitle = (TextView) root.findViewById(R.id.dialog_title);
        closeIcon = (ImageView) root.findViewById(R.id.dialog_close);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mContentView = (LinearLayout) root.findViewById(R.id.dialog_content);
    }

    public Dialog getDialog() {
        return mDialog;
    }

}
