package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.TeamCreateRequestFragment;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamCreateRequestActivity extends BaseActivity {

    private TeamCreateRequestFragment teamCreateRequestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_make_request_layout);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(getString(R.string.team_make_request_title));
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        teamCreateRequestFragment = (TeamCreateRequestFragment) getFragmentManager().findFragmentById(R.id.team_make_request_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_team_make_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // 提交按钮
        if (id == R.id.action_submit) {
            teamCreateRequestFragment.commitRequest();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

}
