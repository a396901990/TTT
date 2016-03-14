package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputFilter;
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
 * Created by DeanGuo on 3/9/16.
 * 选择旅行类型
 */
public class TeamMakeTravelTypeDialog extends DialogFragment {

    private final static int TRAVEL_LIMIT = 12;

    private View contentLayout;

    private EditText travelEditText;

    private TravelTypeCallback travelTypeCallback;

    public static interface TravelTypeCallback {
        public void travelTypeChanged(String type);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.team_create_type_dialog_view, null);

        setUpView();
        initHotTypeView();
        return contentLayout;
    }

    private void initHotTypeView() {
        RecyclerView hotTypeView = (RecyclerView) contentLayout.findViewById(R.id.hot_type_fragment_list_rv);
        hotTypeView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        CommonGridAdapter mAdapter = new CommonGridAdapter(getActivity());
        mAdapter.setSelectCallBack(new CommonGridAdapter.SelectCallBack() {
            @Override
            public void onItemSelect(String name) {
                travelEditText.setText(name);
            }
        });
        String[] routes = TTTApplication.getMyResources().getStringArray(R.array.hot_type);
        final ArrayList<String> mData = new ArrayList<>();
        Collections.addAll(mData, routes);
        mAdapter.setData(mData);
        hotTypeView.setAdapter(mAdapter);
    }


    private void setUpView() {
        travelEditText = (EditText) contentLayout.findViewById(R.id.destination_edit_view);
        travelEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(TRAVEL_LIMIT)});
        View okBtn = contentLayout.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(travelEditText.getText().toString().trim())) {
                    notifyItemClicked(travelEditText.getText().toString().trim());
                } else {
                    dismiss();
                }
            }
        });
    }

    protected void notifyItemClicked(final String type) {
        travelTypeCallback.travelTypeChanged(type);
        dismiss();
    }

    public void setTravelTypeCallback(TravelTypeCallback travelTypeCallback) {
        this.travelTypeCallback = travelTypeCallback;
    }

}