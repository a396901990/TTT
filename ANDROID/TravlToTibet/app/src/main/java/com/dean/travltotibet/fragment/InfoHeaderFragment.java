package com.dean.travltotibet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.ui.sliderview.Animations.DescriptionAnimation;
import com.dean.travltotibet.ui.sliderview.SliderLayout;
import com.dean.travltotibet.ui.sliderview.SliderTypes.BaseSliderView;
import com.dean.travltotibet.ui.sliderview.SliderTypes.BurnsSliderView;
import com.dean.travltotibet.ui.sliderview.SliderTypes.DefaultSliderView;
import com.dean.travltotibet.ui.sliderview.SliderTypes.TextSliderView;

import java.util.HashMap;

/**
 * Created by DeanGuo on 9/19/15.
 * https://github.com/daimajia/AndroidImageSlider
 */
public class InfoHeaderFragment extends BaseInfoFragment {

    private InfoActivity infoActivity;

    private SliderLayout mDefaultIndicator;

    private View root;

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

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Page A", "http://img0.ph.126.net/-S17SIiZT09Vu27xZ6m0jQ==/6630696130768649889.jpg");
        url_maps.put("Page B", "http://img1.ph.126.net/M1Xy4XT4uEFnDpg7WrbUNQ==/6630275017815117694.jpg");
        url_maps.put("Page C", "http://img5.uutuu.com/data5/a/ph/large/071128/e64b9d80bce2a44326daf788af28fb8c.jpg");
        url_maps.put("Page D", "http://s1.sinaimg.cn/mw690/005DAgR4ty6NBXTJCSY50&690");
        url_maps.put("Page E", "http://s1.doyouhike.net/files/2010/02/02/0/09e851076feaa8fba7957b739b36b65b.jpg");

        for(String name : url_maps.keySet()) {
            BurnsSliderView textSliderView = new BurnsSliderView(getActivity());
            textSliderView.image(url_maps.get(name));
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
