package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.ui.SquareView;

/**
 * Created by DeanGuo on 9/19/15.
 * <p/>
 * 用来控制route类型和计划
 */
public class InfoTypeFragment extends Fragment {

    private InfoRouteActivity infoRouteActivity;

    private View root;

    private SquareView bike;

    private SquareView hiking;

    private SquareView moto;

    private SquareView car;

    public InfoTypeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.route_type_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        infoRouteActivity = (InfoRouteActivity) getActivity();

        bike = (SquareView) root.findViewById(R.id.bike_btn);
        hiking = (SquareView) root.findViewById(R.id.hiking_btn);
        moto = (SquareView) root.findViewById(R.id.moto_btn);
        car = (SquareView) root.findViewById(R.id.car_btn);

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bike.setActiveSrc();
                hiking.setDisableSrc();
                moto.setDisableSrc();
                car.setDisableSrc();
            }
        });
        hiking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bike.setDisableSrc();
                hiking.setActiveSrc();
                moto.setDisableSrc();
                car.setDisableSrc();
            }
        });
        moto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bike.setDisableSrc();
                hiking.setDisableSrc();
                moto.setActiveSrc();
                car.setDisableSrc();
            }
        });
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bike.setDisableSrc();
                hiking.setDisableSrc();
                moto.setDisableSrc();
                car.setActiveSrc();
            }
        });
    }


}
