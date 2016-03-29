package com.dean.travltotibet.ui.customScrollView;

import android.content.Context;
import android.util.AttributeSet;

import com.dean.travltotibet.ui.loadmore.LoadMoreListView;
import com.dean.travltotibet.ui.loadmore.LoadMorePressListView;

/**
 * Created by DeanGuo on 11/14/15.
 */
public class InsideScrollLoadMorePressListView extends LoadMorePressListView {
    public InsideScrollLoadMorePressListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
