package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.a.a.V;
import com.dean.travltotibet.R;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.ScreenUtil;

/**
 * Created by DeanGuo on 5/12/16.
 */
public class ContactUsDialogFragment extends DialogFragment {

    private boolean isShowDetail;

    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.contact_dialog_layout, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        if (isShowDetail) {
            View detailContent = root.findViewById(R.id.detail_content);
            detailContent.setVisibility(View.VISIBLE);
        } else {
            View detailContent = root.findViewById(R.id.detail_content);
            detailContent.setVisibility(View.GONE);
        }

        // qq
        View qqCopy = root.findViewById(R.id.qq_copy_btn);
        qqCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                TextView qqText = (TextView) root.findViewById(R.id.qq_text);
                cmb.setText(qqText.getText().toString());
                Toast.makeText(getActivity(), getString(R.string.copy_success),Toast.LENGTH_LONG).show();
            }
        });

        // qq group
        View qqGroupCopy = root.findViewById(R.id.qq_group_copy_btn);
        qqGroupCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                TextView qqGroupText = (TextView) root.findViewById(R.id.qq_group_text);
                cmb.setText(qqGroupText.getText().toString());
                Toast.makeText(getActivity(), getString(R.string.copy_success),Toast.LENGTH_LONG).show();
            }
        });

        // email
        View emailCopy = root.findViewById(R.id.email_copy_btn);
        emailCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                TextView emailText = (TextView) root.findViewById(R.id.email_text);
                cmb.setText(emailText.getText().toString());
                Toast.makeText(getActivity(),getString(R.string.copy_success),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        Window window = getDialog().getWindow();
        //window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(getActivity(), 330));
//        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, ScreenUtil.dip2px(getActivity(), 400));
    }

    public boolean isShowDetail() {
        return isShowDetail;
    }

    public void setShowDetail(boolean showDetail) {
        isShowDetail = showDetail;
    }
}
