package com.dean.travltotibet.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.dean.travltotibet.fragment.PrepareDetailFragment;
import com.dean.travltotibet.model.InfoType;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 11/28/15.
 */
public class PrepareDetailPageAdapter extends FragmentStatePagerAdapter {

    private SparseArray<String> mTitles = new SparseArray<String>();

    private SparseArray<PrepareDetailFragment> mFragments = new SparseArray<PrepareDetailFragment>();

    private ArrayList<InfoType> mInfoTypes;

    private String mRoute;

    public PrepareDetailPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return InfoType.INFO_TEXT.get(mInfoTypes.get(position));
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    public void setData(ArrayList<InfoType> types, String route) {
        mInfoTypes = types;
        mRoute = route;

        for (int i=0; i<types.size(); i++) {
            String title = InfoType.INFO_TEXT.get(types.get(i));
            mTitles.put(i, title);
            mFragments.put(i, new PrepareDetailFragment(mInfoTypes.get(i), mRoute));
        }
        notifyDataSetChanged();
    }
}