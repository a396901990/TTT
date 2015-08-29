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
public class SquareImageView extends RelativeLayout {

    ImageView imageView;

    TextView textView;

    String labelText;

    int imageSrc;

    int labelColor;

    float labelSize;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView);
        labelText = typedArray.getString(R.styleable.SquareImageView_labelText);
        imageSrc = typedArray.getResourceId(R.styleable.SquareImageView_imageSrc, 0x000000);
        labelColor = typedArray.getColor(R.styleable.SquareImageView_labelTextColor, Color.WHITE);
        labelSize = typedArray.getDimension(R.styleable.SquareImageView_labelSize, 10);
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
            int mTop = ((b - t) - height) / 4;

            image.layout(mLeft, mTop, mLeft + width, mTop + height);
        }

        // 4/5 位置
        View text = getChildAt(1);
        if (text instanceof TextView) {

            int width = text.getMeasuredWidth();
            int height = text.getMeasuredHeight();

            int mLeft = ((r - l) - width) / 2;
            int mTop = (((b - t) - height) / 5 ) * 4;

            text.layout(mLeft, mTop, mLeft + width, mTop + height);
        }
    }

}
