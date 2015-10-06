package com.dean.travltotibet.util;

import android.content.SharedPreferences;

import com.dean.travltotibet.TTTApplication;

/**
 * Created by DeanGuo on 10/7/15.
 *
 * 记录工具
 */
public class RecordUtil {

    // 启动次数
    public static final String LAUNCH_COUNT = "launch_count";

    public static final String RECENT_ROUTE = "recent_route";

    public static final String RECENT_ROUTE_NAME = "recent_route_name";

    /**
     * 跟新启动次数 +1
     */
    public static void updateLaunchCount()
    {
        SharedPreferences preferences = TTTApplication.getSharedPreferences();
        int launchCount = preferences.getInt(LAUNCH_COUNT, 0);
        preferences.edit().putInt(LAUNCH_COUNT, ++launchCount).apply();
    }
}
