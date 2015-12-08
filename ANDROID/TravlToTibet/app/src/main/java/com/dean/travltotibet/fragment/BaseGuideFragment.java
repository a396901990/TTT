package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.CustomProgress;
import com.dean.travltotibet.ui.fab.FloatingActionMenu;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseGuideFragment extends Fragment {

    public abstract void update();

}
