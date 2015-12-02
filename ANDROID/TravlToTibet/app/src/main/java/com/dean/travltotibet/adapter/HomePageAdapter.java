package com.dean.travltotibet.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.dean.travltotibet.fragment.HomeRecommendFragment;
import com.dean.travltotibet.fragment.HomeRecentFragment;

/**
 * Created by DeanGuo on 11/28/15.
 */
public class HomePageAdapter extends FragmentStatePagerAdapter {

    private SparseArray<String> mTitles;

    private SparseArray<Fragment> mFragments;

    public HomePageAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    private void init() {

        mTitles = new SparseArray<String>();
        mTitles.put(0, "推荐");
        mTitles.put(1, "路线");
        mTitles.put(2, "历史记录");

        mFragments = new SparseArray<Fragment>();
        mFragments.put(0, HomeRecommendFragment.newInstance());
        mFragments.put(1, HomeRecentFragment.newInstance());
        mFragments.put(2, HomeRecentFragment.newInstance());
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
//    @Override public int getPageIconResId(int i) {
//        if (i == 0) {
//            return R.drawable.icon_budget;
//        } else if (i == 1) {
//            return R.drawable.icon_mountain;
//        } else {
//            return R.drawable.icon_mountain;
//        }
//    }
}