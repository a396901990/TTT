package com.dean.travltotibet.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by DeanGuo on 9/1/15.
 *
 * ExpandableListView 可适应scrollview，
 * 原理就是设置高度为最大的一半，然后在匹配父控件
 */
public class TimelineListview extends ExpandableListView{
    public TimelineListview(Context context) {
        super(context);
    }

    public TimelineListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
