package com.dean.travltotibet.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.activity.InfoRouteActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.util.Constants;

import java.util.HashMap;

import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

/**
 * Created by DeanGuo on 9/19/15.
 * <p/>
 * 用来控制route类型和计划
 */
public class InfoHeaderFragment extends BaseInfoFragment {

    private InfoRouteActivity infoRouteActivity;

    private InfiniteIndicatorLayout mDefaultIndicator;

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

        infoRouteActivity = (InfoRouteActivity) getActivity();
        mDefaultIndicator = (InfiniteIndicatorLayout) root.findViewById(R.id.indicator_default_circle);
//        HashMap<String,Integer> url_maps = new HashMap<String, Integer>();
//        url_maps = new HashMap<String, Integer>();
//        url_maps.put("Page A", R.drawable.show_temp);
//        url_maps.put("Page B", R.drawable.show_temp);
//        url_maps.put("Page C", R.drawable.show_temp);
//        url_maps.put("Page D", R.drawable.show_temp);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps = new HashMap<String, String>();
        url_maps.put("Page A", "http://img0.ph.126.net/-S17SIiZT09Vu27xZ6m0jQ==/6630696130768649889.jpg");
        url_maps.put("Page B", "http://img1.ph.126.net/M1Xy4XT4uEFnDpg7WrbUNQ==/6630275017815117694.jpg");
        url_maps.put("Page C", "http://img5.uutuu.com/data5/a/ph/large/071128/e64b9d80bce2a44326daf788af28fb8c.jpg");
        url_maps.put("Page D", "http://s1.sinaimg.cn/mw690/005DAgR4ty6NBXTJCSY50&690");
        url_maps.put("Page E", "http://s1.doyouhike.net/files/2010/02/02/0/09e851076feaa8fba7957b739b36b65b.jpg");

        for(String name : url_maps.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .showImageResForEmpty(R.color.gray)
                    .showImageResForError(R.color.gray);
            textSliderView.getBundle()
                    .putString("extra",name);
            mDefaultIndicator.addSlider(textSliderView);
        }
        mDefaultIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
        mDefaultIndicator.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDefaultIndicator.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDefaultIndicator.startAutoScroll();
    }

    @Override
    public void updateType(String type) {
        super.updateType(type);
    }
}
