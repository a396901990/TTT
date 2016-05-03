package com.dean.travltotibet.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.tagview.Tag;
import com.dean.travltotibet.util.ScreenUtil;
import com.dean.travltotibet.util.SearchFilterManger;

/**
 * Created by DeanGuo on 5/3/16.
 */
public class QASearchDialog extends SearchDialog {
    
    private View contentLayout;

    private EditText contactText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.q_a_search_dialog_view, null);
        initSearchView();
        bottomView();
        return contentLayout;
    }

    private void initSearchView() {
        contactText = (EditText) contentLayout.findViewById(R.id.search_edit_text);
        contactText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
    }

    private void bottomView() {
        View okBtn = contentLayout.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String viewSymbol = contactText.getText().toString().trim();
                if (!TextUtils.isEmpty(viewSymbol)) {
                    Tag tag = new Tag(viewSymbol);
                    tag.isDeletable = true;
                    tag.layoutColor = TTTApplication.getMyColor(R.color.qa_color);
                    tag.setType(SearchFilterManger.SEARCH_QA);

                    SearchFilterManger.addTagForQAFilter(tag);
                }

                getSearchCallBack().onFinished();
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ScreenUtil.dip2px(getActivity(), 280), WindowManager.LayoutParams.WRAP_CONTENT);
    }
}