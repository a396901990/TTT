package com.dean.travltotibet.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dean.travltotibet.R;

/**
 * Created by DeanGuo on 8/28/15.
 */
public class SquareView extends RelativeLayout {

    ImageView imageView;

    int active_src;

    int disable_src;

    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareView);
        active_src = typedArray.getResourceId(R.styleable.SquareView_activeSrc, 0x000000);
        disable_src = typedArray.getResourceId(R.styleable.SquareView_disableSrc, 0x000000);
        typedArray.recycle();

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setBackgroundResource(R.drawable.btn_click_background);

        imageView = new ImageView(context, attrs);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(disable_src);

        setClickable(true);
        setFocusable(true);

        addView(imageView);
    }

    public void setActiveSrc() {
        imageView.setImageResource(active_src);
    }

    public void setDisableSrc() {
        imageView.setImageResource(disable_src);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        View image = getChildAt(0);
        if (image instanceof ImageView) {

            int imageWidth = MeasureSpec.makeMeasureSpec(width / 2, MeasureSpec.EXACTLY);
            int imageHeight = MeasureSpec.makeMeasureSpec(height / 2, MeasureSpec.EXACTLY);
            image.measure(imageWidth, imageWidth);
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        // 1/4 位置
        View image = getChildAt(0);
        if (image instanceof ImageView) {

            int width = image.getMeasuredWidth();
            int height = image.getMeasuredHeight();

            int mLeft = ((r - l) - width) / 2;
            int mTop = ((b - t) - height) / 2;

            image.layout(mLeft, mTop, mLeft + width, mTop + height);
        }

    }

}
