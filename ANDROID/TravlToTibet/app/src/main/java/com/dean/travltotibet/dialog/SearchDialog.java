package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.FlowLayout;
import com.dean.travltotibet.ui.tagview.Tag;
import com.dean.travltotibet.util.SearchFilterManger;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 4/28/16.
 */
public class SearchDialog extends DialogFragment{

    private SearchCallBack searchCallBack;

    public static interface SearchCallBack {
        public void onFinished();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    public SearchCallBack getSearchCallBack() {
        return searchCallBack;
    }

    public void setSearchCallBack(SearchCallBack searchCallBack) {
        this.searchCallBack = searchCallBack;
    }

}