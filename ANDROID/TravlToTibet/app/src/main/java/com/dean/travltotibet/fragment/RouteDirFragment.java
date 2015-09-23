package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.RouteActivity;
import com.dean.travltotibet.activity.RouteInfoActivity;
import com.dean.travltotibet.ui.SquareView;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 9/19/15.
 * <p/>
 * 用来控制route方向
 */
public class RouteDirFragment extends Fragment {

    private RouteInfoActivity routeInfoActivity;

    private View root;

    public RouteDirFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.route_dir_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        routeInfoActivity = (RouteInfoActivity) getActivity();

        Button btn_f = (Button) root.findViewById(R.id.btn_select_f);
        Button btn_r = (Button) root.findViewById(R.id.btn_select_r);

        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                intent.putExtra(Constants.INTENT_ROUTE_NAME, "XINZANG");
                intent.putExtra(Constants.INTENT_ROUTE_DIR, true);
                intent.putExtra(Constants.INTENT_ROUTE_PLAN_ID, 1);
                //startActivity(intent);

                DialogFragment dialogFragment = new RouteConfirmDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_ROUTE_NAME, routeInfoActivity.getRouteName());
                bundle.putString(Constants.INTENT_ROUTE_TYPE, routeInfoActivity.getRouteType());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), RouteConfirmDialog.class.getName());
            }
        });

        btn_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RouteActivity.class);
                intent.putExtra(Constants.INTENT_ROUTE_NAME, "XINZANG");
                intent.putExtra(Constants.INTENT_ROUTE_DIR, false);
                startActivity(intent);
            }
        });

    }

}
