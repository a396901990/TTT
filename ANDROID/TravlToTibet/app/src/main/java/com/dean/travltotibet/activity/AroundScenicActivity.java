package com.dean.travltotibet.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.adapter.ViewPageFragmentAdapter;
import com.dean.travltotibet.fragment.AroundHotelCommentFragment;
import com.dean.travltotibet.fragment.AroundHotelDetailFragment;
import com.dean.travltotibet.fragment.AroundScenicCommentFragment;
import com.dean.travltotibet.fragment.AroundScenicDetailFragment;
import com.dean.travltotibet.model.AroundType;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.IntentExtra;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundScenicActivity extends AroundBaseActivity {

    private Scenic mScenic;

    private boolean isForward = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            mScenic = (Scenic) getIntent().getSerializableExtra(IntentExtra.INTENT_SCENIC);
            isForward = getIntent().getBooleanExtra(IntentExtra.INTENT_ROUTE_DIR, true);
        }

        setPageTitle(mScenic.getScenic_name());
        
        addPageTab(AroundScenicDetailFragment.class, null, getString(R.string.around_overview));
        addPageTab(AroundScenicCommentFragment.class, null, getString(R.string.around_comment));
        setUpTabViewPager();
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public String getAroundType() {
        return AroundType.SCENIC;
    }

    @Override
    public String[] getHeaderURL() {
        return mScenic.getScenic_pic().split(Constants.URL_MARK);
    }

    @Override
    public Object getAroundObj() {
        return mScenic;
    }

    @Override
    public boolean getDir() {
        return isForward;
    }
}
