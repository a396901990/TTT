package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class MapFragment extends BaseRouteFragment {

    private View root;

    private RouteActivity mActivity;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.lay1, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (RouteActivity) getActivity();

    }

    /**
     * 获取plan bundle 包括date start end
     */
    private Bundle getPlanBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_DATE, mActivity.getPlanDate());
        bundle.putString(Constants.INTENT_START, mActivity.getPlanStart());
        bundle.putString(Constants.INTENT_END, mActivity.getPlanEnd());

        return bundle;
    }

    @Override
    public void updateRoute(String start, String end, String date, String distance) {
        super.updateRoute(start, end, date, distance);
    }
}
