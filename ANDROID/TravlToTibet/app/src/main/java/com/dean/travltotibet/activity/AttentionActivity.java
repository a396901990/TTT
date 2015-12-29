package com.dean.travltotibet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.PrepareDetailFragment;
import com.dean.travltotibet.model.InfoType;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;


/**
 * Created by DeanGuo on 12/20/15.
 */
public class AttentionActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention_layout);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        // init fragment
        Intent intent = getIntent();
        if (intent != null) {

            Bundle bundle = intent.getBundleExtra(IntentExtra.INTENT_ROUTE_BUNDLE);
            String route = bundle.getString(IntentExtra.INTENT_ROUTE);
            String routeType = bundle.getString(IntentExtra.INTENT_ROUTE_TYPE);

            PrepareDetailFragment attentionFragment = (PrepareDetailFragment) getFragmentManager().findFragmentById(R.id.attention_fragment);
            if (attentionFragment == null) {
                attentionFragment = new PrepareDetailFragment(InfoType.ATTENTION, route, routeType);
                getFragmentManager().beginTransaction().add(R.id.attention_fragment, attentionFragment).commit();
            }
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

}
