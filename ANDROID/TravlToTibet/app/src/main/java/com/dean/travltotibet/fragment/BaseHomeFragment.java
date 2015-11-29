package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.ui.CustomProgress;

/**
 * Created by DeanGuo on 8/31/15.
 */
public abstract class BaseHomeFragment extends Fragment {

    /**
     * 跟新fragment
     */
    public abstract void update();
}
