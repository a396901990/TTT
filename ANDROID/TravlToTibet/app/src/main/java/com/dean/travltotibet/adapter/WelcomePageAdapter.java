package com.dean.travltotibet.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.dean.travltotibet.R;
import com.dean.travltotibet.fragment.HomeRecentFragment;
import com.dean.travltotibet.fragment.HomeRecommendFragment;
import com.dean.travltotibet.fragment.WelcomePageFragment;

/**
 * Created by DeanGuo on 11/28/15.
 */
public class WelcomePageAdapter extends FragmentStatePagerAdapter {

    private SparseArray<String> mTitles;

    private SparseArray<Fragment> mFragments;

    public WelcomePageAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    private void init() {

        mTitles = new SparseArray<String>();
        mTitles.put(0, "");
        mTitles.put(1, "");
        mTitles.put(2, "");
        mTitles.put(3, "");

        mFragments = new SparseArray<Fragment>();
        mFragments.put(0, WelcomePageFragment.newInstance(R.layout.page_one_fragment_view, WelcomePageFragment.ANIM_ALPHA));
        mFragments.put(1, WelcomePageFragment.newInstance(R.layout.page_two_fragment_view, WelcomePageFragment.ANIM_ALPHA));
        mFragments.put(2, WelcomePageFragment.newInstance(R.layout.page_three_fragment_view, WelcomePageFragment.ANIM_ALPHA));
        mFragments.put(3, WelcomePageFragment.newInstance(R.layout.page_four_fragment_view, WelcomePageFragment.NO_ANIM));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    public SparseArray<Fragment> getAllFragments() {
        return mFragments;
    }

    public Fragment getFragment(int position) {
        return mFragments.get(position);
    }
}