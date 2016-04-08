package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class RouteDiscoverFragment extends BaseRouteFragment {

    private View rootView;

    private View contentView;

    private RouteActivity routeActivity;

    public static RouteDiscoverFragment newInstance() {
        RouteDiscoverFragment newFragment = new RouteDiscoverFragment();
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.route_discover_view, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        routeActivity = (RouteActivity) getActivity();
    }

    @Override
    protected void onLoadPrepared() {

    }

    @Override
    protected void onLoading() {

    }

    @Override
    protected void onLoadFinished() {

    }

    @Override
    public void updateRoute() {

    }

    @Override
    public void fabBtnEvent() {

    }
}
