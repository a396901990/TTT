package com.dean.travltotibet.activity;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.LoginDialog;
import com.dean.travltotibet.fragment.TeamMakeRequestFragment;
import com.dean.travltotibet.ui.fab.FloatingActionButton;
import com.dean.travltotibet.util.LoginUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import de.greenrobot.event.EventBus;

public class TeamMakeRequestActivity extends BaseActivity {

    private TeamMakeRequestFragment teamMakeRequestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_make_request_layout);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(getString(R.string.team_make_request_title));
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        teamMakeRequestFragment = (TeamMakeRequestFragment) getFragmentManager().findFragmentById(R.id.team_make_request_fragment);
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
            teamMakeRequestFragment.commitRequest();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

}