package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 9/19/15.
 * <p/>
 * 用来控制route类型和计划
 */
public class InfoHeaderFragment extends BaseInfoFragment {

    private InfoRouteActivity infoRouteActivity;

    private View root;

    private TextView returnBtn;

    private ImageView typeBtn;

    public InfoHeaderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.info_header_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        infoRouteActivity = (InfoRouteActivity) getActivity();
        initHeaderButton();
    }

    /**
     * 初始化header左右两侧按钮
     */
    private void initHeaderButton() {
        returnBtn = (TextView)root.findViewById(R.id.header_return_btn);
        typeBtn = (ImageView) root.findViewById(R.id.header_travel_type);

        // 设置按钮文字和按钮图片
        returnBtn.setText(infoRouteActivity.getRouteName());
        typeBtn.setImageDrawable(TravelType.getTypeImageSrc(infoRouteActivity.getRouteType()));

        // 设置监听
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TravelTypeDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_ROUTE, infoRouteActivity.getRoute());
                bundle.putString(Constants.INTENT_ROUTE_NAME, infoRouteActivity.getRouteName());
                bundle.putString(Constants.INTENT_FROM_WHERE, TravelTypeDialog.FROM_SECOND);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), TravelTypeDialog.class.getName());
            }
        });
    }

    @Override
    public void updateType(String type) {
        super.updateType(type);
        typeBtn.setImageDrawable(TravelType.getTypeImageSrc(infoRouteActivity.getRouteType()));
    }
}
