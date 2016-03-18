package com.dean.travltotibet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.AroundBaseActivity;
import com.dean.travltotibet.ui.sliderview.SliderLayout;
import com.dean.travltotibet.ui.sliderview.SliderTypes.DefaultSliderView;

/**
 * Created by DeanGuo on 1/13/16.
 * https://github.com/daimajia/AndroidImageSlider
 */
public class AroundHeaderFragment extends Fragment {

    private SliderLayout mDefaultIndicator;

    private AroundBaseActivity aroundActivity;

    private View root;

    public AroundHeaderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.around_header_view, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aroundActivity = (AroundBaseActivity) getActivity();

        initHeaderView();
    }

    private void initHeaderView() {
        mDefaultIndicator = (SliderLayout) root.findViewById(R.id.slider);
        mDefaultIndicator.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);

        String[] urls = aroundActivity.getHeaderURL();

        // 设置图片
        if (urls != null) {
            for (String url : urls) {
                if (!TextUtils.isEmpty(url)) {
                    DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
                    textSliderView.image(url);
                    mDefaultIndicator.addSlider(textSliderView);
                }
            }
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
}
