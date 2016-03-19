package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.fragment.TeamRequestSearchFragment;
import com.dean.travltotibet.util.CountUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 3/7/16.
 */
public class TeamShowRequestSearchActivity extends BaseActivity {

    private String searchFilter;

    private TeamRequestSearchFragment searchTypeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_show_request_type_view);

        if (getIntent() != null) {
            searchFilter = getIntent().getStringExtra(IntentExtra.INTENT_TEAM_REQUEST_SEARCH_FILTER);
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setTitle(searchFilter);
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        searchTypeFragment = (TeamRequestSearchFragment) getFragmentManager().findFragmentById(R.id.team_request_search_type_fragment);
        searchTypeFragment.setSearchFilter(searchFilter);

        CountUtil.countTeamSearch(this, searchFilter);
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

}
