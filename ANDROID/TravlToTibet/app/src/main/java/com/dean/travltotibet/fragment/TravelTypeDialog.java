package com.dean.travltotibet.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.activity.RouteInfoActivity;
import com.dean.travltotibet.adapter.RoutePlanListAdapter;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/23/15.
 * 选择旅行类型
 */
public class TravelTypeDialog extends DialogFragment {

    private View root;
    private ArrayList<RoutePlan> plans;

    private View bike;
    private View hike;
    private View moto;
    private View car;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        root = LayoutInflater.from(getActivity()).inflate(R.layout.travel_type_fragment, null);

        initButton();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(root).setTitle(Constants.TRAVEL_TYPE_TITLE);
        return builder.create();
    }

    private void initButton() {
        bike = root.findViewById(R.id.travel_bike);
        hike = root.findViewById(R.id.travel_hike);
        moto = root.findViewById(R.id.travel_moto);
        car = root.findViewById(R.id.travel_car);

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

    protected void notifyItemClicked(final View item, final Object obj, final TravelType type) {
        Intent intent = new Intent(getActivity(), RouteInfoActivity.class);
        intent.putExtra(Constants.INTENT_ROUTE_TYPE, TravelType.getTypeValue(type));
        intent.putExtra(Constants.INTENT_ROUTE_NAME, "XINZANG");
        startActivity(intent);
        getActivity().finish();
    }
}