package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by DeanGuo on 3/9/16.
 * 选择旅行类型
 */
public class TeamMakeTravelTypeDialog extends DialogFragment implements View.OnClickListener {

    private final static int TRAVEL_LIMIT = 12;

    private View contentLayout;

    private EditText travelEditText;

    private TravelTypeCallback travelTypeCallback;

    public static interface TravelTypeCallback {
        public void travelTypeChanged(String type);

    }

    @Override
    public void onClick(View v) {
        final String symbol = ((TextView) v).getText().toString().trim();
        travelEditText.setText(symbol);
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
        FlowLayout flowLayout = (FlowLayout) contentLayout.findViewById(R.id.hot_type_flow_layout);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flowLayout.removeAllViews();
        String[] types = TTTApplication.getMyResources().getStringArray(R.array.hot_type);
        for (String type : types) {
            flowLayout.addView(addItem(type), layoutParams);
        }
    }

    private View addItem(String name) {
        View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_item_view, null, false);
        ((TextView) itemView.findViewById(R.id.item_name)).setText(name);
        itemView.findViewById(R.id.item_name).setOnClickListener(this);
        return itemView;
    }

    private void setUpView() {
        travelEditText = (EditText) contentLayout.findViewById(R.id.destination_edit_view);
        travelEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(TRAVEL_LIMIT)});
        View okBtn = contentLayout.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(travelEditText.getText().toString().trim())) {
                    travelTypeCallback.travelTypeChanged(travelEditText.getText().toString().trim());
                }
                dismiss();
            }
        });
    }

    public void setTravelTypeCallback(TravelTypeCallback travelTypeCallback) {
        this.travelTypeCallback = travelTypeCallback;
    }

}