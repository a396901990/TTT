package com.dean.travltotibet.activity;

import android.os.Bundle;
import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.AroundHotelCommentFragment;
import com.dean.travltotibet.fragment.AroundHotelDetailFragment;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundHotelActivity extends AroundBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPageTab(AroundHotelDetailFragment.class, null, getString(R.string.around_overview));
        addPageTab(AroundHotelCommentFragment.class, null, getString(R.string.around_comment));
        setUpTabViewPager();
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

}
