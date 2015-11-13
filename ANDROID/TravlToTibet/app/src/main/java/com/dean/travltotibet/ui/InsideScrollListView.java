package com.dean.travltotibet.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.mobeta.android.dslv.DragSortListView;

/**
 * Created by DeanGuo on 11/14/15.
 */
public class InsideScrollListView extends ListView {
    public InsideScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
