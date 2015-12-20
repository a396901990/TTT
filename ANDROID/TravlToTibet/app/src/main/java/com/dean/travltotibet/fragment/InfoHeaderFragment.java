package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.greendao.RouteAttention;
import com.dean.greendao.Scenic;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.ui.sliderview.Animations.DescriptionAnimation;
import com.dean.travltotibet.ui.sliderview.SliderLayout;
import com.dean.travltotibet.ui.sliderview.SliderTypes.BaseSliderView;
import com.dean.travltotibet.ui.sliderview.SliderTypes.BurnsSliderView;
import com.dean.travltotibet.ui.sliderview.SliderTypes.DefaultSliderView;
import com.dean.travltotibet.ui.sliderview.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DeanGuo on 9/19/15.
 * https://github.com/daimajia/AndroidImageSlider
 */
public class InfoHeaderFragment extends BaseInfoFragment {

    private InfoActivity infoActivity;

    private SliderLayout mDefaultIndicator;

    private View root;

    private String[] urls;

    public InfoHeaderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.info_header_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        infoActivity = (InfoActivity) getActivity();
        mDefaultIndicator = (SliderLayout) root.findViewById(R.id.slider);

        // 取出所有简介图片的url
        urls = TTTApplication.getDbHelper().getRoutePics(infoActivity.getRoute());

        // 设置图片
        for (String url : urls) {
            BurnsSliderView textSliderView = new BurnsSliderView(getActivity());
            textSliderView.image(url);
            mDefaultIndicator.addSlider(textSliderView);
        }
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDefaultIndicator.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPause() {
        mDefaultIndicator.stopAutoCycle();
        super.onPause();
    }

    @Override
    public void onResume() {
        mDefaultIndicator.startAutoCycle();
        super.onResume();
    }

    @Override
    public void updateType(String type) {
        super.updateType(type);
    }
}
