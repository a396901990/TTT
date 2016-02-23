package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.TeamMakeActivity;
import com.dean.travltotibet.activity.TeamMakeRequestActivity;

/**
 * Created by DeanGuo on 2/23/16.
 */
public class TeamMakeRequestFragment extends Fragment {

    private View root;

    private TeamMakeRequestActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = LayoutInflater.from(getActivity()).inflate(R.layout.team_make_request_fragment_view, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (TeamMakeRequestActivity) this.getActivity();
    }
}
