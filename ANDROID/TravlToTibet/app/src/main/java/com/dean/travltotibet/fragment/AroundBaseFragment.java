package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import com.dean.travltotibet.activity.AroundBaseActivity;

/**
 * Created by DeanGuo on 1/13/16.
 */
public abstract class AroundBaseFragment extends Fragment {

    public abstract void onTabChanged();

    public AroundBaseActivity getAroundActivity() {
        return (AroundBaseActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAroundActivity().getFloatingBtn().setVisibility(View.GONE);
    }
}