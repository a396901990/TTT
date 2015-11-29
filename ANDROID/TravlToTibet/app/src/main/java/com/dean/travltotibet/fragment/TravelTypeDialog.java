package com.dean.travltotibet.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.CustomDialog;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/23/15.
 * 选择旅行类型
 */
public class TravelTypeDialog extends DialogFragment {

    public final static String FROM_FIRST = "from_first";
    public final static String FROM_SECOND = "from_itself";

    private View contentLayout;

    private String fromType;

    private ArrayList<RoutePlan> plans;

    private String route;
    private String routeName;

    private View bike;
    private View hike;
    private View moto;
    private View car;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 内容视图
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.travel_dialog_layout, null);

        if (getArguments() != null) {
            route = getArguments().getString(Constants.INTENT_ROUTE);
            routeName = getArguments().getString(Constants.INTENT_ROUTE_NAME);
            fromType = getArguments().getString(Constants.INTENT_FROM_WHERE);
        }

        initContentView();

        // 创建对话框，设置标题，布局，关闭响应
        final CustomDialog dialog = new CustomDialog(getActivity());
        dialog.setTitle(getString(R.string.travel_type_dialog_title));
        dialog.setCustomContentView(contentLayout);
        return dialog;
    }

    private void initContentView() {
        bike = contentLayout.findViewById(R.id.travel_bike);
        hike = contentLayout.findViewById(R.id.travel_hike);
        moto = contentLayout.findViewById(R.id.travel_moto);
        car = contentLayout.findViewById(R.id.travel_car);

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), TravelType.BIKE);
            }
        });

        hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), TravelType.HIKE);
            }
        });

        moto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), TravelType.MOTO);
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), TravelType.CAR);
            }
        });
    }

    protected void notifyItemClicked(final View item, final Object obj, final String type) {

        // 如果是开始视图则关闭对话框进行跳转，如果是info视图则更新type类型
        if (fromType.equals(FROM_FIRST)) {
            // 跳转到InfoRouteActivity
            Intent intent = new Intent(getActivity(), InfoRouteActivity.class);
            intent.putExtra(Constants.INTENT_ROUTE, route);
            intent.putExtra(Constants.INTENT_ROUTE_NAME, routeName);
            intent.putExtra(Constants.INTENT_ROUTE_TYPE, type);
            startActivity(intent);
            dismiss();
        } else if (fromType.equals(FROM_SECOND)) {
            ((InfoRouteActivity) getActivity()).updateType(type);
            dismiss();
        }
    }
}