package com.dean.travltotibet.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ChartPagerAdapter extends FragmentStatePagerAdapter {

    private java.util.List<Class<? extends Fragment>> frags = new ArrayList<Class<? extends Fragment>>();

    private List<Bundle> fragArguments = new ArrayList<Bundle>();

    public SparseArray<Fragment> getAllFragments() {
        return instances;
    }

    private SparseArray<Fragment> instances = new SparseArray<Fragment>();

    public ChartPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(Class<? extends Fragment> clazz, Bundle arguments) {
        frags.add(clazz);
        fragArguments.add(arguments);
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
        return instances.get(position);
    }

    @Override
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
        instances.put(position, frag);
        return frag;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        instances.remove(position);
    }

    @Override
    public int getItemPosition(Object object) {
        if (object != null) {
            for (int i = 0; i < frags.size(); i++) {
                if (frags.get(i).isAssignableFrom(object.getClass())) {
                    return i;
                }
            }
        }
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return frags.size();
    }

}
