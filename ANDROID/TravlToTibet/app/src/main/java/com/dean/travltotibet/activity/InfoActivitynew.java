package com.dean.travltotibet.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.InfoConfirmDialog;
import com.dean.travltotibet.fragment.InfoHeaderFragment;
import com.dean.travltotibet.fragment.InfoPrepareFragment;
import com.dean.travltotibet.fragment.TravelTypeDialog;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.ui.InfoScrollView;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.ScreenUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialize.util.UIUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class InfoActivitynew extends AppCompatActivity {

    private String route;
    private String routeName;
    private String routeType;

    private InfoHeaderFragment headerFragment;
    private InfoPrepareFragment prepareFragment;

    private ImageView returnBtn;
    private ImageView typeBtn;

    private View headerBar;
    private View headerBackground;
    private InfoScrollView scrollView;

    public interface ScrollViewListener {

        void onScrollChanged(InfoScrollView scrollView, int x, int y, int oldx, int oldy);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_view2);
        if (getIntent() != null) {
            route = getIntent().getStringExtra(Constants.INTENT_ROUTE);
            routeName = getIntent().getStringExtra(Constants.INTENT_ROUTE_NAME);
            routeType = getIntent().getStringExtra(Constants.INTENT_ROUTE_TYPE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).actionBar().color(Color.WHITE));

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(routeName);
        fillFab();
    }

    private void fillFab() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        fab.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_directions_run).actionBar().color(Color.WHITE));
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
