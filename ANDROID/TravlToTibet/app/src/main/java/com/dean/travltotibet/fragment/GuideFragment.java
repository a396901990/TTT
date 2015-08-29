package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.ChartActivity;
import com.dean.travltotibet.activity.GuideRouteActivity;
import com.dean.travltotibet.util.Constants;

/**
 * Created by DeanGuo on 8/30/15.
 */
public class GuideFragment extends Fragment {

    private View root;
    private View routeGuideBtn;
    private View foodGuideBtn;
    private View hotelGuideBtn;

    private ChartActivity mActivity;


    public static Fragment newInstance() {
        return new GuideFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.guide_layout, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (ChartActivity) getActivity();

        initBtn();
    }

    /**
     * 初始化按钮
     */
    private void initBtn() {
        routeGuideBtn = root.findViewById(R.id.route_guide);
        foodGuideBtn = root.findViewById(R.id.food_guide);
        hotelGuideBtn = root.findViewById(R.id.hotel_guide);

        routeGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GuideRouteActivity.class);
                intent.putExtra(Constants.INTENT_PLAN_BUNDLE, getPlanBundle());
                startActivity(intent);
            }
        });

        foodGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GuideRouteActivity.class);
            }
        });

        hotelGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GuideRouteActivity.class);
            }
        });
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
