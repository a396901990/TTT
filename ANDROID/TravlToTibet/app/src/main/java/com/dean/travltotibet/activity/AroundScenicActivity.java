package com.dean.travltotibet.activity;

import android.os.Bundle;

import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.AroundCommentFragment;
import com.dean.travltotibet.fragment.AroundScenicCommentFragment;
import com.dean.travltotibet.fragment.AroundScenicDetailFragment;
import com.dean.travltotibet.fragment.BaseRatingCommentFragment;

/**
 * Created by DeanGuo on 1/13/16.
 */
public class AroundScenicActivity extends AroundBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPageTab(AroundScenicDetailFragment.class, null, getString(R.string.around_overview));
        addPageTab(AroundScenicCommentFragment.class, null, getString(R.string.around_comment));
        setUpTabViewPager();
    }

    @Override
    protected boolean needShowSystemBar() {
        return true;
    }

    @Override
    public Object getAroundObj() {
        return null;
    }
}
