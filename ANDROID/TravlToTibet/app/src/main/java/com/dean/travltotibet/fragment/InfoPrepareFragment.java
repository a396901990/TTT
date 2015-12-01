package com.dean.travltotibet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.activity.InfoActivitynew;
import com.dean.travltotibet.activity.PrepareDetailActivity;
import com.dean.travltotibet.adapter.InfoGridAdapter;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.ScrollGridView;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 9/19/15.
 * <p/>
 * 用来显示route准备视图
 */
public class InfoPrepareFragment extends BaseInfoFragment {

    private InfoActivitynew infoActivity;

    private View root;

    private InfoGridAdapter adapter;

    private InfoType[] BIKES = new InfoType[] {
            InfoType.BUDGET, InfoType.MEDICINE, InfoType.EQUIP_BIKE, InfoType.CLOTHING, InfoType.OUTDOOR_EQUIP, InfoType.CREDENTIALS, InfoType.PERSONAL, InfoType.OTHER
    };

    private InfoType[] HIKES = new InfoType[] {
            InfoType.BUDGET, InfoType.MEDICINE, InfoType.EQUIP_HIKE, InfoType.CLOTHING, InfoType.OUTDOOR_EQUIP, InfoType.CREDENTIALS, InfoType.PERSONAL, InfoType.OTHER
    };

    private InfoType[] MOTOS = new InfoType[] {
            InfoType.BUDGET, InfoType.MEDICINE, InfoType.EQUIP_MOTO, InfoType.CLOTHING, InfoType.OUTDOOR_EQUIP, InfoType.CREDENTIALS, InfoType.PERSONAL, InfoType.OTHER
    };

    private InfoType[] CARS = new InfoType[] {
            InfoType.BUDGET, InfoType.MEDICINE, InfoType.EQUIP_CAR, InfoType.CLOTHING, InfoType.OUTDOOR_EQUIP, InfoType.CREDENTIALS, InfoType.PERSONAL, InfoType.OTHER
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

        infoActivity = (InfoActivitynew) getActivity();

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

                // 打开详细页面
                Intent intent = new Intent(getActivity(), PrepareDetailActivity.class);
                intent.putExtra(Constants.INTENT_PREPARE_TYPE, type);
                intent.putExtra(Constants.INTENT_ROUTE, infoActivity.getRoute());
                startActivity(intent);

                // 设置动画
                getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
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
