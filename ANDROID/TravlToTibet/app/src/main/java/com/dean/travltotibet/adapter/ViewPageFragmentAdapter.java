package com.dean.travltotibet.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 10/24/15.
 */
public class ViewPageFragmentAdapter extends FragmentStatePagerAdapter {

    private final FragmentManager mFragmentManager;
    private SparseArray<Fragment> mFragments;
    private FragmentTransaction mCurTransaction;

    private java.util.List<Class<? extends Fragment>> frags = new ArrayList<Class<? extends Fragment>>();
    private List<Bundle> fragArguments = new ArrayList<Bundle>();

    public ViewPageFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        mFragmentManager = fragmentManager;
        mFragments = new SparseArray<Fragment>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = getItem(position);
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        mCurTransaction.add(container.getId(), fragment, "fragment:" + position);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        mCurTransaction.detach(mFragments.get(position));
        mFragments.remove(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object fragment) {
        return ((Fragment) fragment).getView() == view;
    }

    public void add(Class<? extends Fragment> clazz, Bundle arguments) {
        frags.add(clazz);
        fragArguments.add(arguments);
        notifyDataSetChanged();
    }

    public int remove(Class<? extends Fragment> clazz) {
        int index = frags.indexOf(clazz);
        if (index >= 0) {
            frags.remove(index);
            notifyDataSetChanged();
        }
        return index;
    }

    public Fragment getFragment(int position) {
        return mFragments.get(position);
    }

    public Fragment getItem(int position) {
        Class<? extends Fragment> clazz = frags.get(position);
        Fragment frag = null;
        try {
            frag = clazz.newInstance();
            Bundle args = fragArguments.get(position);
            if (args != null) {
                frag.setArguments(args);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mFragments.put(position, frag);
        return frag;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public int getCount() {
        return frags.size();
    }

    public SparseArray<Fragment> getAllFragments() {
        return mFragments;
    }
}