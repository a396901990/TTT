package com.dean.travltotibet.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 8/28/15.
 */
public class SquareMaterialRippleLayout extends MaterialRippleLayout {

    public SquareMaterialRippleLayout(Context context) {
        super(context);
    }

    public SquareMaterialRippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, width);
    }
}
