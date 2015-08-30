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
public class AutoResizeImageView extends RelativeLayout {

    ImageView imageView;

    TextView textView;

    String labelText;

    int imageSrc;

    int labelColor;

    float labelSize;

    public AutoResizeImageView(Context context) {
        super(context);
    }

    public AutoResizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView);
        labelText = typedArray.getString(R.styleable.SquareImageView_labelText);
        imageSrc = typedArray.getResourceId(R.styleable.SquareImageView_imageSrc, 0x000000);
        labelColor = typedArray.getColor(R.styleable.SquareImageView_labelTextColor, Color.WHITE);
        labelSize = typedArray.getDimension(R.styleable.SquareImageView_labelSize, 8);
        typedArray.recycle();

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setBackgroundResource(R.drawable.btn_click_background);

        imageView = new ImageView(context, attrs);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundResource(imageSrc);

        textView = new TextView(context, attrs);
        textView.setGravity(Gravity.CENTER);
        textView.setText(labelText);
        textView.setTextColor(labelColor);
        textView.setTextSize(labelSize);

        setClickable(true);

        setFocusable(true);

        addView(imageView);

        addView(textView);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        View image = getChildAt(0);
        if (image instanceof ImageView) {

            int imageWidth = MeasureSpec.makeMeasureSpec(width / 3, MeasureSpec.EXACTLY);
            int imageHeight = MeasureSpec.makeMeasureSpec(height / 3, MeasureSpec.EXACTLY);
            image.measure(imageWidth, imageWidth);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int imagebottom = 0;

        // 1/4 位置
        View image = getChildAt(0);
        if (image instanceof ImageView) {

            int width = image.getMeasuredWidth();
            int height = image.getMeasuredHeight();

            int mLeft = ((r - l) - width) / 2;
            int mTop = ((b - t) - height) / 4;
            imagebottom = mTop + height;
            image.layout(mLeft, mTop, mLeft + width, mTop + height);
        }

        // 5/6 位置
        View text = getChildAt(1);
        if (text instanceof TextView) {

            int width = text.getMeasuredWidth();
            int height = text.getMeasuredHeight();

            int mLeft = ((r - l) - width) / 2;
            int mTop = (((b - t) - height) / 10 ) * 9;
            int mBottom = ((b - t) - height) / 7 ;

            text.layout(mLeft, imagebottom, mLeft + width, imagebottom + height);
        }
    }

}
