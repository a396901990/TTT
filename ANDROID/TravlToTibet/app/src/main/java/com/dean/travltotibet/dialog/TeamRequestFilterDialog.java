package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.CommonGridAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by DeanGuo on 3/8/16.
 */
public class TeamRequestFilterDialog extends DialogFragment {

    private View contentLayout;

    private FilterCallback filterCallback;

    private EditText searchView;

    public static interface FilterCallback {
        void filterChanged(String filter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.team_request_filter_dialog_view, null);
        initSearchView();
        initHotDestinationView();
        initHotTypeView();
        return contentLayout;
    }

    private void initSearchView() {
        searchView = (EditText) contentLayout.findViewById(R.id.search_view);

        View searchBtn = contentLayout.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterCallback != null) {
                    filterCallback.filterChanged(searchView.getText().toString());
                }
                dismiss();
            }
        });
    }

    private void initHotDestinationView() {
        RecyclerView mRecyclerView = (RecyclerView) contentLayout.findViewById(R.id.hot_destination_fragment_list_rv);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        CommonGridAdapter mAdapter = new CommonGridAdapter(getActivity());
        mAdapter.setSelectCallBack(new CommonGridAdapter.SelectCallBack() {
            @Override
            public void onItemSelect(String name) {
                searchView.setText(name);
            }
        });

        String[] routes = TTTApplication.getMyResources().getStringArray(R.array.hot_destination);
        final ArrayList<String> mData = new ArrayList<>();
        Collections.addAll(mData, routes);
        mAdapter.setData(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initHotTypeView() {
        RecyclerView hotTypeView = (RecyclerView) contentLayout.findViewById(R.id.hot_type_fragment_list_rv);
        hotTypeView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        CommonGridAdapter mAdapter = new CommonGridAdapter(getActivity());
        mAdapter.setSelectCallBack(new CommonGridAdapter.SelectCallBack() {
            @Override
            public void onItemSelect(String name) {
                searchView.setText(name);
            }
        });
        String[] routes = TTTApplication.getMyResources().getStringArray(R.array.hot_type);
        final ArrayList<String> mData = new ArrayList<>();
        Collections.addAll(mData, routes);
        mAdapter.setData(mData);
        hotTypeView.setAdapter(mAdapter);
    }

    public void setFilterCallback(FilterCallback filterCallback) {
        this.filterCallback = filterCallback;
    }
}