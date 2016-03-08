package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.niceSpinner.NiceSpinner;
import com.dean.travltotibet.util.TeamRequestFilter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by DeanGuo on 3/8/16.
 */
public class TeamRequestFilterDialog extends DialogFragment {

    private View contentLayout;

    private FilterCallback filterCallback;

    private NiceSpinner destinationSpinner;

    private NiceSpinner typeSpinner;

    private TeamRequestFilter filter;

    public static interface FilterCallback {
        void filterChanged(TeamRequestFilter filter);
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
        initDestinationContent();
        initTypeContent();
        initCommitButton();
        filter = new TeamRequestFilter();
        return contentLayout;
    }

    private void initCommitButton() {
        View view = contentLayout.findViewById(R.id.commit_btn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterCallback != null) {
                    filterCallback.filterChanged(filter);
                }
                getDialog().dismiss();
            }
        });
    }

    private void initDestinationContent() {
        destinationSpinner = (NiceSpinner) contentLayout.findViewById(R.id.destination_spinner);
        String[] routes = TTTApplication.getMyResources().getStringArray(R.array.hot_destination);
        final List<String> mData = new LinkedList<>();
        Collections.addAll(mData, routes);
        mData.add(0, TeamRequestFilter.DEFAULT);
        destinationSpinner.attachDataSource(mData);
        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter.setDestinationFilter(mData.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initTypeContent() {
        typeSpinner = (NiceSpinner) contentLayout.findViewById(R.id.type_spinner);
        String[] types = TTTApplication.getMyResources().getStringArray(R.array.hot_type);
        final List<String> mData = new LinkedList<>();
        Collections.addAll(mData, types);
        mData.add(0, TeamRequestFilter.DEFAULT);
        typeSpinner.attachDataSource(mData);
        typeSpinner.attachDataSource(mData);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter.setTypeFilter(mData.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setFilterCallback(FilterCallback filterCallback) {
        this.filterCallback = filterCallback;
    }
}