package com.dean.travltotibet.ui.customScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.ScreenUtil;

/**
 * Created by DeanGuo on 11/14/15.
 */
public class AutoHeightListView extends ListView {
    public AutoHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    LinearLayout footerPad = new LinearLayout(getContext());

    @Override
    protected void onSizeChanged(int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Log.e("height:  ", "size:"+size + " h:" + h);

        // This is from Droid-Fu. Converts dip to px
        final int size = ScreenUtil.dip2px(getContext(), 60) * 3;

        footerPad.setBackgroundColor(TTTApplication.getMyColor(R.color.white));

        if (size > h) {
            this.removeFooterView(footerPad);
            footerPad.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, size - h));
            this.addFooterView(footerPad);
        } else {
            this.removeFooterView(footerPad);
            footerPad.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, h));
            this.addFooterView(footerPad);
        }
    }
}
