package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 9/19/15.
 * <p/>
 * 用来显示route准备视图
 */
public class InfoPrepareFragment extends BaseInfoFragment {

    private InfoRouteActivity infoRouteActivity;

    private View root;

    public InfoPrepareFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.info_prepare_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        infoRouteActivity = (InfoRouteActivity) getActivity();

        Button btn_f = (Button) root.findViewById(R.id.btn_select_f);

        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment dialogFragment = new InfoConfirmDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_ROUTE, infoRouteActivity.getRoute());
                bundle.putString(Constants.INTENT_ROUTE_NAME, infoRouteActivity.getRouteName());
                bundle.putString(Constants.INTENT_ROUTE_TYPE, infoRouteActivity.getRouteType());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), InfoConfirmDialog.class.getName());
            }
        });
    }

    @Override
    public void updateType(String type) {
        super.updateType(type);
    }
}
