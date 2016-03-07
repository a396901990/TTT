package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.TeamShowPersonalRequestFragment;
import com.dean.travltotibet.model.TeamRequest;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 3/7/16.
 */
public class TeamPersonalRequestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_personal_request_view);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(getString(R.string.team_make_personal_request_title));
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

}
