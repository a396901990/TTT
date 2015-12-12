package com.dean.travltotibet.ui.customScrollView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by DeanGuo on 9/28/15.
 */
public class ScrollRecycleView extends RecyclerView {


    public ScrollRecycleView(Context context) {
        super(context);
    }

    public ScrollRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}