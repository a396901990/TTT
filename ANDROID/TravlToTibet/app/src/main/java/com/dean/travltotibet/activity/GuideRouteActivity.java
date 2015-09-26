package com.dean.travltotibet.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.RouteGuideFragment;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class GuideRouteActivity extends Activity {

    RouteGuideFragment routeGuideFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_route);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setIcon(R.drawable.route_map);
        getActionBar().setTitle(getString(R.string.guide_route_name));

        initFragment();
    }

    private void initFragment() {
        // routeGuideFragment
        Fragment fragment = getFragmentManager().findFragmentById(R.id.route_guide_fragment);
        if (fragment == null) {
            routeGuideFragment = RouteGuideFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.route_guide_fragment, routeGuideFragment).commit();
        } else {
            routeGuideFragment = (RouteGuideFragment) fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guide_route, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.homeAsUp) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
