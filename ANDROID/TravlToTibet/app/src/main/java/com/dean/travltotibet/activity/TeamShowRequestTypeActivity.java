package com.dean.travltotibet.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.IntentExtra;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by DeanGuo on 3/7/16.
 */
public class TeamShowRequestTypeActivity extends BaseActivity {

    public static final int SHOW_SEARCH = 0;

    public static final int SHOW_PERSONAL = 1;

    private int currentShowType;

    private String searchFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_show_request_type_view);

        if (getIntent() != null) {
            currentShowType = getIntent().getIntExtra(IntentExtra.INTENT_TEAM_REQUEST_SHOW_TYPE, 0);
            searchFilter = getIntent().getStringExtra(IntentExtra.INTENT_TEAM_REQUEST_SEARCH_FILTER);
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setUpToolBar(toolbar);
        setHomeIndicator(TTTApplication.getGoogleIconDrawable(GoogleMaterial.Icon.gmd_arrow_back, TTTApplication.getMyColor(R.color.white)));

        initHeader();
    }

    private void initHeader() {
        if (currentShowType == SHOW_SEARCH) {
            setTitle(searchFilter);
        }
        else if (currentShowType == SHOW_PERSONAL) {
            setTitle(getString(R.string.team_make_personal_request_title));
        }
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    public int getCurrentShowType() {
        return currentShowType;
    }

    public String getSearchFilter() {
        return searchFilter;
    }
}
