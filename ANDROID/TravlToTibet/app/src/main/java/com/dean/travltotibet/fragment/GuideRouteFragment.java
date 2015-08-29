package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class GuideRouteFragment extends Fragment {

    public static GuideRouteFragment newInstance(Bundle bundle) {
        GuideRouteFragment newFragment = new GuideRouteFragment();
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.guide_route_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
