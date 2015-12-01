package com.dean.travltotibet.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.dean.travltotibet.activity.InfoActivity;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class InfoScrollView extends ScrollView {

    private InfoActivity.ScrollViewListener mScrollListener;

    public InfoScrollView(Context context) {
        super(context);
    }

    public InfoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InfoScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener != null) {
            mScrollListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public InfoActivity.ScrollViewListener getScrollListener() {
        return mScrollListener;
    }

    public void setScrollListener(InfoActivity.ScrollViewListener mScrollListener) {
        this.mScrollListener = mScrollListener;
    }
}
