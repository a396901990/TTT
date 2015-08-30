package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class GuideRouteFragment extends Fragment {

    private View root;

    public static GuideRouteFragment newInstance(Bundle bundle) {
        GuideRouteFragment newFragment = new GuideRouteFragment();
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_route_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        TextView tv = (TextView)root.findViewById(R.id.ttt);
        tv.setText(this.getArguments().getString(Constants.INTENT_DATE));
    }
}
