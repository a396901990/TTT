package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.TeamCreateUpdateRequestFragment;
import com.dean.travltotibet.model.TeamRequest;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import cn.bmob.v3.helper.PermissionManager;

/**
 * Created by DeanGuo on 3/3/16.
 */
public class TeamCreateRequestActivity extends BaseActivity {

    private TeamCreateUpdateRequestFragment teamCreateUpdateRequestFragment;

    private TeamRequest teamRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_create_request_view);

        if (getIntent() != null) {
            teamRequest = (TeamRequest) getIntent().getSerializableExtra(IntentExtra.INTENT_TEAM_REQUEST);
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(getString(R.string.team_make_request_title));
        setHomeIndicator();

        teamCreateUpdateRequestFragment = (TeamCreateUpdateRequestFragment) getFragmentManager().findFragmentById(R.id.team_make_request_fragment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
            if (ScreenUtil.isFastClick()) {
                return true;
            }
            teamCreateUpdateRequestFragment.commitRequest();
        }
        if (id == android.R.id.home) {
            actionBack();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    public TeamRequest getTeamRequest() {
        return teamRequest;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            actionBack();
        }
        return false;
    }

    private void actionBack() {

        new MaterialDialog.Builder(this)
                .title(getString(R.string.back_action_title))
                .positiveText(getString(R.string.ok_btn))
                .negativeText(getString(R.string.cancel_btn))
                .positiveColor(TTTApplication.getMyColor(R.color.colorPrimary))
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        finish();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

}
