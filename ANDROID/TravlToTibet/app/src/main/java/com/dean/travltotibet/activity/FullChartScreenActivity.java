package com.dean.travltotibet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.FullChartFragment;
import com.dean.travltotibet.util.Constants;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 12/12/15.
 * 全屏显示chart
 */
public class FullChartScreenActivity
        extends AppCompatActivity {

    // 当前计划
    private String planStart;
    private String planEnd;
    // 当前线路名称
    private String routeName;
    // 当前线路类型
    private String routeType;
    // 当前是否向前，也就是正向反向 f/r
    private boolean isForward;

    private Toolbar mToolbar;

    private FullChartFragment fullChartFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_chart_view);

        Intent intent = getIntent();
        if (intent != null) {
            routeName = intent.getStringExtra(Constants.INTENT_ROUTE);
            routeType = intent.getStringExtra(Constants.INTENT_ROUTE_TYPE);
            isForward = intent.getBooleanExtra(Constants.INTENT_ROUTE_DIR, true);
            planStart = intent.getStringExtra(Constants.INTENT_PLAN_START);
            planEnd = intent.getStringExtra(Constants.INTENT_PLAN_END);
        }

        initToolBar();
        initFragment();
    }

    private void initFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_ROUTE, routeName);
        bundle.putString(Constants.INTENT_ROUTE_TYPE, routeType);
        bundle.putBoolean(Constants.INTENT_ROUTE_DIR, isForward);
        bundle.putString(Constants.INTENT_PLAN_START, planStart);
        bundle.putString(Constants.INTENT_PLAN_END, planEnd);

        fullChartFragment = (FullChartFragment) getFragmentManager().findFragmentById(R.id.full_chart_fragment);
        if (fullChartFragment == null) {
            fullChartFragment = new FullChartFragment();
            fullChartFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.full_chart_fragment, fullChartFragment).commit();
        }

    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        // 返回按钮
        actionBar.setHomeAsUpIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 结束
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
