package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ChartActivity;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class MapFragment extends Fragment {

    private View root;

    private ChartActivity mActivity;


    public static Fragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.lay1, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (ChartActivity) getActivity();

    }

    /**
     * 获取plan bundle 包括date start end
     */
    private Bundle getPlanBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_DATE, mActivity.getPlanDate());
        bundle.putString(Constants.INTENT_START, mActivity.getPlanStart());
        bundle.putString(Constants.INTENT_END, mActivity.getPlanEnd());

        return bundle;
    }

}
