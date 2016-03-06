package com.dean.travltotibet.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.HotDestinationAdapter;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.ScreenUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by DeanGuo on 2/24/16.
 * 选择旅行类型
 */
public class TeamMakeTravelTypeDialog extends DialogFragment {

    private View contentLayout;

    private HotDestinationAdapter mAdapter;

    private EditText destEditText;

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
        initListView();
        return contentLayout;
    }

    private void initListView() {
        ListView listView = (ListView) contentLayout.findViewById(R.id.dest_list_view);
        mAdapter = new HotDestinationAdapter(getActivity());
        String[] routes = TTTApplication.getMyResources().getStringArray(R.array.hot_type);
        ArrayList<String> mData = new ArrayList<>();
        Collections.addAll(mData, routes);
        mAdapter.setData(mData);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String routeName = (String) mAdapter.getItem(position);
                destEditText.setText(routeName);
            }
        });

    }

    private void setUpView() {
        destEditText = (EditText) contentLayout.findViewById(R.id.destination_edit_view);

        View okBtn = contentLayout.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(destEditText.getText())) {
                    notifyItemClicked(destEditText.getText().toString());
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