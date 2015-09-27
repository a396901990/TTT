package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.adapter.InfoGridAdapter;
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
        ArrayList<SquareImageView> mdata = new ArrayList<SquareImageView>();
        mdata.add(getOne());
        mdata.add(getOne());
        mdata.add(getOne());
        mdata.add(getOne());
        mdata.add(getOne());
        mdata.add(getOne());
        mdata.add(getOne());
        mdata.add(getOne());
        mdata.add(getOne());
        ScrollGridView gridView = (ScrollGridView) root.findViewById(R.id.gridView);
        InfoGridAdapter adapter = new InfoGridAdapter(getActivity());
        adapter.setData(mdata);
        gridView.setAdapter(adapter);

    }

    public SquareImageView getOne(){
        SquareImageView sq = new SquareImageView(infoRouteActivity);
        sq.setImageSrc(R.drawable.car_active);
        sq.setLabelText("car");
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return sq;
    }

    @Override
    public void updateType(String type) {
        super.updateType(type);
    }
}
