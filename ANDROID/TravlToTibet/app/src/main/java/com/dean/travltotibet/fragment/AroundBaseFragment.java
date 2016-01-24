package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.adapter.CommentListAdapter;
import com.dean.travltotibet.model.Comment;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;

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