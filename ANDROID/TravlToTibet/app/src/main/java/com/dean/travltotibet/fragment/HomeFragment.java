package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dean.greendao.Route;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.HomeActivity;
import com.dean.travltotibet.adapter.HomeGridAdapter;
import com.dean.travltotibet.ui.ScrollGridView;
import com.dean.travltotibet.util.Constants;

import java.util.ArrayList;

/**
 * Created by DeanGuo on 10/15/15.
 */
public class HomeFragment extends Fragment {

    private View root;
    private GridView gridView;
    private HomeGridAdapter adapter;
    private ArrayList<Route> routes;
    private HomeActivity mActivity;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.home_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity)getActivity();

        getRouteData();
        initGridView();
    }

    private void initGridView() {
        gridView = (GridView) root.findViewById(R.id.gridView);
        adapter = new HomeGridAdapter(getActivity());
        adapter.setData(routes);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogFragment dialogFragment = new TravelTypeDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_ROUTE, routes.get(position).getRoute());
                bundle.putString(Constants.INTENT_ROUTE_NAME, routes.get(position).getName());
                bundle.putString(Constants.INTENT_FROM_WHERE, TravelTypeDialog.FROM_FIRST);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), TravelTypeDialog.class.getName());
            }
        });
    }

    /**
     * 获取route数据
     */
    private void getRouteData() {
        routes = (ArrayList<Route>) TTTApplication.getDbHelper().getRoutsList();
        for (Route route : routes) {
            Log.e("route", route.toString());
        }
    }


}
