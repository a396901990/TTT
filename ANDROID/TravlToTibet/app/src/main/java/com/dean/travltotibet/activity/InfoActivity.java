package com.dean.travltotibet.activity;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.InfoConfirmDialog;
import com.dean.travltotibet.fragment.InfoHeaderFragment;
import com.dean.travltotibet.fragment.InfoPrepareFragment;
import com.dean.travltotibet.fragment.TravelTypeDialog;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class InfoActivity extends AppCompatActivity {

    private String route;
    private String routeName;
    private String routeType;

    private InfoHeaderFragment headerFragment;
    private InfoPrepareFragment prepareFragment;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_view);
        if (getIntent() != null) {
            route = getIntent().getStringExtra(Constants.INTENT_ROUTE);
            routeName = getIntent().getStringExtra(Constants.INTENT_ROUTE_NAME);
            routeType = getIntent().getStringExtra(Constants.INTENT_ROUTE_TYPE);
        }

        headerFragment = (InfoHeaderFragment) getFragmentManager().findFragmentById(R.id.info_header_fragment);
        prepareFragment = (InfoPrepareFragment) getFragmentManager().findFragmentById(R.id.info_prepare_fragment);

        initView();
        initGoButton();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).actionBar().color(Color.WHITE));

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(routeName);

        fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        fab.setImageDrawable(TravelType.getWhiteTypeImageSrc(getRouteType()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TravelTypeDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_ROUTE, getRoute());
                bundle.putString(Constants.INTENT_ROUTE_NAME, getRouteName());
                bundle.putString(Constants.INTENT_FROM_WHERE, TravelTypeDialog.FROM_SECOND);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), TravelTypeDialog.class.getName());
            }
        });
    }

    /**
     * 初始化出发按钮
     */
    private void initGoButton() {
        Button go = (Button) this.findViewById(R.id.goButton);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new InfoConfirmDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_ROUTE, getRoute());
                bundle.putString(Constants.INTENT_ROUTE_NAME, getRouteName());
                bundle.putString(Constants.INTENT_ROUTE_TYPE, getRouteType());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), InfoConfirmDialog.class.getName());
            }
        });
    }

    public void updateType(String routeType) {
        // 更新routeType
        setRouteType(routeType);
        // 更新图标
        fab.setImageDrawable(TravelType.getWhiteTypeImageSrc(getRouteType()));

        // update headerFragment
        if (headerFragment.isAdded()) {
            headerFragment.updateType(routeType);
        }

        // update prepareFragment
        if (prepareFragment.isAdded()) {
            prepareFragment.updateType(routeType);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 结束
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
