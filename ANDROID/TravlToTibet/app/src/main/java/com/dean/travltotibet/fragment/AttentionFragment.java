package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.greendao.RouteAttention;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.adapter.AttentionAdapter;
import com.dean.travltotibet.util.IntentExtra;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeanGuo on 12/21/15.
 */
public class AttentionFragment extends Fragment {

    private View root;

    private String route;

    private String routeType;

    public AttentionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.attention_fragment_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            route = bundle.getString(IntentExtra.INTENT_ROUTE);
            routeType = bundle.getString(IntentExtra.INTENT_ROUTE_TYPE);
        }
        initView();
    }

    private void initView() {


        List<RouteAttention> routeAttentions = TTTApplication.getDbHelper().getRouteAttention(route, routeType);
        routeAttentions.toString();

        AttentionAdapter adapter = new AttentionAdapter();
        adapter.setData((ArrayList<RouteAttention>) routeAttentions);

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.attention_fragment_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(adapter);

    }
}
