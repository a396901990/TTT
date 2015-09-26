package com.dean.travltotibet.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.InfoHeaderFragment;
import com.dean.travltotibet.fragment.InfoPrepareFragment;
import com.dean.travltotibet.util.Constants;

public class InfoRouteActivity extends Activity {

    private String route;
    private String routeName;
    private String routeType;

    InfoHeaderFragment headerFragment;
    InfoPrepareFragment prepareFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_route_view);

        if (getIntent() != null) {
            route = getIntent().getStringExtra(Constants.INTENT_ROUTE);
            routeName = getIntent().getStringExtra(Constants.INTENT_ROUTE_NAME);
            routeType = getIntent().getStringExtra(Constants.INTENT_ROUTE_TYPE);
        }
        headerFragment = (InfoHeaderFragment) getFragmentManager().findFragmentById(R.id.info_header_fragment);
        prepareFragment = (InfoPrepareFragment) getFragmentManager().findFragmentById(R.id.info_prepare_fragment);
    }

    public void updateType(String routeType) {
        // 更新routeType
        setRouteType(routeType);

        // update headerFragment
        if (headerFragment.isAdded()) {
            headerFragment.updateType(routeType);
        }

        // update prepareFragment
        if (prepareFragment.isAdded()) {
            prepareFragment.updateType(routeType);
        }
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }


}
