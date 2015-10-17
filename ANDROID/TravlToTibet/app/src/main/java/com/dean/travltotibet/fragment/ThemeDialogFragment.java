package com.dean.travltotibet.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 10/18/15.
 */
public abstract class ThemeDialogFragment extends DialogFragment {

    private Dialog mDialog;
    private View root;

    private TextView title;
    private ImageView closeIcon;
    private Button okButton;
    private Button cancelButton;

    private View mContentView;
    private View mButtonView;

    public void setDialogTitle(String title) {

    }

    public void setDialogContentView(int resourece) {
        mDialog.setContentView(resourece);
    }

    public void setDialogCloseButton(DialogInterface.OnClickListener onClickListener) {

    }

    public void setDialogOKButton(String title, DialogInterface.OnClickListener onClickListener) {

    }

    public void setDialogCancelButton(String title, DialogInterface.OnClickListener onClickListener) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mDialog = new Dialog(getActivity(), R.style.Transparent_Dialog);
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        root = inflater.inflate(R.layout.theme_dialog_layout, null);
//
//        title = (TextView) root.findViewById(R.id.dialog_title);
//        closeIcon = (ImageView) root.findViewById(R.id.dialog_close);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }
}
