package com.dean.travltotibet.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.dean.travltotibet.R;

public class FlowLayout
    extends ViewGroup
{

    private int paddingHorizontal;

    private int paddingVertical;
    
    private int endPadding;

    public FlowLayout( Context context )
    {
        super(context);
        init();
    }

    public FlowLayout( Context context, AttributeSet attrs )
    {
        this(context, attrs, 0);
    }

    public FlowLayout( Context context, AttributeSet attrs, int defStyle )
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        paddingHorizontal = getResources().getDimensionPixelSize(R.dimen.flowlayout_horizontal_padding);
        paddingVertical = getResources().getDimensionPixelSize(R.dimen.flowlayout_vertical_padding);
        endPadding = getResources().getDimensionPixelSize(R.dimen.flowlayout_end_padding);
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec )
    {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int lineHeight = 0;
        // 100 is a dummy number, widthMeasureSpec should always be EXACTLY for FlowLayout
        int myWidth = resolveSize(MeasureSpec.EXACTLY, widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++)
        {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.GONE)
            {
                continue;
            }
            // let the child measure itself
            child.measure(getChildMeasureSpec(widthMeasureSpec, 0, child.getLayoutParams().width), getChildMeasureSpec(heightMeasureSpec, 0, child.getLayoutParams().height));
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            // lineheight is the height of current line, should be the height of the heightest view
            if (childWidth + childLeft + getPaddingRight() > myWidth)
            {
                // wrap this line
                childLeft = getPaddingLeft();
                childTop += paddingVertical + lineHeight;
                lineHeight = childHeight;
            }
            else if (lineHeight < childHeight)
            {
                lineHeight = childHeight;
            }
            childLeft += childWidth + paddingHorizontal;
        }

        int wantedHeight = childTop + lineHeight + getPaddingBottom() + endPadding;
        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout( boolean changed, int left, int top, int right, int bottom )
    {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int lineHeight = 0;
        int myWidth = right - left;
        for (int i = 0; i < getChildCount(); i++)
        {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.GONE)
            {
                continue;
            }
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (childWidth + childLeft + getPaddingRight() > myWidth)
            {
                childLeft = getPaddingLeft();
                childTop += paddingVertical + lineHeight;
                lineHeight = childHeight;
            }
            else if (lineHeight < childHeight)
            {
                lineHeight = childHeight;
            }
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + paddingHorizontal;
        }
    }
}