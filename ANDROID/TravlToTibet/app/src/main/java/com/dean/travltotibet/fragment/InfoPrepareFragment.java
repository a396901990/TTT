package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.adapter.InfoGridAdapter;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.ScrollGridView;
import com.dean.travltotibet.ui.SquareImageView;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 9/19/15.
 * <p/>
 * 用来显示route准备视图
 */
public class InfoPrepareFragment extends BaseInfoFragment {

    private InfoRouteActivity infoRouteActivity;

    private View root;

    private InfoGridAdapter adapter;

    private InfoType[] BIKES = new InfoType[] {
            InfoType.ROUTE_DETAIL, InfoType.BUDGET, InfoType.MEDICINE, InfoType.EQUIP_BIKE, InfoType.CLOTHING, InfoType.OUTDOOR_EQUIP, InfoType.CREDENTIALS, InfoType.PERSONAL, InfoType.OTHER
    };

    private InfoType[] HIKES = new InfoType[] {
            InfoType.ROUTE_DETAIL, InfoType.BUDGET, InfoType.MEDICINE, InfoType.EQUIP_HIKE, InfoType.CLOTHING, InfoType.OUTDOOR_EQUIP, InfoType.CREDENTIALS, InfoType.PERSONAL, InfoType.OTHER
    };

    private InfoType[] MOTOS = new InfoType[] {
            InfoType.ROUTE_DETAIL, InfoType.BUDGET, InfoType.MEDICINE, InfoType.EQUIP_MOTO, InfoType.CLOTHING, InfoType.OUTDOOR_EQUIP, InfoType.CREDENTIALS, InfoType.PERSONAL, InfoType.OTHER
    };

    private InfoType[] CARS = new InfoType[] {
            InfoType.ROUTE_DETAIL, InfoType.BUDGET, InfoType.MEDICINE, InfoType.EQUIP_CAR, InfoType.CLOTHING, InfoType.OUTDOOR_EQUIP, InfoType.CREDENTIALS, InfoType.PERSONAL, InfoType.OTHER
    };

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
        
        initGridView();
    }

    private void initGridView() {
        ScrollGridView gridView = (ScrollGridView) root.findViewById(R.id.gridView);
        adapter = new InfoGridAdapter(getActivity());
        adapter.setData(BIKES);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoType type = (InfoType) adapter.getItem(position);
                Log.e("hfhf",type.toString());
            }
        });

    }

    @Override
    public void updateType(String type) {
        super.updateType(type);

        if (type.equals(TravelType.BIKE)) {
            adapter.setData(BIKES);
        }
        else if (type.equals(TravelType.HIKE)) {
            adapter.setData(HIKES);
        }
        else if (type.equals(TravelType.MOTO)) {
            adapter.setData(MOTOS);
        }
        else if (type.equals(TravelType.CAR)) {
            adapter.setData(CARS);
        }

    }
}
