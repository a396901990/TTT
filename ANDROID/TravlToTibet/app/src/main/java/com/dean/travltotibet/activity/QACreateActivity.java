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
import com.dean.travltotibet.fragment.QACreateUpdateFragment;
import com.dean.travltotibet.model.QARequest;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.ScreenUtil;

/**
 * Created by DeanGuo on 5/3/16.
 */
public class QACreateActivity extends BaseActivity {

    private QACreateUpdateFragment qaCreateUpdateFragment;

    private QARequest qaRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q_a_create_view);

        if (getIntent() != null) {
            qaRequest = (QARequest) getIntent().getSerializableExtra(IntentExtra.INTENT_QA_REQUEST);
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(getString(R.string.q_a_create_title));
        setHomeIndicator();

        qaCreateUpdateFragment = (QACreateUpdateFragment) getFragmentManager().findFragmentById(R.id.q_a_create_fragment);
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
            qaCreateUpdateFragment.commitRequest();
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

    public QARequest getQaRequest() {
        return qaRequest;
    }

    public void setQaRequest(QARequest qaRequest) {
        this.qaRequest = qaRequest;
    }

}
