package com.dean.travltotibet.util;

import android.content.Context;

/**
 * Created by DeanGuo on 9/30/15.
 */
public class ScreenUtil {

    private final Context mContext;

    public ScreenUtil( Context context )
    {
        super();
        this.mContext = context;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
