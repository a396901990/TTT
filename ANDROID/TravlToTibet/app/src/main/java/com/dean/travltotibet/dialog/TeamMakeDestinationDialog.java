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
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.ui.FlowLayout;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by DeanGuo on 2/24/16.
 */
public class TeamMakeDestinationDialog extends DialogFragment implements View.OnClickListener {

    private final static int DEST_LIMIT = 20;

    private View contentLayout;

    private TravelDestinationCallback travelDestinationCallback;

    private EditText destEditText;

    private ViewGroup.LayoutParams layoutParams;

    @Override
    public void onClick(View v) {
        final String name = ((TextView) v).getText().toString().trim();
        if (!TextUtils.isEmpty(destEditText.getText())) {
            destEditText.append(Constants.DESTINATION_MARK + name);
        } else {
            destEditText.append(name);
        }
    }

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
        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setUpView();
        initHotRouteView();
        initHotDestinationView();
        initHotScenicView();
        return contentLayout;
    }

    private View addItem(String name) {
        View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_item_view, null, false);
        ((TextView) itemView.findViewById(R.id.item_name)).setText(name);
        itemView.findViewById(R.id.item_name).setOnClickListener(this);
        return itemView;
    }

    private void setUpView() {
        destEditText = (EditText) contentLayout.findViewById(R.id.destination_edit_view);
        destEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEST_LIMIT)});
        View okBtn = contentLayout.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(destEditText.getText().toString().trim())) {
                    travelDestinationCallback.travelDestinationChanged(destEditText.getText().toString().trim());
                }

                dismiss();
            }
        });
    }

    private void initHotScenicView() {
        FlowLayout flowLayout = (FlowLayout) contentLayout.findViewById(R.id.hot_scenic_flow_layout);
        flowLayout.removeAllViews();
        String[] scenics = TTTApplication.getMyResources().getStringArray(R.array.hot_scenic);
        for (String scienc : scenics) {
            flowLayout.addView(addItem(scienc), layoutParams);
        }
    }

    private void initHotRouteView() {
        FlowLayout flowLayout = (FlowLayout) contentLayout.findViewById(R.id.hot_route_flow_layout);
        flowLayout.removeAllViews();
        String[] routes = TTTApplication.getMyResources().getStringArray(R.array.hot_routes);
        for (String route : routes) {
            flowLayout.addView(addItem(route), layoutParams);
        }
    }

    private void initHotDestinationView() {
        FlowLayout flowLayout = (FlowLayout) contentLayout.findViewById(R.id.hot_dest_flow_layout);
        flowLayout.removeAllViews();
        String[] destinations = TTTApplication.getMyResources().getStringArray(R.array.hot_destination);
        for (String dest : destinations) {
            flowLayout.addView(addItem(dest), layoutParams);
        }
    }

    protected void notifyItemClicked(final String destination) {

    }

    public void setTravelDestinationCallback(TravelDestinationCallback travelDestinationCallback) {
        this.travelDestinationCallback = travelDestinationCallback;
    }

}