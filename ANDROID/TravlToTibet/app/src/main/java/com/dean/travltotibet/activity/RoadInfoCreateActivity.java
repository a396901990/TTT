package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.RoadInfoCreateFragment;
import com.dean.travltotibet.util.IntentExtra;

/**
 * Created by DeanGuo on 4/8/16.
 */
public class RoadInfoCreateActivity extends BaseActivity {

    private RoadInfoCreateFragment roadInfoCreateFragment;

    private String routeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.road_info_create_view);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle("路线反馈");
        setHomeIndicator();

        if (getIntent() != null) {
            routeName = getIntent().getStringExtra(IntentExtra.INTENT_ROUTE);
        }

        roadInfoCreateFragment = (RoadInfoCreateFragment) getFragmentManager().findFragmentById(R.id.road_info_create_fragment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_road_info_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // 提交按钮
        if (id == R.id.action_submit) {
            roadInfoCreateFragment.commitRequest();
        }
        if (id == android.R.id.home) {
            actionBack();
            return false;
        }
        return super.onOptionsItemSelected(item);
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

    public String getRouteName() {
        return routeName;
    }

}
