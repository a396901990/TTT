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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.CommonGridAdapter;
import com.dean.travltotibet.adapter.HotDestinationAdapter;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by DeanGuo on 2/24/16.
 */
public class TeamMakeDestinationDialog extends DialogFragment {

    private final static int DEST_LIMIT = 20;

    private View contentLayout;

    private TravelDestinationCallback travelDestinationCallback;

    private EditText destEditText;

    public static interface TravelDestinationCallback {
        public void travelDestinationChanged(String type);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TravelTypeDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.team_create_destination_dialog_view, null);

        setUpView();
        initHotDestinationView();
        return contentLayout;
    }

    private void initHotDestinationView() {
        RecyclerView mRecyclerView = (RecyclerView) contentLayout.findViewById(R.id.hot_destination_fragment_list_rv);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        CommonGridAdapter mAdapter = new CommonGridAdapter(getActivity());
        mAdapter.setSelectCallBack(new CommonGridAdapter.SelectCallBack() {
            @Override
            public void onItemSelect(String name) {
                if (!TextUtils.isEmpty(destEditText.getText())) {
                    destEditText.append(Constants.DESTINATION_MARK + name);
                } else {
                    destEditText.append(name);
                }
            }
        });

        String[] routes = TTTApplication.getMyResources().getStringArray(R.array.hot_destination);
        final ArrayList<String> mData = new ArrayList<>();
        Collections.addAll(mData, routes);
        mAdapter.setData(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUpView() {
        destEditText = (EditText) contentLayout.findViewById(R.id.destination_edit_view);
        destEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEST_LIMIT)});
        View okBtn = contentLayout.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(destEditText.getText().toString().trim())) {
                    notifyItemClicked(destEditText.getText().toString().trim());
                } else {
                    dismiss();
                }
            }
        });
    }

    protected void notifyItemClicked(final String destination) {
        travelDestinationCallback.travelDestinationChanged(destination);
        dismiss();
    }

    public void setTravelDestinationCallback(TravelDestinationCallback travelDestinationCallback) {
        this.travelDestinationCallback = travelDestinationCallback;
    }

}