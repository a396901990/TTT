package com.dean.travltotibet.ui.sliderview.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.kbv.KenBurnsView;

/**
 * This is a slider with KenBurnsView
 */
public class BurnsSliderView extends BaseSliderView {
    public BurnsSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_burn, null);
        KenBurnsView target = (KenBurnsView) v.findViewById(R.id.burns_slider_image);
        target.buildDrawingCache(true);
        bindEventAndShow(v, target);
        return v;
    }
}
