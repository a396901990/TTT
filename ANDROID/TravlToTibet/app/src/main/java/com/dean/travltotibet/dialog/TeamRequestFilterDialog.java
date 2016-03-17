package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.TeamShowRequestSearchActivity;
import com.dean.travltotibet.ui.FlowLayout;
import com.dean.travltotibet.util.IntentExtra;

/**
 * Created by DeanGuo on 3/8/16.
 */
public class TeamRequestFilterDialog extends DialogFragment implements View.OnClickListener{

    private View contentLayout;

    private EditText searchView;

    private ViewGroup.LayoutParams layoutParams;

    @Override
    public void onClick(View v) {
        final String symbol = ((TextView) v).getText().toString();
        searchView.setText(symbol);
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
        layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        initSearchView();
        initHotTypeView();
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

    private void initHotTypeView() {
        FlowLayout flowLayout = (FlowLayout) contentLayout.findViewById(R.id.hot_type_flow_layout);
        flowLayout.removeAllViews();
        String[] types = TTTApplication.getMyResources().getStringArray(R.array.hot_type);
        for (String type : types) {
            flowLayout.addView(addItem(type), layoutParams);
        }
    }

    private void initSearchView() {
        searchView = (EditText) contentLayout.findViewById(R.id.search_view);

        View searchBtn = contentLayout.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filter = searchView.getText().toString().trim();
                if (!TextUtils.isEmpty(filter)) {
                    Intent intent = new Intent(getActivity(), TeamShowRequestSearchActivity.class);
                    intent.putExtra(IntentExtra.INTENT_TEAM_REQUEST_SEARCH_FILTER, filter);
                    startActivity(intent);
                }

                dismiss();
            }
        });
    }
}