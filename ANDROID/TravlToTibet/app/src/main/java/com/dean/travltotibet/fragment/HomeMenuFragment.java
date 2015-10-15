package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 8/13/15.
 */
public class HomeMenuFragment extends Fragment {

    private View root;

    public HomeMenuFragment() {
    }

    public static HomeMenuFragment newInstance() {
        HomeMenuFragment fragment = new HomeMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.menu_layout, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
